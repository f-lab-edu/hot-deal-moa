package com.example.hotdealmoa.order.service;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hotdealmoa.coupon.domain.Coupon;
import com.example.hotdealmoa.coupon.repository.CouponRepository;
import com.example.hotdealmoa.global.common.response.PageResponse;
import com.example.hotdealmoa.global.exception.CustomException;
import com.example.hotdealmoa.global.exception.ErrorCode;
import com.example.hotdealmoa.member.domain.Member;
import com.example.hotdealmoa.member.repository.MemberRepository;
import com.example.hotdealmoa.order.domain.Order;
import com.example.hotdealmoa.order.dto.OrderCreateRequestDTO;
import com.example.hotdealmoa.order.dto.OrderDetailDTO;
import com.example.hotdealmoa.order.dto.OrderInfoRequestDTO;
import com.example.hotdealmoa.order.dto.OrderInfoResponseDTO;
import com.example.hotdealmoa.order.dto.OrderListDTO;
import com.example.hotdealmoa.order.dto.OrderStatusDTO;
import com.example.hotdealmoa.order.mapper.OrderCreateRequestMapper;
import com.example.hotdealmoa.order.mapper.OrderInfoResponseMapper;
import com.example.hotdealmoa.order.repository.OrderRepository;
import com.example.hotdealmoa.product.domain.Product;
import com.example.hotdealmoa.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final MemberRepository memberRepository;
	private final ProductRepository productRepository;
	private final CouponRepository couponRepository;
	private final OrderInfoResponseMapper orderInfoResponseMapper;
	private final OrderCreateRequestMapper orderCreateRequestMapper;

	@Transactional(readOnly = true)
	public OrderInfoResponseDTO getOrderInfo(final String email, final OrderInfoRequestDTO orderInfoRequestDTO) {

		// 구매자 정보
		Member buyer = memberRepository.findByEmail(email)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

		// 결제 정보
		Product product = productRepository.findById(orderInfoRequestDTO.getProductId())
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

		OrderInfoResponseDTO orderInfoResponseDTO = orderInfoResponseMapper.toDto(orderInfoRequestDTO.getProductCount(),
			buyer, product);

		// 쿠폰 정보
		if (null != orderInfoRequestDTO.getCouponId()) {
			Coupon coupon = couponRepository.findById(orderInfoRequestDTO.getProductId())
				.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
			orderInfoResponseDTO.updateCouponInfo(coupon);
		}

		return orderInfoResponseDTO;
	}

	@Transactional(readOnly = true)
	public OrderDetailDTO getOrderDetailInfo(final Long id) {
		return orderRepository.getOrderDetailInfo(id)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
	}

	@Transactional(readOnly = true)
	public PageResponse<OrderListDTO> getOrderList(final String email, final Pageable pageable) {
		return orderRepository.getOrderList(email, pageable);
	}

	@Transactional
	public boolean createOrder(final OrderCreateRequestDTO orderCreateRequestDTO) {
		Order orderEntity = orderCreateRequestMapper.toEntity(orderCreateRequestDTO);

		Product product = productRepository.findById(orderEntity.getProductId())
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

		// 상품 개수 유효 체크
		if (NumberUtils.compare(product.getStock(), orderEntity.getProductCount()) < 0) {
			throw new CustomException(ErrorCode.ORDER_ERROR);
		}

		// 결제 금액 계산
		int totalPrice = getTotalPrice(product.getTotalPrice(), orderEntity.getProductCount());
		int disCountPrice = getDiscountPrice(orderEntity.getCouponId(), orderEntity.getMemberId());

		int paymentPrice = NumberUtils.compare(totalPrice, disCountPrice) < 0 ? 0 : totalPrice - disCountPrice;

		orderEntity.addPaymentPrice(paymentPrice);

		// 상품 개수 감소 처리
		product.updateStock(product.getStock() - orderEntity.getProductCount());

		// 주문 생성
		Order order = orderRepository.save(orderEntity);

		return order.getId() > 0;
	}

	@Transactional
	public OrderStatusDTO updateOrderStatus(final Long id, final OrderStatusDTO orderStatusDTO) {
		Order order = orderRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

		order.updateStatus(orderStatusDTO.getOrderStatus());

		return OrderStatusDTO.toDTO(order);
	}

	/**
	 * 결제할 금액 계산 함수
	 * @param price 제품 금액
	 * @param count 주문 개수
	 * @return 결제할 금액
	 */
	private int getTotalPrice(int price, int count) {
		return price * count;
	}

	/**
	 * 할인 쿠폰 금액 함수
	 * @param couponId 쿠폰 id
	 * @return 쿠폰 할인 금액
	 */
	private int getDiscountPrice(Long couponId, Long memberId) {
		if (couponId == null) {
			return 0;
		}

		Coupon coupon = couponRepository.findById(couponId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

		// 사용 가능한 쿠폰인지 확인
		if (coupon.getIsUsed() || coupon.getIsExpired()) {
			throw new CustomException(ErrorCode.COUPON_ERROR);
		}

		// 해당 유저의 쿠폰인지 확인
		if (NumberUtils.compare(coupon.getMemberId(), memberId) != 0) {
			throw new CustomException(ErrorCode.COUPON_OWN_MEMBER);
		}

		// 쿠폰 사용 처리
		coupon.updateCouponStatus();

		return coupon.getDiscountPrice();
	}

}
