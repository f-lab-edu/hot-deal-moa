package com.example.hotdealmoa.product.dto;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDetailDTO {
	private Long id;
	private String title;
	private String content;
	private String mainImg;
	private String detailImg;
	private Double starAvg;
	private Long reviewCount;
	private Integer stock;
	private Integer totalPrice;
	private Integer deliveryFee;
	private LocalDateTime startAt;
	private LocalDateTime endAt;
	private String categoryName;

	@Builder
	public ProductDetailDTO(Long id, String title, String content, String mainImg, String detailImg, Double starAvg,
		Long reviewCount, Integer stock, Integer totalPrice, Integer deliveryFee, LocalDateTime startAt,
		LocalDateTime endAt, String categoryName) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.mainImg = mainImg;
		this.detailImg = detailImg;
		this.starAvg = starAvg;
		this.reviewCount = reviewCount;
		this.stock = stock;
		this.totalPrice = totalPrice;
		this.deliveryFee = deliveryFee;
		this.startAt = startAt;
		this.endAt = endAt;
		this.categoryName = categoryName;
	}
}
