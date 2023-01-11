package com.example.hotdealmoa.order.dto;

import com.example.hotdealmoa.order.domain.OrderStatus;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDetailDTO {

	private Long id;
	private String productTitle;
	private Integer productCount;
	private String productMainImg;
	private String requestMessage;
	private OrderStatus orderStatus;
	private String name;
	private String email;
	private String phoneNumber;
	private String address;
	private Integer totalPrice;
	private Integer deliveryFee;
	private Integer discountPrice;
	private Integer paymentPrice;

	@Builder
	public OrderDetailDTO(Long id, String productTitle, Integer productCount, String productMainImg,
		String requestMessage, OrderStatus orderStatus, String name, String email, String phoneNumber,
		String address, Integer totalPrice, Integer deliveryFee, Integer discountPrice, Integer paymentPrice) {
		this.id = id;
		this.productTitle = productTitle;
		this.productCount = productCount;
		this.productMainImg = productMainImg;
		this.requestMessage = requestMessage;
		this.orderStatus = orderStatus;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.totalPrice = totalPrice;
		this.deliveryFee = deliveryFee;
		this.discountPrice = discountPrice;
		this.paymentPrice = paymentPrice;
	}
}
