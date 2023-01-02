package com.example.hotdealmoa.review.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewUpdateRequestDTO {
	private String reviewImg;

	@NotNull
	@Max(value = 5)
	private Integer star;
	@NotBlank
	private String content;

	@Builder
	public ReviewUpdateRequestDTO(String reviewImg, Integer star, String content) {
		this.reviewImg = reviewImg;
		this.star = star;
		this.content = content;
	}
}
