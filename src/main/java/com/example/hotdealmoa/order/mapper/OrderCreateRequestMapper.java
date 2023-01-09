package com.example.hotdealmoa.order.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.hotdealmoa.order.domain.Order;
import com.example.hotdealmoa.order.dto.OrderCreateRequestDTO;

@Mapper(componentModel = "spring")
public interface OrderCreateRequestMapper {

	OrderCreateRequestMapper INSTANCE = Mappers.getMapper(OrderCreateRequestMapper.class);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "paymentPrice", ignore = true)
	Order toEntity(OrderCreateRequestDTO orderCreateRequestDTO);
}
