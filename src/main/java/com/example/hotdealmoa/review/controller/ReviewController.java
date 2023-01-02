package com.example.hotdealmoa.review.controller;

import static com.example.hotdealmoa.global.common.response.ResponseHandler.*;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotdealmoa.global.common.annotation.CurrentUser;
import com.example.hotdealmoa.global.common.annotation.LoginCheck;
import com.example.hotdealmoa.global.common.response.PageResponse;
import com.example.hotdealmoa.global.common.response.ResponseEnum;
import com.example.hotdealmoa.global.common.response.SuccessResponse;
import com.example.hotdealmoa.global.exception.CustomException;
import com.example.hotdealmoa.global.exception.ErrorCode;
import com.example.hotdealmoa.review.DTO.ReviewCreateRequestDTO;
import com.example.hotdealmoa.review.DTO.ReviewDTO;
import com.example.hotdealmoa.review.DTO.ReviewUpdateRequestDTO;
import com.example.hotdealmoa.review.DTO.ReviewUpdateResponseDTO;
import com.example.hotdealmoa.review.service.ReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/review")
public class ReviewController {
	private final ReviewService reviewService;

	@GetMapping("/{productId}")
	public SuccessResponse<PageResponse<ReviewDTO>> getReviewList(@PathVariable Long productId,
		@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		PageResponse<ReviewDTO> reviewList = reviewService.getReviewList(productId, pageable);
		return success(reviewList);
	}

	@LoginCheck
	@PostMapping
	public SuccessResponse<Void> createReview(@CurrentUser String email,
		@Valid @RequestBody ReviewCreateRequestDTO reviewCreateRequestDTO) {
		if (!reviewService.createReview(email, reviewCreateRequestDTO)) {
			throw new CustomException(ErrorCode.FAIL);
		}
		return success(ResponseEnum.SUCCESS);
	}

	@LoginCheck
	@PutMapping("/{id}")
	public SuccessResponse<ReviewUpdateResponseDTO> updateReview(@PathVariable Long id,
		@Valid @RequestBody ReviewUpdateRequestDTO reviewUpdateRequestDTO) {
		ReviewUpdateResponseDTO responseDTO = reviewService.updateReview(id, reviewUpdateRequestDTO);
		return success(responseDTO);
	}

	@LoginCheck
	@DeleteMapping("/{id}")
	public SuccessResponse<Void> deleteReview(@PathVariable Long id) {
		reviewService.deleteReview(id);
		return success(ResponseEnum.SUCCESS);
	}
}
