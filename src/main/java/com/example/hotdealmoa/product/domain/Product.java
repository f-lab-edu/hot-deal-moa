package com.example.hotdealmoa.product.domain;

import static jakarta.persistence.FetchType.*;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;

import com.example.hotdealmoa.category.domain.Category;
import com.example.hotdealmoa.global.common.BaseTimeEntity;
import com.example.hotdealmoa.member.domain.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "seller_id")
	private Member seller;

	@Builder
	public Product(Long id, String title, String content, String mainImg, String detailImg, Integer stock,
		Integer totalPrice, Integer deliveryFee, LocalDateTime startAt, LocalDateTime endAt, Category category,
		Member seller) {
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
		this.category = category;
		this.seller = seller;
	}
}
