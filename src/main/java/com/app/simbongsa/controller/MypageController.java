package com.app.simbongsa.controller;

import com.app.simbongsa.domain.*;
import com.app.simbongsa.entity.board.FreeBoard;
import com.app.simbongsa.provider.UserDetail;
import com.app.simbongsa.repository.board.FreeBoardRepository;
import com.app.simbongsa.search.admin.AdminFundingSearch;
import com.app.simbongsa.service.board.FreeBoardService;
import com.app.simbongsa.service.board.ReviewService;
import com.app.simbongsa.service.funding.FundingService;
import com.app.simbongsa.service.inquiry.AnswerService;
import com.app.simbongsa.service.inquiry.InquiryService;
import com.app.simbongsa.service.member.MemberService;
import com.app.simbongsa.service.rice.RicePaymentService;
import com.app.simbongsa.service.support.SupportRequestService;
import com.app.simbongsa.service.support.SupportService;
import com.app.simbongsa.service.volunteer.VolunteerWorkActivityService;
import com.app.simbongsa.service.volunteer.VolunteerWorkService;
import com.app.simbongsa.type.InquiryType;
import com.app.simbongsa.type.RicePaymentType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.Arrays;

@Controller
@RequestMapping("/mypage/*")
@RequiredArgsConstructor
@Slf4j
public class MypageController {
    private final InquiryService inquiryService;
    private final AnswerService answerService;
    private final SupportRequestService supportRequestService;
    private final FreeBoardService freeBoardService;
    private final FreeBoardRepository freeBoardRepository;
    private final MemberService memberService;
    private final RicePaymentService ricePaymentService;
    private final VolunteerWorkActivityService volunteerWorkActivityService;
    private final SupportService supportService;
    private final FundingService fundingService;
    private final PasswordEncoder passwordEncoder;
    private final ReviewService reviewService;

    /* 내 문의 페이징처리해서 불러오기 */
    @GetMapping("my-question")
    public String notice(Integer page, Model model, HttpSession httpSession, @AuthenticationPrincipal UserDetail userDetail) {
        page = page == null ? 0 : page - 1;
        MemberDTO memberDTO = (MemberDTO)httpSession.getAttribute("member");
/*        if(memberDTO != null){

        }*/

        Page<InquiryDTO> myInquiries = inquiryService.getMyInquiry(page, memberDTO.getId());

        log.info(myInquiries.toString() + "asdfasaaaaaaaddddddddddddddddddd");

        model.addAttribute("memberDTO", memberDTO);
        model.addAttribute("myInquiries", myInquiries.getContent());
        model.addAttribute("pageDTO", new PageDTO(myInquiries));
        return "mypage/my-question";
    }

    /* 내 문의 게시글 수정 */
    @PostMapping("my-question-update")
    public RedirectView myQuestionUpdate(Long id, String inquiryTitle, String inquiryContent,HttpSession httpSession, @AuthenticationPrincipal UserDetail userDetail) {
        MemberDTO memberDTO = (MemberDTO)httpSession.getAttribute("member");
        InquiryDTO inquiryDTO = InquiryDTO.builder().id(id).inquiryTitle(inquiryTitle).inquiryContent(inquiryContent)
                .inquiryStatus(InquiryType.답변대기).memberDTO(memberService.getMemberById(memberDTO.getId())).build();
        inquiryService.setInquiry(inquiryDTO);
        log.info("문의 게시글 수정에서의 inquiryDTO: ", inquiryDTO.toString());
        return new RedirectView("my-question");
    }

    /* 내 후원요청목록 페이징처리해서 불러오기 */
    @GetMapping("support-request")
    public String supportRequest(Integer page, Model model,HttpSession httpSession, @AuthenticationPrincipal UserDetail userDetail){
        MemberDTO memberDTO = (MemberDTO)httpSession.getAttribute("member");
        page = page == null ? 0 : page - 1;
        Page<SupportRequestDTO> mySupportRequests = supportRequestService.getMySupportRequest(page, memberDTO);

        model.addAttribute("memberDTO", memberDTO);
        model.addAttribute("mySupportRequests", mySupportRequests.getContent());
        model.addAttribute("pageDTO",new PageDTO(mySupportRequests));
        return "mypage/support-request";
    }

    /* 공양미 환불 요청 */
    @GetMapping("exchange-request")
    public String exchangeRequest(RicePaymentDTO ricePaymentDTO, HttpSession session, Model model){
        MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");

        model.addAttribute("memberDTO", memberDTO);
        model.addAttribute("enableRice", ricePaymentService.findEnableRiceById(memberDTO.getId()));
        return "mypage/exchange-request";
    }

