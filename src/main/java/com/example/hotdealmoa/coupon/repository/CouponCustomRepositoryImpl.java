package com.example.hotdealmoa.coupon.repository;

import static com.example.hotdealmoa.coupon.domain.QCoupon.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.example.hotdealmoa.coupon.domain.Coupon;
import com.example.hotdealmoa.coupon.dto.CouponDTO;
import com.example.hotdealmoa.coupon.dto.CouponSearchCondition;
import com.example.hotdealmoa.global.common.response.PageResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CouponCustomRepositoryImpl implements CouponCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public PageResponse<CouponDTO> getCouponList(CouponSearchCondition couponSearchCondition,
		Pageable pageable) {

		List<CouponDTO> list = queryFactory.select(Projections.constructor(CouponDTO.class,
				coupon.id,
				coupon.title,
				coupon.discountPrice,
				coupon.isUsed,
				coupon.isExpired,
				coupon.expiredAt
			)).from(coupon)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.where(
				eqMemberId(couponSearchCondition.getMemberId()),
				eqIsUsed(couponSearchCondition.getIsUsed()),
				eqIsExpired(couponSearchCondition.getIsExpired())
			)
			.fetch();

		JPAQuery<Long> count = queryFactory.select(coupon.count())
			.from(coupon)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.where(
				eqMemberId(couponSearchCondition.getMemberId()),
				eqIsUsed(couponSearchCondition.getIsUsed()),
				eqIsExpired(couponSearchCondition.getIsExpired())
			);

		return PageResponse.of(PageableExecutionUtils.getPage(list, pageable, count::fetchFirst));
	}

	@Override
	public List<Coupon> getUsedOrExpiredCouponList() {
		return queryFactory
			.selectFrom(coupon)
			.where(
				coupon.expiredAt.before(LocalDateTime.now())
					.or(coupon.isUsed.eq(true))
					.and(coupon.isExpired.eq(false))
			)
			.fetch();
	}

	@Override
	public long updateExpiredCoupon(List<Coupon> coupons) {
		return queryFactory
			.update(coupon)
			.set(coupon.isExpired, true)
			.where(coupon.in(coupons))
			.execute();
	}

	private BooleanExpression eqMemberId(Long memberId) {
		return memberId != null ? coupon.memberId.eq(memberId) : null;
	}

	private BooleanExpression eqIsUsed(Boolean isUsed) {
		return isUsed != null ? coupon.isUsed.eq(isUsed) : null;
	}

	private BooleanExpression eqIsExpired(Boolean isExpired) {
		return isExpired != null ? coupon.isExpired.eq(isExpired) : null;
	}

}
