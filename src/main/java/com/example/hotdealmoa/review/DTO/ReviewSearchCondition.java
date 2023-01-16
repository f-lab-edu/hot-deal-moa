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
	private Long lastReviewId;

	@Builder
	public ReviewSearchCondition(Long productId, String buyerName, Long lastReviewId) {
		this.productId = productId;
		this.buyerName = buyerName;
		this.lastReviewId = lastReviewId;
	}
}
