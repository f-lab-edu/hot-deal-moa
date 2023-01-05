package com.example.hotdealmoa.product.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;

import com.example.hotdealmoa.global.common.BaseTimeEntity;
import com.example.hotdealmoa.product.dto.ProductUpdateDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String content;

	@Column(name = "main_img")
	private String mainImg;

	@Column(name = "detail_img")
	private String detailImg;

	@ColumnDefault("0")
	@Column(nullable = false)
	private Integer stock;

	@ColumnDefault("0")
	@Column(name = "total_price", nullable = false)
	private Integer totalPrice;

	@ColumnDefault("0")
	@Column(name = "delivery_fee", nullable = false)
	private Integer deliveryFee;

	@Column(name = "start_at")
	private LocalDateTime startAt;

	@Column(name = "end_at")
	private LocalDateTime endAt;

	@Column(name = "category_id")
	private Long categoryId;

	@Column(name = "seller_id")
	private Long sellerId;

	@Builder
	public Product(Long id, String title, String content, String mainImg, String detailImg, Integer stock,
		Integer totalPrice, Integer deliveryFee, LocalDateTime startAt, LocalDateTime endAt, Long categoryId,
		Long sellerId) {
		this.id = id;
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

	public Product updateProduct(ProductUpdateDTO productUpdateRequestDTO) {
		this.title = productUpdateRequestDTO.getTitle();
		this.content = productUpdateRequestDTO.getContent();
		this.mainImg = productUpdateRequestDTO.getMainImg();
		this.detailImg = productUpdateRequestDTO.getDetailImg();
		this.stock = productUpdateRequestDTO.getStock();
		this.totalPrice = productUpdateRequestDTO.getTotalPrice();
		this.deliveryFee = productUpdateRequestDTO.getDeliveryFee();
		this.categoryId = productUpdateRequestDTO.getCategoryId();

		return this;
	}

}
