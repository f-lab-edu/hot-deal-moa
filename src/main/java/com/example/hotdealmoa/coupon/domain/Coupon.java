package com.example.hotdealmoa.coupon.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;

import com.example.hotdealmoa.global.common.BaseTimeEntity;

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
@Table(name = "coupon")
public class Coupon extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@ColumnDefault("0")
	@Column(name = "discount_price", nullable = false)
	private Integer discountPrice;

	@ColumnDefault("false")
	@Column(name = "is_used", nullable = false)
	private Boolean isUsed;

	@ColumnDefault("false")
	@Column(name = "is_expired", nullable = false)
	private Boolean isExpired;

	@Column(name = "expired_at", nullable = false)
	private LocalDateTime expiredAt;

	@Column(name = "member_id")
	private Long memberId;

	@Builder
	public Coupon(Long id, String title, Integer discountPrice, Boolean isUsed, Boolean isExpired,
		LocalDateTime expiredAt, Long memberId) {
		this.id = id;
		this.title = title;
		this.discountPrice = discountPrice;
		this.isUsed = isUsed;
		this.isExpired = isExpired;
		this.expiredAt = expiredAt;
		this.memberId = memberId;
	}
}
