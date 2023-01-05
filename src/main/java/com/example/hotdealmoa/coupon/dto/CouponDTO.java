package com.example.hotdealmoa.coupon.dto;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponDTO {

	private Long id;
	private String title;
	private Integer discountPrice;
	private Boolean isUsed;
	private Boolean isExpired;
	private LocalDateTime expiredAt;

	@Builder
	public CouponDTO(Long id, String title, Integer discountPrice, Boolean isUsed, Boolean isExpired,
		LocalDateTime expiredAt) {
		this.id = id;
		this.title = title;
		this.discountPrice = discountPrice;
		this.isUsed = isUsed;
		this.isExpired = isExpired;
		this.expiredAt = expiredAt;
	}
}