    /*환전 요청 값 넣기*/
    @PostMapping("exchange-request")
    public RedirectView exchangeRequest(RicePaymentDTO ricePaymentDTO, HttpSession session) {
        MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
        ricePaymentDTO.setMemberDTO(memberDTO);
        ricePaymentDTO.setRicePaymentStatus(RicePaymentType.환전대기);

        ricePaymentService.insertExchangeRequest(ricePaymentDTO, memberDTO);

        return new RedirectView("/mypage/rice-list");
    }

    /* 내가 만든 펀딩 목록 */
    @GetMapping("my-funding-list")
    public String myFundingList(Integer page, Model model,HttpSession httpSession, @AuthenticationPrincipal UserDetail userDetail){
        MemberDTO memberDTO = (MemberDTO)httpSession.getAttribute("member");
        page = page == null ? 0 : page - 1;
        Page<FundingDTO> myFundings = fundingService.getMyFunding(page, memberDTO);
        log.info( "myFundings 잘 나오나요" + myFundings.toString());

        model.addAttribute("memberDTO", memberDTO);
        model.addAttribute("myFundings", myFundings.getContent());
        model.addAttribute("pageDTO", new PageDTO(myFundings));
        return "/error/unReady";
    }

/*    @GetMapping("my-question")
    public String myQuestion(){
        return "mypage/my-question";
    }*/

    /* 내 자유 게시글 목록 */
    @GetMapping("my-freeboard")
    public String myFreeBoard(Integer page, Model model,HttpSession httpSession, @AuthenticationPrincipal UserDetail userDetail){
        MemberDTO memberDTO = (MemberDTO)httpSession.getAttribute("member");
        log.info(memberDTO.getId() + "아이디아이디아이디아이디아이디아이디아이디아이디아이디아이디아이디");
        page = page == null ? 0 : page - 1;
        log.info(page + "pagepagepagepapgapgagpdspagpsdgpasdpgapsppage");
        Page<FreeBoardDTO> myFreeBoards = freeBoardService.getMyFreeBoards(page, memberDTO);
        log.info( "freeBoardDTO 잘 나오나요" + myFreeBoards);

        model.addAttribute("memberDTO", memberDTO);
        model.addAttribute("myFreedBoards", myFreeBoards.getContent());
        model.addAttribute("total", myFreeBoards.getTotalElements());
        model.addAttribute("pageDTO", new PageDTO(myFreeBoards));
        return "/mypage/my-freeboard";
    }

    /* 내 후기 게시글 목록 */
    @GetMapping("my-review")
    public String myReview(Integer page, Model model,HttpSession httpSession, @AuthenticationPrincipal UserDetail userDetail){
        MemberDTO memberDTO = (MemberDTO)httpSession.getAttribute("member");
        log.info(memberDTO.getId() + "아이디아이디아이디아이디아이디아이디아이디아이디아이디아이디아이디");
        page = page == null ? 0 : page - 1;
        log.info(page + "pagepagepagepapgapgagpdspagpsdgpasdpgapsppage");
        Page<ReviewDTO> myReviews = reviewService.getMyReviewBoards(page, memberDTO);
        log.info( "myReviews 잘 나오나요" + myReviews);

        model.addAttribute("memberDTO", memberDTO);
        model.addAttribute("myReviews", myReviews.getContent());
        model.addAttribute("total", myReviews.getTotalElements());
        model.addAttribute("pageDTO", new PageDTO(myReviews));
        return "/mypage/my-review";
    }

    /* 내 자유 게시글 상세보기 */
    @GetMapping("my-free-board-detail/{boardId}")
    public String getMyFreeBoardetail(@PathVariable("boardId") Long boardId, Model model){
        FreeBoardDTO freeBoardDTO = freeBoardService.getFreeBoard(boardId);

        model.addAttribute("freeBoardDTO", freeBoardDTO);
        return "community/free-board-modify";
    }

    /* 내 후원 목록 */
    @GetMapping("my-support-list")
    public String mySupportListSupport(Integer page, HttpSession session, Model model){
        MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");

        page = page == null ? 0 : page - 1;

        Page<SupportDTO> supports = supportService.getSupportById(page, memberDTO.getId());

        model.addAttribute("memberDTO", memberDTO);
        model.addAttribute("supportDTOS", supports.getContent());
        model.addAttribute("pageDTO", new PageDTO(supports));

        return "mypage/my-support-list";
    }

