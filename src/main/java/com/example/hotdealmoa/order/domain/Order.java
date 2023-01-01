package com.example.hotdealmoa.order.domain;

import static jakarta.persistence.FetchType.*;

import org.hibernate.annotations.ColumnDefault;

import com.example.hotdealmoa.coupon.domain.Coupon;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ColumnDefault("0")
	@Column(name = "product_count", nullable = false)
	private Integer productCount;

	@Column(name = "request_message")
	private String requestMessage;

	@Column(name = "request_address", nullable = false)
	private String requestAddress;

	@ColumnDefault("0")
	@Column(name = "payment_price", nullable = false)
	private Integer paymentPrice;

	@Column(name = "order_status", nullable = false)
	private String orderStatus;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "coupon_id")
	private Coupon coupon;

	@Builder
	public Order(Long id, Integer productCount, String requestMessage, String requestAddress, Integer paymentPrice,
		String orderStatus, Product product, Member member, Coupon coupon) {
		this.id = id;
		this.productCount = productCount;
		this.requestMessage = requestMessage;
		this.requestAddress = requestAddress;
		this.paymentPrice = paymentPrice;
		this.orderStatus = orderStatus;
		this.product = product;
		this.member = member;
		this.coupon = coupon;
	}
}
