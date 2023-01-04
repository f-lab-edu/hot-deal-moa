package com.example.hotdealmoa.product.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductCreateRequestDTO {

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
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime startAt;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime endAt;

	@NotNull
	private Long categoryId;

	@NotNull
	private Long sellerId;

	@Builder
	public ProductCreateRequestDTO(String title, String content, String mainImg, String detailImg, Integer stock,
		Integer totalPrice, Integer deliveryFee, LocalDateTime startAt, LocalDateTime endAt, Long categoryId,
		Long sellerId) {
		this.title = title;
		this.content = content;
		this.mainImg = mainImg;
		this.detailImg = detailImg;
		this.stock = stock;
		this.totalPrice = totalPrice;
		this.deliveryFee = deliveryFee;
		this.startAt = startAt;
		this.endAt = endAt;
		this.categoryId = categoryId;
		this.sellerId = sellerId;
	}
}
