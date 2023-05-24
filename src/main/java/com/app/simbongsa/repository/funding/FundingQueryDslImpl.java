package com.app.simbongsa.repository.funding;

import com.app.simbongsa.entity.board.Review;
import com.app.simbongsa.entity.file.QFundingFile;
import com.app.simbongsa.entity.funding.*;
import com.app.simbongsa.entity.support.Support;
import com.app.simbongsa.entity.support.SupportRequest;
import com.app.simbongsa.search.admin.AdminFundingSearch;
import com.app.simbongsa.type.FundingCategoryType;
import com.app.simbongsa.type.RequestType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.app.simbongsa.entity.board.QReview.review;
import static com.app.simbongsa.entity.file.QFundingFile.fundingFile;
import static com.app.simbongsa.entity.funding.QFunding.funding;
import static com.app.simbongsa.entity.funding.QFundingGift.fundingGift;
import static com.app.simbongsa.entity.funding.QFundingPayment.fundingPayment;
import static com.app.simbongsa.entity.member.QMember.member;

@RequiredArgsConstructor
public class FundingQueryDslImpl implements FundingQueryDsl {
    private final JPAQueryFactory query;

    //    메인페이지 인기펀딩
    @Override
    public List<Funding> findAllWithPopular() {
        return query.select(funding)
                .from(funding)
                .leftJoin(funding.fundingFile, fundingFile)
                .fetchJoin()
                .where(funding.fundingTargetPrice.ne(0))
                .orderBy(funding.fundingCurrentPrice.divide(funding.fundingTargetPrice).multiply(100).desc())
                .fetch();
    }
    /*.join(funding.fundingFile).fetchJoin()*/

    //    펀딩 전체 조회(페이징)
    @Override
    public Page<Funding> findAllWithPaging(AdminFundingSearch adminFundingSearch, Pageable pageable) {
        BooleanExpression fundingTitleLike = adminFundingSearch.getFundingTitle() == null ? null : funding.fundingTitle.like("%" + adminFundingSearch.getFundingTitle() + "%");
        BooleanExpression creatorNameLike = adminFundingSearch.getCreatorNickName() == null ? null : funding.fundingCreator.fundingCreatorNickname.like("%" + adminFundingSearch.getCreatorNickName() + "%");

        List<Funding> foundFunding = query.select(funding)
                .from(funding)
                .where(fundingTitleLike, creatorNameLike)
                .orderBy(funding.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(funding.count())
                .from(funding)
                .where(fundingTitleLike, creatorNameLike)
                .fetchOne();

        return new PageImpl<>(foundFunding, pageable, count);
    }

    @Override
    public Optional<Funding> findByIdForDetail_QueryDsl(Long fundingId) {

        Funding foundFunding = query.select(funding)
                        .from(funding)
                        .leftJoin(funding.fundingFile, fundingFile)
                        .fetchJoin()
                        .leftJoin(funding.member, member)
                        .fetchJoin()
                        .where(funding.id.eq(fundingId))
                        .fetchOne();

        return Optional.ofNullable(foundFunding);
    }

    /* 내 펀딩 내역 조회(페이징처리) */
    @Override
    public Page<Funding> findByMemberId_QueryDSL(Pageable pageable, Long memberId) {
        /*memberService.getPays((Long)session.getAttribute("memberId"));*/

        List<Funding> foundFunding = query.select(funding)
                .from(funding)
                .join(funding.fundingFile)
                .fetchJoin()
                .where(funding.member.id.eq(memberId))
                .orderBy(funding.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(funding.count())
                .from(funding)
                .where(funding.member.id.eq(memberId))
                .fetchOne();

        return new PageImpl<>(foundFunding, pageable, count);
    }

    //    펀딩 승인, 펀딩 대기 수 구하기
    @Override
    public Long findCountAcceptOrWait(RequestType requestType) {
        return query.select(funding.count())
                .from(funding)
                .where(funding.fundingStatus.eq(requestType))
                .fetchOne();
    }


    //    펀딩 대기를 승인으로 변경
    @Override
    public void updateWaitToAcceptByIds(List<Long> ids, RequestType requestType) {
        query.update(funding)
                .set(funding.fundingStatus, requestType)
                .where(funding.id.in(ids))
                .execute();
    }

    //펀딩 후원하기
    @Override
    public FundingGift findSupport_QueryDsl(Long giftId) {
        return query.select(fundingGift)
                .from(fundingGift)
                .join(fundingGift.funding, funding)
                .leftJoin(fundingGift.funding.fundingFile)
                .join(fundingGift.funding.member)
                .leftJoin(fundingGift.fundingGiftItems)
                .fetchJoin()
                .where(fundingGift.id.eq(giftId))
                .fetchOne();
    }


    //    펀딩 전체 조회(무한스크롤)
    @Override
    public Slice<Funding> findAllWithSlice_QueryDsl(Pageable pageable) {
        List<Funding> fundings = query.select(funding)
                .from(funding)
                .leftJoin(funding.fundingFile, fundingFile)
                .fetchJoin()
                .orderBy(funding.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        // 펀딩 전체 목록 갯수
//        Long count = query.select(funding.count())
//                .from(funding)
//                .fetchOne();

        //  hasNext는 현재 페이지에서 다음 페이지가 있는지 여부를 나타내는 불리언(Boolean) 값, true로 설정되면 다음 페이지가 있는 것으로 간주되고,
        //   false로 설정되면 다음 페이지가 없는 것으로 간주
        //    hasNext true인지 false인지 체크하는 메소드(마지막 페이지 체크)
        boolean hasNext = false;
        if (fundings.size() > pageable.getPageSize()) {
            fundings.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(fundings, pageable, hasNext);
    }

    @Override
    public Long getFundingCount_QueryDsl(Long fundingId) {
        return query.select(funding.count())
                .from(funding)
                .fetchOne();

    }
////      false로 설정되면 다음 페이지가 없는 것으로 간주
//    }
//    //    hasNext true인지 false인지 체크하는 메소드(마지막 페이지 체크)
//    private Slice<Funding> checkLastPage(Pageable pageable,  List<Funding> fundings) {
//        boolean hasNext = false;
//        // 조회한 결과 개수가 요청한 페이지 사이즈보다 크면 뒤에 더 있음, next = true
//        if (fundings.size() > pageable.getPageSize()) {
//            hasNext = true;
//            fundings.remove(pageable.getPageSize());
//        }
//        return new SliceImpl<>(fundings, pageable, hasNext);
//    }
//
//}


    @Override
    public Long updateFunding_QueryDsl(Long fundingId, int fundingTargetPrice, LocalDate fundingStartDate, LocalDate fundingEndDate) {
      return  query.update(funding)
                .set(funding.fundingTargetPrice, fundingTargetPrice)
                .set( funding.fundingStartDate, fundingStartDate)
                .set(funding.fundingEndDate, fundingEndDate)
                .where(funding.id.eq(fundingId))
                .execute();
    }


    @Override
    public Funding getCurrentSequence_QueryDsl() {
        return query.select(funding)
                .from(funding)
                .orderBy(funding.id.desc())
                .limit(1)
                .fetchOne();
    }

    @Override
    public Page<FundingPayment> findByMemberId(Pageable pageable, Long id) {
        List<FundingPayment> foundFundingPayment = query.select(fundingPayment)
                .from(fundingPayment)
                .where(fundingPayment.member.id.eq(id))
                .orderBy(fundingPayment.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(fundingPayment.count())
                .from(fundingPayment)
                .where(fundingPayment.member.id.eq(id))
                .fetchOne();

        return new PageImpl<>(foundFundingPayment, pageable, count);
    }
}