package com.example.hotdealmoa.review.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.hotdealmoa.review.DTO.ReviewDTO;
import com.example.hotdealmoa.review.domain.Review;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
	ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

	ReviewDTO toDto(Review review);
}
