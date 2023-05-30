package com.app.simbongsa.service.board;

import com.app.simbongsa.domain.*;
import com.app.simbongsa.entity.board.Review;
import com.app.simbongsa.entity.board.ReviewReply;
import com.app.simbongsa.entity.file.ReviewFile;
import com.app.simbongsa.entity.member.Member;
import com.app.simbongsa.search.admin.AdminBoardSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.ArrayList;
import java.util.List;

public interface ReviewService {
    /*마이페이지 게시물 목록 조회*/
    public Page<ReviewDTO> getReviewForMemberIdList(Pageable pageable, Long id);

    /*수정*/
    public void update(ReviewDTO reviewDTO);

    /*삭제*/
    public void delete(Long reviewId);

    /*저징*/
    public void register(ReviewDTO reviewDTO);

    /*상세보기*/
    public ReviewDTO getReview(Long reviewId);

    /*시퀀스 가져오기*/
    public Review getCurrentSequence();

    /*댓글 작성*/
    public void registerReply(ReplyRequestDTO replyRequestDTO);

    /*댓글 삭제*/
    public void deleteReply(Long replyId);

    /*댓글 목록*/
    public Slice<ReviewReplyDTO> getReplyList(Long reviewId, int page);

    /*댓글 갯수*/
    public Integer getReplyCount(Long reviewId);

    /*최신순 목록*/
    public Slice<ReviewDTO> getNewReviewList(Pageable pageable);

    /*인기순 목록*/
    public Slice<ReviewDTO> getLikesReviewList(Pageable pageable);

    /*작성하기*/
    public void write(ReviewDTO reviewDTO, Long memberId);

//    목록 전체 조회(페이징)
    public Page<ReviewDTO> getReview(Integer page, AdminBoardSearch adminBoardSearch);

//    게시판 상세보기
    public ReviewDTO getReviewDetail(Long id);

//    게시판 삭제
    public void deleteReviewByIds(List<Long> ids);

    /* 내 후기 게시물 목록 조회 (페이징처리) */
    Page<ReviewDTO> getMyReviewBoards(Integer page, MemberDTO memberDTO);

//    댓글 갯수
    public Long getCount(Long id);


    default ReviewDTO toReviewDTO(Review review) {
        ReviewDTO.ReviewDTOBuilder builder = ReviewDTO.builder()
                .id(review.getId())
                .boardTitle(review.getBoardTitle())
                .boardContent(review.getBoardContent())
                .createdDate(review.getCreatedDate())
                .updatedDate(review.getUpdatedDate())
                .memberDTO(toMemberDTO(review.getMember()))
                .fileDTOS(FileToDTO(review.getReviewFiles()))
                .replyCount(review.getReviewReplyCount());

        if (review.getMember() != null) {
            builder.memberDTO(toMemberDTO(review.getMember()));
        }

        return builder.build();
    }

    default MemberDTO toMemberDTO(Member member){
        return MemberDTO.builder()
                .id(member.getId())
                .memberRank(member.getMemberRank())
                .memberName(member.getMemberName())
                .memberVolunteerTime(member.getMemberVolunteerTime())
                .memberAddress(member.getMemberAddress())
                .memberEmail(member.getMemberEmail())
                .memberAge(member.getMemberAge())
                .memberPassword(member.getMemberPassword())
                .memberInterest(member.getMemberInterest())
                .memberJoinType(member.getMemberJoinType())
                .memberRice(member.getMemberRice())
                .memberRole(member.getMemberRole())
                .memberStatus(member.getMemberStatus())
                .build();
    }

    default List<FileDTO> FileToDTO(List<ReviewFile> reviewFiles){
        List<FileDTO> reviewFileList = new ArrayList<>();
        reviewFiles.forEach(
                reviewFile -> {
                    FileDTO fileDTO = FileDTO.builder()
                            .id(reviewFile.getId())
                            .fileName(reviewFile.getFileName())
                            .filePath(reviewFile.getFilePath())
                            .fileRepresentationalType(reviewFile.getFileRepresentationalType())
                            .fileUuid(reviewFile.getFileUuid())
                            .build();
                    reviewFileList.add(fileDTO);
                }
        );
        return reviewFileList;
    }

    default ReviewDTO reviewToDTO(Review review){
        return ReviewDTO.builder()
                .id(review.getId())
                .memberDTO(toMemberDTO(review.getMember()))
                .createdDate(review.getCreatedDate())
                .updatedDate(review.getUpdatedDate())
                .boardTitle(review.getBoardTitle())
                .boardContent(review.getBoardContent())
                .replyCount(review.getReviewReplyCount())
                .build();
    }

    default Review toReviewEntity(ReviewDTO reviewDTO){
        return Review.builder()
                .boardTitle(reviewDTO.getBoardTitle())
                .boardContent(reviewDTO.getBoardContent())
                .member(toMemberEntity(reviewDTO.getMemberDTO()))
                .build();
    }

    default Member toMemberEntity(MemberDTO memberDTO){
        return Member.builder()
                .id(memberDTO.getId())
                .memberName(memberDTO.getMemberName())
                .memberJoinType(memberDTO.getMemberJoinType())
                .memberRank(memberDTO.getMemberRank())
                .memberAddress(memberDTO.getMemberAddress())
                .memberAge(memberDTO.getMemberAge())
                .memberEmail(memberDTO.getMemberEmail())
                .memberPassword(memberDTO.getMemberPassword())
                .memberRice(memberDTO.getMemberRice())
                .memberInterest(memberDTO.getMemberInterest())
                .memberRole(memberDTO.getMemberRole())
                .memberStatus(memberDTO.getMemberStatus())
                .memberVolunteerTime(memberDTO.getMemberVolunteerTime())
                .randomKey(memberDTO.getRandomKey())
                .build();
    }

    default ReviewFile toReviewFileEntity(FileDTO fileDTO){
        return ReviewFile.builder()
//                .id(fileDTO.getId())
                .fileName(fileDTO.getFileName())
                .fileUuid(fileDTO.getFileUuid())
                .filePath(fileDTO.getFilePath())
                .fileRepresentationalType(fileDTO.getFileRepresentationalType())
                .review(fileDTO.getReview())
                .build();
    }

    default ReplyDTO toReplyDTO(ReviewReply reviewReply){
        return ReplyDTO.builder()
                .id(reviewReply.getId())
                .memberDTO(toMemberDTO(reviewReply.getMember()))
                .registerDate(reviewReply.getCreatedDate())
                .replyContent(reviewReply.getReplyContent())
                .build();
    }

    default ReviewReplyDTO toReviewReplyDTO(ReviewReply reviewReply){
        return ReviewReplyDTO.builder().id(reviewReply.getId())
                .memberDTO(toMemberDTO(reviewReply.getMember()))
                .reviewReplyContent(reviewReply.getReplyContent())
                .createdDate(reviewReply.getCreatedDate())
                .updatedDate(reviewReply.getUpdatedDate())
                .build();
    }
}
