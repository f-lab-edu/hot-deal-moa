package com.example.hotdealmoa.order.domain;

import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OrderStatus {

	/**
	 * 주문 완료
	 */
	ORDERED("ORDERED"),

	/**
	 * 출고 진행 중
	 */
	PROCESSING("PROCESSING"),

	/**
	 * 배송 진행 중
	 */
	IN_DELIVERY("IN_DELIVERY"),

	/**
	 * 배송 완료
	 */
	DELIVERED("DELIVERED");

	private final String value;

	@JsonCreator(mode = JsonCreator.Mode.DELEGATING)
	public static OrderStatus fromOrderStatus(String value) {
		return Stream.of(OrderStatus.values())
			.filter(orderStatus -> StringUtils.equals(orderStatus.getValue(), value))
			.findFirst()
			.orElse(null);
	}

}
