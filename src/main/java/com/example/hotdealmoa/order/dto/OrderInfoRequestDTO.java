package com.example.hotdealmoa.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderInfoRequestDTO {

	@NotNull
	private Long productId;
	private Long couponId;
	@NotNull
	private Integer productCount;

	@Builder
	public OrderInfoRequestDTO(Long productId, Long couponId, Integer productCount) {
		this.productId = productId;
		this.couponId = couponId;
		this.productCount = productCount;
	}
}
