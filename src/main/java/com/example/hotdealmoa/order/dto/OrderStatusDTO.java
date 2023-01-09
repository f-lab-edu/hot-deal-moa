package com.example.hotdealmoa.order.dto;

import com.example.hotdealmoa.global.validation.annotation.EnumValid;
import com.example.hotdealmoa.order.domain.Order;
import com.example.hotdealmoa.order.domain.OrderStatus;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderStatusDTO {

	@EnumValid(message = "VALID_ORDER.STATUS")
	OrderStatus orderStatus;

	@Builder
	public OrderStatusDTO(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public static OrderStatusDTO toDTO(Order order) {
		return OrderStatusDTO.builder()
			.orderStatus(order.getOrderStatus())
			.build();
	}
}
