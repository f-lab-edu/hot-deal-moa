package com.example.hotdealmoa.order.domain;

import org.hibernate.annotations.ColumnDefault;

import com.example.hotdealmoa.global.common.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

	@Enumerated(EnumType.STRING)
	@Column(name = "order_status")
	private OrderStatus orderStatus;

	@Column(name = "product_id")
	private Long productId;

	@Column(name = "member_id")
	private Long memberId;

	@Column(name = "coupon_id")
	private Long couponId;

	@Builder
	public Order(Long id, Integer productCount, String requestMessage, String requestAddress,
		Integer paymentPrice, OrderStatus orderStatus, Long productId, Long memberId, Long couponId) {
		this.id = id;
		this.productCount = productCount;
		this.requestMessage = requestMessage;
		this.requestAddress = requestAddress;
		this.paymentPrice = paymentPrice;
		this.orderStatus = orderStatus;
		this.productId = productId;
		this.memberId = memberId;
		this.couponId = couponId;
	}

	public void addPaymentPrice(Integer paymentPrice) {
		this.paymentPrice = paymentPrice;
	}

	public void updateStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

}
