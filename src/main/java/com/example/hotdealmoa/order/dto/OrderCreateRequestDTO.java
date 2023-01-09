package com.example.hotdealmoa.order.dto;

import org.hibernate.validator.constraints.Length;

import com.example.hotdealmoa.global.validation.RegexEnum;
import com.example.hotdealmoa.global.validation.annotation.EnumValid;
import com.example.hotdealmoa.global.validation.annotation.Regex;
import com.example.hotdealmoa.order.domain.OrderStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderCreateRequestDTO {

	@NotBlank
	@Regex(RegexEnum.PHONE_NUMBER)
	private String phoneNumber;

	@NotBlank
	@Length(min = 1, max = 30)
	private String requestAddress;

	private String requestMessage;

	@NotNull
	private Integer productCount;

	@EnumValid(message = "VALID_ORDER.STATUS")
	private OrderStatus orderStatus = OrderStatus.ORDERED;

	@NotNull
	private Long productId;

	@NotNull
	private Long memberId;
	private Long couponId;

	@Builder
	public OrderCreateRequestDTO(String phoneNumber, String requestAddress, String requestMessage, Integer productCount,
		OrderStatus orderStatus, Long productId, Long memberId, Long couponId) {
		this.phoneNumber = phoneNumber;
		this.requestAddress = requestAddress;
		this.requestMessage = requestMessage;
		this.productCount = productCount;
		this.orderStatus = orderStatus;
		this.productId = productId;
		this.memberId = memberId;
		this.couponId = couponId;
	}
}
