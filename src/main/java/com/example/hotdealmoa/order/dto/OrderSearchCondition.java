package com.example.hotdealmoa.order.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderSearchCondition {

	private Long MemberId;

	@Builder
	public OrderSearchCondition(Long memberId) {
		MemberId = memberId;
	}
}
