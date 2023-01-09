package com.example.hotdealmoa.review.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewCreateRequestDTO {
	private String reviewImg;

	@NotNull
	@Max(value = 5)
	@PositiveOrZero
	private Integer star;

	@NotBlank
	private String content;

	@NotNull
	private Long productId;

	@NotNull
	private Long orderId;

	@Builder
	public ReviewCreateRequestDTO(String reviewImg, Integer star, String content, Long productId, Long orderId) {
		this.reviewImg = reviewImg;
		this.star = star;
		this.content = content;
		this.productId = productId;
		this.orderId = orderId;
	}
}
