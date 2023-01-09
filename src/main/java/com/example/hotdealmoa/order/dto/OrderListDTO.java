package com.example.hotdealmoa.order.dto;

import java.time.LocalDateTime;

import com.example.hotdealmoa.order.domain.OrderStatus;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderListDTO {

	private Long productId;
	private String productName;

	private LocalDateTime orderDate;

	private Integer productCount;

	private OrderStatus orderStatus;

	private Long orderId;

	@Builder
	public OrderListDTO(Long productId, String productName, LocalDateTime orderDate, Integer productCount,
		OrderStatus orderStatus, Long orderId) {
		this.productId = productId;
		this.productName = productName;
		this.orderDate = orderDate;
		this.productCount = productCount;
		this.orderStatus = orderStatus;
		this.orderId = orderId;
	}
}
