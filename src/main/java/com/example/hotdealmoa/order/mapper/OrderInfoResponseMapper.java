package com.example.hotdealmoa.order.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.hotdealmoa.member.domain.Member;
import com.example.hotdealmoa.order.dto.OrderInfoResponseDTO;
import com.example.hotdealmoa.product.domain.Product;

@Mapper(componentModel = "spring")
public interface OrderInfoResponseMapper {

	OrderInfoResponseMapper INSTANCE = Mappers.getMapper(OrderInfoResponseMapper.class);

	@Mapping(source = "member.id", target = "memberId")
	@Mapping(source = "product.id", target = "productId")
	@Mapping(source = "product.title", target = "productTitle")
	@Mapping(target = "couponId", ignore = true)
	@Mapping(target = "discountPrice", ignore = true)
	OrderInfoResponseDTO toDto(Integer productCount, Member member, Product product);
}
