package com.example.hotdealmoa.coupon.repository;

import org.springframework.data.domain.Pageable;

import com.example.hotdealmoa.coupon.dto.CouponDTO;
import com.example.hotdealmoa.coupon.dto.CouponSearchCondition;
import com.example.hotdealmoa.global.common.response.PageResponse;

public interface CouponCustomRepository {

	PageResponse<CouponDTO> getCouponList(final CouponSearchCondition couponSearchCondition,
		final Pageable pageable);
}
