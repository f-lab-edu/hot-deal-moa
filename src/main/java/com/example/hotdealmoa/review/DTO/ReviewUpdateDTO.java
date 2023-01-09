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
public class ReviewUpdateDTO {
	private String reviewImg;

	@NotNull
	@Max(value = 5)
	@PositiveOrZero
	private Integer star;
	@NotBlank
	private String content;

	@Builder
	public ReviewUpdateDTO(String reviewImg, Integer star, String content) {
		this.reviewImg = reviewImg;
		this.star = star;
		this.content = content;
	}
}
