package com.example.hotdealmoa.review.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hotdealmoa.global.common.response.PageResponse;
import com.example.hotdealmoa.global.exception.CustomException;
import com.example.hotdealmoa.global.exception.ErrorCode;
import com.example.hotdealmoa.member.domain.Member;
import com.example.hotdealmoa.member.repository.MemberRepository;
import com.example.hotdealmoa.order.repository.OrderRepository;
import com.example.hotdealmoa.product.repository.ProductRepository;
import com.example.hotdealmoa.review.DTO.ReviewCreateRequestDTO;
import com.example.hotdealmoa.review.DTO.ReviewDTO;
import com.example.hotdealmoa.review.DTO.ReviewSearchCondition;
import com.example.hotdealmoa.review.DTO.ReviewUpdateDTO;
import com.example.hotdealmoa.review.domain.Review;
import com.example.hotdealmoa.review.mapper.ReviewCreateMapper;
import com.example.hotdealmoa.review.mapper.ReviewUpdateMapper;
import com.example.hotdealmoa.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final MemberRepository memberRepository;
	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;
	private final ReviewUpdateMapper reviewUpdateMapper;
	private final ReviewCreateMapper reviewCreateMapper;

	@Transactional(readOnly = true)
	public PageResponse<ReviewDTO> getReviewList(ReviewSearchCondition reviewSearchCondition, Pageable pageable) {
		return reviewRepository.getReviewListById(reviewSearchCondition, pageable);
	}

	@Transactional
	public boolean createReview(String email, ReviewCreateRequestDTO reviewCreateRequestDTO) {
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

		productRepository.findById(reviewCreateRequestDTO.getProductId())
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

		orderRepository.findById(reviewCreateRequestDTO.getOrderId())
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

		Review review = reviewCreateMapper.toEntity(member.getId(), reviewCreateRequestDTO);
		return reviewRepository.save(review).getId() > 0;
	}

	@Transactional
	public ReviewUpdateDTO updateReview(Long id, ReviewUpdateDTO reviewUpdateDTO) {
		Review review = reviewRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

		review.updateReview(reviewUpdateDTO);
		return reviewUpdateMapper.toDto(review);
	}

	@Transactional
	public void deleteReview(Long id) {
		Review review = reviewRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

		reviewRepository.delete(review);
	}
}
