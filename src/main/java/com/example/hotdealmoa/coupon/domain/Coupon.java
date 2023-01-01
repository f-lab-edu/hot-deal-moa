package com.example.hotdealmoa.coupon.domain;

import static jakarta.persistence.FetchType.*;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;

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

	@Column(name = "expired_at", nullable = false)
	private LocalDateTime expiredAt;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@Builder
	public Coupon(Long id, String title, Integer discountPrice, Boolean isUsed, LocalDateTime expiredAt,
		Member member) {
		this.id = id;
		this.title = title;
		this.discountPrice = discountPrice;
		this.isUsed = isUsed;
		this.expiredAt = expiredAt;
		this.member = member;
	}
}
