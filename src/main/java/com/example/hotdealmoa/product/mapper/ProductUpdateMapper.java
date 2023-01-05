package com.example.hotdealmoa.product.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.hotdealmoa.product.domain.Product;
import com.example.hotdealmoa.product.dto.ProductUpdateDTO;

@Mapper(componentModel = "spring")
public interface ProductUpdateMapper {

	ProductUpdateMapper INSTANCE = Mappers.getMapper(ProductUpdateMapper.class);

	ProductUpdateDTO toDto(Product product);
}
