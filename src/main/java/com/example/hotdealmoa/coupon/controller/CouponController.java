package com.example.hotdealmoa.coupon.controller;

import static com.example.hotdealmoa.global.common.response.ResponseHandler.*;

import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotdealmoa.coupon.dto.CouponDTO;
import com.example.hotdealmoa.coupon.dto.CouponSearchCondition;
import com.example.hotdealmoa.coupon.service.CouponService;
import com.example.hotdealmoa.global.common.response.SliceResponse;
import com.example.hotdealmoa.global.common.response.SuccessResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/coupon")
public class CouponController {

	private final CouponService couponService;

	@GetMapping
	public SuccessResponse<SliceResponse<CouponDTO>> getCouponList(CouponSearchCondition couponSearchCondition,
		Pageable pageable) {
		SliceResponse<CouponDTO> couponList = couponService.getCouponList(couponSearchCondition, pageable);
		return success(couponList);
	}
}
