package com.example.hotdealmoa.order.repository;

import static com.example.hotdealmoa.coupon.domain.QCoupon.*;
import static com.example.hotdealmoa.member.domain.QMember.*;
import static com.example.hotdealmoa.order.domain.QOrder.*;
import static com.example.hotdealmoa.product.domain.QProduct.*;
import static com.querydsl.core.types.dsl.Expressions.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.example.hotdealmoa.global.common.response.PageResponse;
import com.example.hotdealmoa.order.dto.OrderDetailDTO;
import com.example.hotdealmoa.order.dto.OrderListDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderCustomRepositoryImpl implements OrderCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<OrderDetailDTO> getOrderDetailInfo(Long id) {

		OrderDetailDTO orderDetailDTO = queryFactory.select(Projections.constructor(OrderDetailDTO.class,
				constantAs(id, order.id),
				product.title,
				order.productCount,
				order.requestMessage,
				order.orderStatus,
				member.name,
				member.email,
				member.phoneNumber,
				order.requestAddress,
				product.totalPrice,
				product.deliveryFee,
				coupon.discountPrice,
				order.paymentPrice
			))
			.from(order)
			.leftJoin(member).on(order.memberId.eq(member.id))
			.leftJoin(coupon).on(order.couponId.eq(coupon.id))
			.leftJoin(product).on(order.productId.eq(product.id))
			.where(order.id.eq(id))
			.fetchFirst();

		return Optional.ofNullable(orderDetailDTO);
	}

	@Override
	public PageResponse<OrderListDTO> getOrderList(String email, Pageable pageable) {
		List<OrderListDTO> list = queryFactory.select(Projections.constructor(OrderListDTO.class,
				product.id,
				product.title,
				order.createdAt,
				order.productCount,
				order.orderStatus,
				order.id
			))
			.from(order)
			.leftJoin(member).on(order.memberId.eq(member.id))
			.leftJoin(product).on(order.productId.eq(product.id))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.where(member.email.eq(email))
			.orderBy(order.createdAt.desc())
			.fetch();

		JPAQuery<Long> count = queryFactory
			.select(order.count())
			.from(order)
			.leftJoin(member).on(order.memberId.eq(member.id))
			.leftJoin(product).on(order.productId.eq(product.id))
			.where();

		return PageResponse.of(PageableExecutionUtils.getPage(list, pageable, count::fetchFirst));
	}

}
