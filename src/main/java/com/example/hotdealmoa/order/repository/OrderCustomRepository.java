package com.example.hotdealmoa.order.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.example.hotdealmoa.global.common.response.SliceResponse;
import com.example.hotdealmoa.order.dto.OrderDetailDTO;
import com.example.hotdealmoa.order.dto.OrderListDTO;

public interface OrderCustomRepository {

	Optional<OrderDetailDTO> getOrderDetailInfo(final Long id);

	SliceResponse<OrderListDTO> getOrderList(final String email, final Long lastOrderId, final Pageable pageable);
}
