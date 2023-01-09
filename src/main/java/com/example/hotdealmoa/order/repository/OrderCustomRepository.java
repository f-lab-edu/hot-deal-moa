package com.example.hotdealmoa.order.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.example.hotdealmoa.global.common.response.PageResponse;
import com.example.hotdealmoa.order.dto.OrderDetailDTO;
import com.example.hotdealmoa.order.dto.OrderListDTO;
import com.example.hotdealmoa.order.dto.OrderSearchCondition;

public interface OrderCustomRepository {

	Optional<OrderDetailDTO> getOrderDetailInfo(final Long id);

	PageResponse<OrderListDTO> getOrderList(final String email, final Pageable pageable);
}
