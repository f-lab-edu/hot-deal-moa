package com.example.hotdealmoa.review.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewCreateRequestDTO {
	private String reviewImg;

	@NotBlank
	@Max(5)
	private Integer star;

	@NotBlank
	private String content;

	@NotBlank
	private Long productId;

	@Builder
	public ReviewCreateRequestDTO(String reviewImg, Integer star, String content, Long productId) {
		this.reviewImg = reviewImg;
		this.star = star;
		this.content = content;
		this.productId = productId;
	}
}
