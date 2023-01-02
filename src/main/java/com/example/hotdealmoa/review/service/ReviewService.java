package com.example.hotdealmoa.review.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hotdealmoa.global.common.response.PageResponse;
import com.example.hotdealmoa.global.exception.CustomException;
import com.example.hotdealmoa.global.exception.ErrorCode;
import com.example.hotdealmoa.member.domain.Member;
import com.example.hotdealmoa.member.repository.MemberRepository;
import com.example.hotdealmoa.review.DTO.ReviewCreateRequestDTO;
import com.example.hotdealmoa.review.DTO.ReviewDTO;
import com.example.hotdealmoa.review.DTO.ReviewUpdateRequestDTO;
import com.example.hotdealmoa.review.domain.Review;
import com.example.hotdealmoa.review.mapper.ReviewCreateMapper;
import com.example.hotdealmoa.review.mapper.ReviewMapper;
import com.example.hotdealmoa.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final MemberRepository memberRepository;
	private final ReviewMapper reviewMapper;
	private final ReviewCreateMapper reviewCreateMapper;

	@Transactional(readOnly = true)
	public PageResponse<ReviewDTO> getReviewList(Long productId, Pageable pageable) {
		return reviewRepository.getReviewListById(productId, pageable);
	}

	@Transactional
	public boolean createReview(String email, ReviewCreateRequestDTO reviewCreateRequestDTO) {
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

		Review review = reviewCreateMapper.toEntity(member.getId(), reviewCreateRequestDTO);
		return reviewRepository.save(review).getId() > 0;
	}

	@Transactional
	public ReviewDTO updateReview(Long id, ReviewUpdateRequestDTO reviewUpdateRequestDTO) {
		Review review = reviewRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

		review.updateReview(reviewUpdateRequestDTO);
		return reviewMapper.toDto(review);
	}

	@Transactional
	public void deleteReview(Long id) {
		Review review = reviewRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

		reviewRepository.delete(review);
	}
}
