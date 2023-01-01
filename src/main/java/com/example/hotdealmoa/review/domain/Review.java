package com.example.hotdealmoa.review.domain;

import static jakarta.persistence.FetchType.*;

import org.hibernate.annotations.ColumnDefault;

import com.example.hotdealmoa.global.common.BaseTimeEntity;
import com.example.hotdealmoa.member.domain.Member;
import com.example.hotdealmoa.product.domain.Product;

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

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@Builder
	public Review(Long id, String reviewImg, Integer star, String content, Member member, Product product) {
		this.id = id;
		this.reviewImg = reviewImg;
		this.star = star;
		this.content = content;
		this.member = member;
		this.product = product;
	}
}
