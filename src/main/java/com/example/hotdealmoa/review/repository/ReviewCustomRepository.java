package com.example.hotdealmoa.review.repository;

import org.springframework.data.domain.Pageable;

import com.example.hotdealmoa.global.common.response.SliceResponse;
import com.example.hotdealmoa.review.DTO.ReviewDTO;
import com.example.hotdealmoa.review.DTO.ReviewSearchCondition;

public interface ReviewCustomRepository {
	SliceResponse<ReviewDTO> getReviewListById(ReviewSearchCondition reviewSearchCondition, Pageable pageable);
}
