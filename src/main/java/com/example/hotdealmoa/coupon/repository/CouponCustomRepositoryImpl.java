package com.example.hotdealmoa.coupon.repository;

import static com.example.hotdealmoa.coupon.domain.QCoupon.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.hotdealmoa.coupon.domain.Coupon;
import com.example.hotdealmoa.coupon.dto.CouponDTO;
import com.example.hotdealmoa.coupon.dto.CouponSearchCondition;
import com.example.hotdealmoa.global.common.response.SliceResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CouponCustomRepositoryImpl implements CouponCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public SliceResponse<CouponDTO> getCouponList(CouponSearchCondition couponSearchCondition,
		Pageable pageable) {

		List<CouponDTO> list = queryFactory.select(Projections.constructor(CouponDTO.class,
				coupon.id,
				coupon.title,
				coupon.discountPrice,
				coupon.isUsed,
				coupon.isExpired,
				coupon.expiredAt
			)).from(coupon)
			.limit(pageable.getPageSize() + 1)
			.where(
				eqMemberId(couponSearchCondition.getMemberId()),
				eqIsUsed(couponSearchCondition.getIsUsed()),
				eqIsExpired(couponSearchCondition.getIsExpired()),
				ltCouponId(couponSearchCondition.getLastCouponId())
			)
			.orderBy(coupon.createdAt.desc())
			.fetch();

		return SliceResponse.of(list, pageable);
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

	private BooleanExpression ltCouponId(Long id) {
		return id == null ? null : coupon.id.lt(id);
	}

}
