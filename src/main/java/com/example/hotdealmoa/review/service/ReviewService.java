package com.example.hotdealmoa.review.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hotdealmoa.global.common.response.SliceResponse;
import com.example.hotdealmoa.global.exception.CustomException;
import com.example.hotdealmoa.global.exception.ErrorCode;
import com.example.hotdealmoa.member.domain.Member;
import com.example.hotdealmoa.member.repository.MemberRepository;
import com.example.hotdealmoa.order.domain.Order;
import com.example.hotdealmoa.order.repository.OrderRepository;
import com.example.hotdealmoa.product.domain.Product;
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
	public SliceResponse<ReviewDTO> getReviewList(ReviewSearchCondition reviewSearchCondition, Pageable pageable) {
		return reviewRepository.getReviewListById(reviewSearchCondition, pageable);
	}

	@Transactional
	public boolean createReview(String email, ReviewCreateRequestDTO reviewCreateRequestDTO) {

		reviewRepository.findByOrderId(reviewCreateRequestDTO.getOrderId()).ifPresent(review -> {
			throw new CustomException(ErrorCode.DUPLICATION);
		});

		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

		Order order = orderRepository.findById(reviewCreateRequestDTO.getOrderId())
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

		Product product = getProductById(order.getProductId());

		Review review = reviewRepository
			.save(reviewCreateMapper.toEntity(member.getId(), product.getId(), reviewCreateRequestDTO));

		updateProductReviewAverage(product);

		return review.getId() > 0;
	}

	@Transactional
	public ReviewUpdateDTO updateReview(Long id, ReviewUpdateDTO reviewUpdateDTO) {
		Review review = getReviewById(id);
		review.updateReview(reviewUpdateDTO);

		Product product = getProductById(review.getProductId());
		updateProductReviewAverage(product);

		return reviewUpdateMapper.toDto(review);
	}

	@Transactional
	public void deleteReview(Long id) {
		Review review = getReviewById(id);
		reviewRepository.delete(review);

		Product product = getProductById(review.getProductId());
		updateProductReviewAverage(product);
	}

	private Review getReviewById(Long id) {
		return reviewRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
	}

	private Product getProductById(Long id) {
		return productRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
	}

	/**
	 * 상품 리뷰 평점 컬럼 업데이트 처리
	 */
	private void updateProductReviewAverage(Product product) {
		List<Review> allByProductId = reviewRepository.findAllByProductId(product.getId());

		double starAverage = allByProductId.stream().map(Review::getStar)
			.mapToInt(i -> i).average().orElse(0);

		product.updateReviewAverage(starAverage);
	}
}
