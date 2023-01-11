package com.example.hotdealmoa.review.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.hotdealmoa.review.DTO.ReviewCreateRequestDTO;
import com.example.hotdealmoa.review.domain.Review;

@Mapper(componentModel = "spring")
public interface ReviewCreateMapper {
	ReviewCreateMapper INSTANCE = Mappers.getMapper(ReviewCreateMapper.class);

	@Mapping(target = "id", ignore = true)
	Review toEntity(Long memberId, Long productId, ReviewCreateRequestDTO reviewCreateRequestDTO);
}
