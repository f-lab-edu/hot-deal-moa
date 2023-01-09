package com.example.hotdealmoa.order.controller;

import static com.example.hotdealmoa.global.common.response.ResponseHandler.*;

import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotdealmoa.global.common.annotation.CurrentUser;
import com.example.hotdealmoa.global.common.annotation.LoginCheck;
import com.example.hotdealmoa.global.common.response.PageResponse;
import com.example.hotdealmoa.global.common.response.ResponseEnum;
import com.example.hotdealmoa.global.common.response.SuccessResponse;
import com.example.hotdealmoa.global.exception.CustomException;
import com.example.hotdealmoa.global.exception.ErrorCode;
import com.example.hotdealmoa.order.dto.OrderCreateRequestDTO;
import com.example.hotdealmoa.order.dto.OrderDetailDTO;
import com.example.hotdealmoa.order.dto.OrderInfoRequestDTO;
import com.example.hotdealmoa.order.dto.OrderInfoResponseDTO;
import com.example.hotdealmoa.order.dto.OrderListDTO;
import com.example.hotdealmoa.order.dto.OrderStatusDTO;
import com.example.hotdealmoa.order.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/order")
public class OrderController {

	private final OrderService orderService;

	@LoginCheck
	@GetMapping("/info")
	public SuccessResponse<OrderInfoResponseDTO> getOrderInfo(@CurrentUser String email,
		@Valid OrderInfoRequestDTO orderInfoRequestDTO) {
		OrderInfoResponseDTO orderInfo = orderService.getOrderInfo(email, orderInfoRequestDTO);
		return success(orderInfo);
	}

	@LoginCheck
	@GetMapping("/{id}")
	public SuccessResponse<OrderDetailDTO> getOrderDetailInfo(@PathVariable Long id) {
		OrderDetailDTO orderDetailInfo = orderService.getOrderDetailInfo(id);
		return success(orderDetailInfo);
	}

	@LoginCheck
	@GetMapping
	public SuccessResponse<PageResponse<OrderListDTO>> getOrderList(@CurrentUser String email, Pageable pageable) {
		PageResponse<OrderListDTO> list = orderService.getOrderList(email, pageable);
		return success(list);
	}

	@LoginCheck
	@PostMapping
	public SuccessResponse<Void> createOrder(@Valid @RequestBody OrderCreateRequestDTO orderCreateRequestDTO) {
		if (!orderService.createOrder(orderCreateRequestDTO)) {
			throw new CustomException(ErrorCode.FAIL);
		}
		return success(ResponseEnum.SUCCESS);
	}

	@LoginCheck
	@PutMapping("/{id}")
	public SuccessResponse<OrderStatusDTO> updateOrderStatus(@PathVariable Long id,
		@Valid @RequestBody OrderStatusDTO orderStatusDTO) {
		return success(orderService.updateOrderStatus(id, orderStatusDTO));
	}
}
