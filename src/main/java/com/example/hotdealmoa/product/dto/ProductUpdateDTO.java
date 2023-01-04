package com.example.hotdealmoa.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductUpdateDTO {
	@NotBlank
	private String title;
	@NotBlank
	private String content;
	private String mainImg;
	private String detailImg;
	@NotNull
	@PositiveOrZero
	private Integer stock;
	@NotNull
	@PositiveOrZero
	private Integer totalPrice;
	@NotNull
	@PositiveOrZero
	private Integer deliveryFee;
	@NotNull
	private Long categoryId;

	@Builder
	public ProductUpdateDTO(String title, String content, String mainImg, String detailImg,
		Integer stock, Integer totalPrice, Integer deliveryFee, Long categoryId) {
		this.title = title;
		this.content = content;
		this.mainImg = mainImg;
		this.detailImg = detailImg;
		this.stock = stock;
		this.totalPrice = totalPrice;
		this.deliveryFee = deliveryFee;
		this.categoryId = categoryId;
	}
}