    /* 내 펀딩후원 목록 */
    @GetMapping("my-support-list/funding")
    public String mySupportListFunding(Integer page, HttpSession session, Model model){
        MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");

        page = page == null ? 0 : page - 1;

        Page<FundingPaymentDTO> fundingPaymentDTOS = fundingService.getFundingSupportByMemberId(page, memberDTO.getId());

        model.addAttribute("memberDTO", memberDTO);
        model.addAttribute("fundingPaymentDTOS", fundingPaymentDTOS.getContent());
        model.addAttribute("pageDTO", new PageDTO(fundingPaymentDTOS));

        return "/error/unReady";
    }

    /* 공양미 충전 */
    @GetMapping("rice-charge")
    public String riceCharge(Model model, HttpSession session){
        MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
        model.addAttribute("memberDTO", memberDTO);
        return "mypage/rice-charge";
    }

    /* 내 공양미 조회(페이징) */
    @GetMapping("rice-list")
    public String riceList(Integer page, Model model, HttpSession session){
        page = page == null ? 0 : page - 1;

        MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
        Page<RicePaymentDTO> myRice = ricePaymentService.getMyRicePayment(page, memberDTO);

        log.info(myRice.toString() + "------------내 공양미 리스트-------------- ");
        model.addAttribute("memberDTO", memberDTO);
        model.addAttribute("myRicePayments", myRice.getContent());
        model.addAttribute("pageDTO", new PageDTO(myRice));
        return "mypage/rice-list";
    }

/*    @GetMapping("support-request")
    public String supportRequest(){
        return "mypage/support-request";
    }*/

    /* 회원정보수정 페이지 */
    @GetMapping("user-modify")
    public String updateMemberInfo(@AuthenticationPrincipal UserDetail userDetail, Model model,HttpSession httpSession) {
        MemberDTO memberDTO = (MemberDTO) httpSession.getAttribute("member");
        Long memberId = memberDTO.getId();
        log.info(memberService.getMemberById(memberId) + "memberDTO 들어오나");
        model.addAttribute("memberDTO", memberService.getMemberById(memberId));
        return "/mypage/user-modify";
    }

    /* 회원정보 수정 */
    @PostMapping("user-modify")
    public RedirectView updateMemberInfo(String memberPassword, String memberName, String memberAddress, Integer memberAge, String memberInterest, HttpSession session) {
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("member");
        MemberDTO updateInfo = new MemberDTO();
        log.info(memberPassword);
        log.info(memberName);
        log.info(memberAddress);
        log.info(memberAge+"나이");
        log.info(memberInterest);
        // 기존 memberDTO 값을 수정하여 필요한 정보만 업데이트
        updateInfo.setMemberPassword(memberPassword);
        updateInfo.setMemberName(memberName);
        updateInfo.setMemberAddress(memberAddress);
        updateInfo.setMemberAge(memberAge);
        updateInfo.setMemberInterest(memberInterest);

        log.info("contorller updateInfo: =================" + updateInfo);
        memberService.updateMyInfo(memberDTO,updateInfo, passwordEncoder);
        return new RedirectView("/mypage/user-modify?result=ok");
    }

    /* 봉사활동 내역 */
    @GetMapping("volunteer-work-list")
    public String volunteerWorkList(Integer page, Model model,HttpSession httpSession, @AuthenticationPrincipal UserDetail userDetail){
        MemberDTO memberDTO = (MemberDTO)httpSession.getAttribute("member");
        Long memberId = memberDTO.getId();
        page = page == null ? 0 : page - 1;
        Page<VolunteerWorkActivityDTO> myActivity = volunteerWorkActivityService.getMyVolunteerWork(page, memberId);

        model.addAttribute("memberDTO", memberDTO);
        model.addAttribute("myActivity",myActivity.getContent());
        model.addAttribute("pageDTO",new PageDTO(myActivity));
        return "mypage/volunteer-work-list";
    }

    /* 탈퇴확인 */
    @GetMapping("withdraw-check")
    public String withdrawCheck(){
        return "mypage/withdraw-check";
    }

    /* 탈퇴로그인 */
    @GetMapping("withdraw-login")
    public String withdrawLogin(HttpSession session, Model model){
        MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
        model.addAttribute("memberEmail", memberDTO.getMemberEmail());
        return "mypage/withdraw-login";
    }

    /* 환전요청 */
    @PostMapping("rices-charge")
    @ResponseBody
    public void riceCharge(Integer ricePaymentUsed, HttpSession session) {
        MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");

        ricePaymentService.insertRicePayment(ricePaymentUsed, memberDTO);
    }

    /* 탈퇴 */
    @PostMapping("withdraw")
    public RedirectView withdraw(HttpSession session) {
        MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");

        memberService.updateStatusByIds(Arrays.asList(memberDTO.getId()));
        return new RedirectView("/member/logout");
    }
}
