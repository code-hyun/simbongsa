package com.app.simbongsa.service.board;

import com.app.simbongsa.domain.*;
import com.app.simbongsa.entity.board.FreeBoard;
import com.app.simbongsa.entity.board.FreeBoardReply;
import com.app.simbongsa.entity.board.Review;
import com.app.simbongsa.entity.board.ReviewReply;
import com.app.simbongsa.entity.file.FreeBoardFile;
import com.app.simbongsa.entity.file.ReviewFile;
import com.app.simbongsa.entity.member.Member;
import com.app.simbongsa.exception.UserNotFoundException;
import com.app.simbongsa.repository.board.ReviewFileRepository;
import com.app.simbongsa.repository.board.ReviewReplyRepository;
import com.app.simbongsa.repository.board.ReviewRepository;
import com.app.simbongsa.repository.member.MemberRepository;
import com.app.simbongsa.search.admin.AdminBoardSearch;
import com.app.simbongsa.type.FileRepresentationalType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Qualifier("review") @Primary
@Slf4j
public class ReviewServiceImpl implements ReviewService{
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ReviewReplyRepository reviewReplyRepository;
    private final ReviewFileRepository reviewFileRepository;

    /*저장*/
    @Override @Transactional
    public void register(ReviewDTO reviewDTO, Long memberId) {
        List<FileDTO> fileDTOS = reviewDTO.getFileDTOS();

        memberRepository.findById(memberId).ifPresent(
                member -> reviewDTO.setMemberDTO(toMemberDTO(member))
        );

        reviewRepository.save(toReviewEntity(reviewDTO));
        if (fileDTOS != null){
            for (int i = 0; i < fileDTOS.size(); i++){
                if (1 == 0){
                    fileDTOS.get(i).setFileRepresentationalType(FileRepresentationalType.REPRESENTATION);
                }else {
                    fileDTOS.get(i).setFileRepresentationalType(FileRepresentationalType.NORMAL);
                }
                fileDTOS.get(i).setReview(getCurrentSequence());
                reviewFileRepository.save(toReviewFileEntity(fileDTOS.get(i)));
            }
        }
    }

    /*상세*/
    @Override
    public ReviewDTO getReview(Long reviewId) {
        Optional<Review> review = reviewRepository.findByIdForDetail_QueryDsl(reviewId);
        return toReviewDTO(review.get());
    }

    /*시퀀스*/
    @Override
    public Review getCurrentSequence(){
//        reviewRepository.getCurrentSequence_QueryDsl();
        return null;
    }

    /*댓글 저장*/
    @Override @Transactional
    public void registerReply(ReplyRequestDTO replyRequestDTO) {
        memberRepository.findById(replyRequestDTO.getMemberId()).ifPresent(
                member ->
                        reviewRepository.findById(replyRequestDTO.getBoardId()).ifPresent(
                                review -> {
                                    ReviewReply reviewReply = ReviewReply.builder()
                                            .review(review)
                                            .member(member)
                                            .replyContent(replyRequestDTO.getReplyContent())
                                            .build();
                                    reviewReplyRepository.save(reviewReply);
                                    review.setReviewReplyCount(getReplyCount(replyRequestDTO.getBoardId()));
                                    reviewRepository.save(review);
                                }
                        )
        );
    }

    /*댓글 삭제*/
    @Override
    public void deleteReply(Long replyId) {
        reviewReplyRepository.findById(replyId).ifPresent(
                reviewReply -> {
                    reviewReplyRepository.delete(reviewReply);
                    reviewRepository.findById(reviewReply.getReview().getId()).ifPresent(
                            review -> {
                                review.setReviewReplyCount(getReplyCount(replyId));
                                reviewRepository.save(review);
                            }
                    );
                }
        );
    }

    /*댓글 목록*/
    @Override
    public Slice<ReplyDTO> getReplyList(Long reviewId, Pageable pageable) {
        Slice<ReviewReply> reviewReplyList = reviewReplyRepository.findAllByReviewReplyWithPaging(reviewId, pageable);

        List<ReplyDTO> replyDTOS = reviewReplyList.getContent().stream().map(this::toReplyDTO).collect(Collectors.toList());
        return new SliceImpl<>(replyDTOS, pageable, reviewReplyList.hasNext());
    }

    /*댓글 갯수*/
    @Override
    public Integer getReplyCount(Long reviewId) {
        return reviewReplyRepository.getReplyCount_QueryDsl(reviewId).intValue();
    }

    /*작성*/
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void write(ReviewDTO reviewDTO, Long memberId) {
        List<FileDTO> fileDTOS = reviewDTO.getFileDTOS();

        Member member = memberRepository.findById(memberId).orElseThrow(UserNotFoundException::new);

        Review review = toReviewEntity(reviewDTO);
        review.setMember(member);
        reviewRepository.save(review);

        int count = 0;

        for (int i = 0; i < fileDTOS.size(); i++){
            if (fileDTOS.get(i) == null) continue;

            if (count == 0){
                fileDTOS.get(i).setFileRepresentationalType(FileRepresentationalType.REPRESENTATION);
                count++;
            }else {
                fileDTOS.get(i).setFileRepresentationalType(FileRepresentationalType.NORMAL);
            }

            fileDTOS.get(i).setReviewDTO(reviewToDTO(getCurrentSequence()));
            ReviewFile reviewFile = toReviewFileEntity(fileDTOS.get(i));

            reviewFile.setReview(review);
            reviewFileRepository.save(reviewFile);
        }
    }


    /*최신순*/
    @Override
    public Slice<ReviewDTO> getNewReviewList(Pageable pageable) {
        Slice<Review> reviews =
                reviewRepository.findAllByIdReviewPaging_QueryDSL(pageable);
        List<ReviewDTO> collect = reviews.get().map(review -> toReviewDTO(review)).collect(Collectors.toList());

        return new SliceImpl<>(collect, pageable, reviews.hasNext());
    }

    /*인기순*/
    @Override
    public Slice<ReviewDTO> getLikesReviewList(Pageable pageable) {
        Slice<Review> reviews =
                reviewRepository.findAllByLikeCountReviewPaging_QueryDSL(PageRequest.of(0,10));
        List<ReviewDTO> collect = reviews.get().map(review -> toReviewDTO(review)).collect(Collectors.toList());
        return new SliceImpl<>(collect, pageable, reviews.hasNext());
    }



    @Override
    public Page<ReviewDTO> getReview(Integer page, AdminBoardSearch adminBoardSearch) {
        Page<Review> reviews = reviewRepository.findAllWithPaging(adminBoardSearch, PageRequest.of(page, 5));
        List<ReviewDTO> reviewDTOS = reviews.getContent().stream().map(this::toReviewDTO).collect(Collectors.toList());
        return new PageImpl<>(reviewDTOS, reviews.getPageable(), reviews.getTotalElements());
    }

    @Override
    public ReviewDTO getReviewDetail(Long id) {
        return toReviewDTO(reviewRepository.findById(id).get());
    }

    @Override
    public void deleteReviewByIds(List<Long> ids) {
        reviewRepository.deleteAllById(ids);
    }
}
