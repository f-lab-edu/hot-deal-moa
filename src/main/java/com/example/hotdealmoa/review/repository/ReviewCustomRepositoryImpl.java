package com.example.hotdealmoa.review.repository;

import static com.example.hotdealmoa.member.domain.QMember.*;
import static com.example.hotdealmoa.order.domain.QOrder.*;
import static com.example.hotdealmoa.product.domain.QProduct.*;
import static com.example.hotdealmoa.review.domain.QReview.*;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.hotdealmoa.global.common.response.SliceResponse;
import com.example.hotdealmoa.review.DTO.ReviewDTO;
import com.example.hotdealmoa.review.DTO.ReviewSearchCondition;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ReviewCustomRepositoryImpl implements ReviewCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public SliceResponse<ReviewDTO> getReviewListById(final ReviewSearchCondition searchCondition,
		final Pageable pageable) {
		List<ReviewDTO> list = queryFactory.select(Projections.constructor(ReviewDTO.class,
				review.id,
				review.reviewImg,
				review.star,
				review.content,
				member.name,
				product.title,
				order.productCount))
			.from(review)
			.innerJoin(member).on(member.id.eq(review.memberId))
			.innerJoin(product).on(product.id.eq(review.productId))
			.innerJoin(order).on(order.id.eq(review.orderId))
			.limit(pageable.getPageSize() + 1)
			.where(
				eqProductId(searchCondition.getProductId()),
				eqBuyerName(searchCondition.getBuyerName()),
				ltReviewId(searchCondition.getLastReviewId()))
			.orderBy(review.id.desc())
			.fetch();

		return SliceResponse.of(list, pageable);
	}

	private BooleanExpression eqProductId(Long productId) {
		return productId != null ? product.id.eq(productId) : null;
	}

	private BooleanExpression eqBuyerName(String buyerName) {
		return StringUtils.isEmpty(buyerName) ? null : member.name.eq(buyerName);
	}

	private BooleanExpression ltReviewId(Long id) {
		return id == null ? null : review.id.lt(id);
	}

}
