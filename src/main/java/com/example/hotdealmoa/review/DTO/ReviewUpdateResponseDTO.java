package com.example.hotdealmoa.review.DTO;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewUpdateResponseDTO {
	private String reviewImg;
	private Integer star;
	private String content;

	@Builder
	public ReviewUpdateResponseDTO(String reviewImg, Integer star, String content) {
		this.reviewImg = reviewImg;
		this.star = star;
		this.content = content;
	}
}
