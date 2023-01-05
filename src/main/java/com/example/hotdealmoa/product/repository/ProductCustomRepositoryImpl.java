package com.example.hotdealmoa.product.repository;

import static com.example.hotdealmoa.category.domain.QCategory.*;
import static com.example.hotdealmoa.product.domain.QProduct.*;
import static com.example.hotdealmoa.review.domain.QReview.*;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.example.hotdealmoa.global.common.response.PageResponse;
import com.example.hotdealmoa.product.dto.ProductDTO;
import com.example.hotdealmoa.product.dto.ProductDetailDTO;
import com.example.hotdealmoa.product.dto.ProductSearchCondition;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.MathExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ProductCustomRepositoryImpl implements ProductCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public PageResponse<ProductDTO> getProductList(final ProductSearchCondition productSearchCondition,
		final Pageable pageable) {
		List<ProductDTO> list = queryFactory.select(Projections.constructor(ProductDTO.class,
				product.id,
				product.title,
				product.mainImg,
				product.stock,
				MathExpressions.round(review.star.coalesce(0).avg(), 1),
				product.id.count(),
				product.totalPrice,
				product.startAt,
				product.endAt,
				category.name))
			.from(product)
			.leftJoin(category).on(category.id.eq(product.categoryId))
			.leftJoin(review).on(review.productId.eq(product.id))
			.groupBy(product.id)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.where(
				containsCategory(productSearchCondition.getCategory()),
				containsProductName(productSearchCondition.getProductName()))
			.fetch();

		JPAQuery<Long> count = queryFactory
			.select(product.count())
			.from(product)
			.leftJoin(category).on(category.id.eq(product.categoryId))
			.leftJoin(review).on(review.productId.eq(product.id))
			.where(
				containsCategory(productSearchCondition.getCategory()),
				containsProductName(productSearchCondition.getProductName())
			);

		return PageResponse.of(PageableExecutionUtils.getPage(list, pageable, count::fetchFirst));
	}

	@Override
	public Optional<ProductDetailDTO> getProductDetail(final Long id) {
		ProductDetailDTO productDetailDTO = queryFactory.select(Projections.constructor(ProductDetailDTO.class,
				product.id,
				product.title,
				product.content,
				product.mainImg,
				product.detailImg,
				MathExpressions.round(review.star.coalesce(0).avg(), 1),
				product.id.count(),
				product.stock,
				product.totalPrice,
				product.deliveryFee,
				product.startAt,
				product.endAt,
				category.name))
			.from(product)
			.leftJoin(category).on(category.id.eq(product.categoryId))
			.leftJoin(review).on(review.productId.eq(product.id))
			.groupBy(product.id)
			.where(product.id.eq(id))
			.fetchFirst();

		return Optional.ofNullable(productDetailDTO);
	}

	private BooleanExpression containsCategory(final String keyword) {
		return StringUtils.isEmpty(keyword) ? null : category.name.containsIgnoreCase(keyword);
	}

	private BooleanExpression containsProductName(final String keyword) {
		return StringUtils.isEmpty(keyword) ? null : product.title.containsIgnoreCase(keyword);
	}
}
