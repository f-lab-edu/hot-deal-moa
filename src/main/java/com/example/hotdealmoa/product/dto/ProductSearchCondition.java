package com.example.hotdealmoa.product.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductSearchCondition {

	private String category;
	private String productName;

	@Builder
	public ProductSearchCondition(String category, String productName) {
		this.category = category;
		this.productName = productName;
	}
}
