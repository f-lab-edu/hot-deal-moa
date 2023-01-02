package com.example.hotdealmoa.review.repository;

import static com.example.hotdealmoa.member.domain.QMember.*;
import static com.example.hotdealmoa.product.domain.QProduct.*;
import static com.example.hotdealmoa.review.domain.QReview.*;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.example.hotdealmoa.global.common.response.PageResponse;
import com.example.hotdealmoa.review.DTO.ReviewDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ReviewCustomRepositoryImpl implements ReviewCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public PageResponse<ReviewDTO> getReviewListById(final Long productId, final Pageable pageable) {

		List<ReviewDTO> list = queryFactory.select(Projections.constructor(ReviewDTO.class,
				review.id,
				review.reviewImg,
				review.star,
				review.content,
				member.name,
				product.title))
			.from(review)
			.innerJoin(member).on(member.id.eq(review.memberId))
			.innerJoin(product).on(product.id.eq(productId))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.where(review.productId.eq(productId))
			.fetch();

		JPAQuery<Long> count = queryFactory
			.select(review.count())
			.from(review);

		return PageResponse.of(PageableExecutionUtils.getPage(list, pageable, count::fetchFirst));
	}
}
