package com.example.hotdealmoa.product.dto;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDTO {
	private Long id;
	private String title;
	private String mainImg;
	private Integer stock;
	private Double starAverage;
	private Long reviewCount;
	private Integer totalPrice;
	private LocalDateTime startAt;
	private LocalDateTime endAt;
	private String categoryName;

	@Builder
	public ProductDTO(Long id, String title, String mainImg, Integer stock, Double starAverage, Long reviewCount,
		Integer totalPrice, LocalDateTime startAt, LocalDateTime endAt, String categoryName) {
		this.id = id;
		this.title = title;
		this.mainImg = mainImg;
		this.stock = stock;
		this.starAverage = starAverage;
		this.reviewCount = reviewCount;
		this.totalPrice = totalPrice;
		this.startAt = startAt;
		this.endAt = endAt;
		this.categoryName = categoryName;
	}
}
