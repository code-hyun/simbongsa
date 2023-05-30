package com.app.simbongsa.repository.board;

import com.app.simbongsa.domain.MemberDTO;
import com.app.simbongsa.search.admin.AdminBoardSearch;
import com.app.simbongsa.entity.board.FreeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

public interface FreeBoardQueryDsl {

    // 시퀀스
    public FreeBoard getCurrentSequence_QueryDsl();

    // 메인페이지 인기순 목록 조회
    public List<FreeBoard> findAllWithPopularFreeBoard();

    // 메인 페이지 자유게시판 파일
    public List<FreeBoard> findAllWithFile();

    //    인기순 목록 조회 - 무한스크롤
    public Slice<FreeBoard> findAllByLikeCountDescWithPaging_QueryDSL(Pageable pageable);

    //    최신순 목록 조회 - 무한스크롤
    public Slice<FreeBoard> findAllByIdDescWithPaging_QueryDSL(Pageable pageable);

    public Slice<FreeBoard> findByMemberIdDiary_QueryDsl(String sort, Pageable pageable);

    //    자유게시판 전체 조회(페이징)
    public Page<FreeBoard> findAllWithPaging(AdminBoardSearch adminBoardSearch, Pageable pageable);

    /* 유저가 작성한 자유게시물 조회(페이징처리) */
    public Page<FreeBoard> findByMemberId(Pageable pageable, MemberDTO memberDTO);

    //    세션에 담긴 id 값 받아와서 내가 작성한 자유 게시글 리스트 가져오기
    public List<FreeBoard> findFreeBoardListByMemberId(Pageable pageable, Long memberId);

    //    자유게시판 상세페이지 조회
    public Optional<FreeBoard> findByIdForDetail_QueryDsl(Long freeBoardId);

    /* 마이페이지 작성한 자유게시물 상세 조회*/
//    public Optional<FreeBoard> findByIdForMyDetail(Long freeBoardId);

    /*마이페이지 내가 작성한 글 전체 조회*/
    public Page<FreeBoard> findAllByFreeMemberIdPaging_QueryDsl(Pageable pageable, Long id);

    /* ===== 인기순 무한스크롤  ==== */
    public Slice<FreeBoard> findAllSliceByPopular(Pageable pageable);
    /* ===== 최신순 무한스크롤  ==== */
    public Slice<FreeBoard> findAllSliceByNew(Pageable pageable);
    /* ===== 인기순, 최신순 무한스크롤  ==== */
    public Slice<FreeBoard> findAllSliceByNewWithPopular(Pageable pageable, String keyword);

}
