package com.example.hotdealmoa.coupon.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponSearchCondition {

	private Long memberId;
	private Long lastCouponId;
	private Boolean isUsed;

	private Boolean isExpired;

	@Builder
	public CouponSearchCondition(Long memberId, Long lastCouponId, Boolean isUsed, Boolean isExpired) {
		this.memberId = memberId;
		this.lastCouponId = lastCouponId;
		this.isUsed = isUsed;
		this.isExpired = isExpired;
	}
}
