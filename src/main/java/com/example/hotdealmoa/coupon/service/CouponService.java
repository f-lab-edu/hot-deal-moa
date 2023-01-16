package com.example.hotdealmoa.coupon.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hotdealmoa.coupon.dto.CouponDTO;
import com.example.hotdealmoa.coupon.dto.CouponSearchCondition;
import com.example.hotdealmoa.coupon.repository.CouponRepository;
import com.example.hotdealmoa.global.common.response.SliceResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponService {
	private final CouponRepository couponRepository;

	@Transactional(readOnly = true)
	public SliceResponse<CouponDTO> getCouponList(final CouponSearchCondition couponSearchCondition,
		final Pageable pageable) {
		return couponRepository.getCouponList(couponSearchCondition, pageable);
	}
}
