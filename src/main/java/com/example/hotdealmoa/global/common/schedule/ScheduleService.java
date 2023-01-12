package com.example.hotdealmoa.global.common.schedule;

import java.util.List;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hotdealmoa.coupon.domain.Coupon;
import com.example.hotdealmoa.coupon.repository.CouponRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@EnableScheduling
@RequiredArgsConstructor
@Slf4j
@Component
@Service
public class ScheduleService {

	private final CouponRepository couponRepository;

	@Transactional
	@Scheduled(cron = "0 0 0 * * *")
	public void updateExpiredCoupon() {
		List<Coupon> couponList = couponRepository.getUsedOrExpiredCouponList();

		if (!couponList.isEmpty()) {
			long coupons = couponRepository.updateExpiredCoupon(couponList);
			log.info("expired coupon count: {}", coupons);
		}
	}

}
