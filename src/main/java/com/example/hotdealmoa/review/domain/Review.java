package com.example.hotdealmoa.review.domain;

import org.hibernate.annotations.ColumnDefault;

import com.example.hotdealmoa.global.common.BaseTimeEntity;
import com.example.hotdealmoa.review.DTO.ReviewUpdateDTO;

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
@Table(name = "review")
public class Review extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "review_img")
	private String reviewImg;

	@ColumnDefault("0")
	@Column(nullable = false)
	private Integer star;

	@Column(nullable = false)
	private String content;

	@Column(name = "member_id")
	private Long memberId;

	@Column(name = "product_id")
	private Long productId;

	@Column(name = "order_id")
	private Long orderId;

	@Builder
	public Review(Long id, String reviewImg, Integer star, String content, Long memberId, Long productId,
		Long orderId) {
		this.id = id;
		this.reviewImg = reviewImg;
		this.star = star;
		this.content = content;
		this.memberId = memberId;
		this.productId = productId;
		this.orderId = orderId;
	}

	public Review updateReview(ReviewUpdateDTO reviewUpdateDTO) {
		this.reviewImg = reviewUpdateDTO.getReviewImg();
		this.star = reviewUpdateDTO.getStar();
		this.content = reviewUpdateDTO.getContent();
		return this;
	}

}
