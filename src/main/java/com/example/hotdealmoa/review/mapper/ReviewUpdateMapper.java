package com.example.hotdealmoa.review.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.hotdealmoa.review.DTO.ReviewUpdateResponseDTO;
import com.example.hotdealmoa.review.domain.Review;

@Mapper(componentModel = "spring")
public interface ReviewUpdateMapper {
	ReviewUpdateMapper INSTANCE = Mappers.getMapper(ReviewUpdateMapper.class);

	ReviewUpdateResponseDTO toDto(Review review);
}
