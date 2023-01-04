package com.example.hotdealmoa.review.DTO;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewSearchCondition {
	private Long productId;
	private String buyerName;

	@Builder
	public ReviewSearchCondition(Long productId, String buyerName) {
		this.productId = productId;
		this.buyerName = buyerName;
	}
}
