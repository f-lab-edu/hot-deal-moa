package com.example.hotdealmoa.review.repository;

import org.springframework.data.domain.Pageable;

import com.example.hotdealmoa.global.common.response.PageResponse;
import com.example.hotdealmoa.review.DTO.ReviewDTO;

public interface ReviewCustomRepository {
	PageResponse<ReviewDTO> getReviewListById(Long productId, Pageable pageable);
}
