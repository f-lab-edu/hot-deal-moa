package com.example.hotdealmoa.order.dto;

import com.example.hotdealmoa.coupon.domain.Coupon;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderInfoResponseDTO {

	private Long memberId;
	private String name;
	private String email;
	private String phoneNumber;
	private String address;
	private Long productId;
	private String productTitle;
	private Integer productCount;
	private String productMainImg;
	private Integer totalPrice;
	private Integer deliveryFee;
	private Long couponId;
	private Integer discountPrice;

	@Builder
	public OrderInfoResponseDTO(Long memberId, String name, String email, String phoneNumber, String address,
		Long productId, String productTitle, Integer productCount, String productMainImg, Integer totalPrice,
		Integer deliveryFee, Long couponId, Integer discountPrice) {
		this.memberId = memberId;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.productId = productId;
		this.productTitle = productTitle;
		this.productCount = productCount;
		this.productMainImg = productMainImg;
		this.totalPrice = totalPrice;
		this.deliveryFee = deliveryFee;
		this.couponId = couponId;
		this.discountPrice = discountPrice;
	}

	public void updateCouponInfo(Coupon coupon) {
		this.couponId = coupon.getId();
		this.discountPrice = coupon.getDiscountPrice();
	}

}
