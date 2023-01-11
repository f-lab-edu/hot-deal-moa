package com.example.hotdealmoa.review.DTO;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewDTO {
	private Long id;
	private String reviewImg;
	private Integer star;
	private String content;
	private String buyerName;
	private String productName;
	private Integer orderCount;

	@Builder
	public ReviewDTO(Long id, String reviewImg, Integer star, String content, String buyerName, String productName,
		Integer orderCount) {
		this.id = id;
		this.reviewImg = reviewImg;
		this.star = star;
		this.content = content;
		this.buyerName = buyerName;
		this.productName = productName;
		this.orderCount = orderCount;
	}
}
