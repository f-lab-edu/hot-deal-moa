package com.example.hotdealmoa.product.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.hotdealmoa.product.domain.Product;
import com.example.hotdealmoa.product.dto.ProductCreateRequestDTO;

@Mapper(componentModel = "spring")
public interface ProductCreateRequestMapper {
	ProductCreateRequestMapper INSTANCE = Mappers.getMapper(ProductCreateRequestMapper.class);

	@Mapping(target = "id", ignore = true)
	Product toEntity(ProductCreateRequestDTO productCreateRequestDTO);
}
