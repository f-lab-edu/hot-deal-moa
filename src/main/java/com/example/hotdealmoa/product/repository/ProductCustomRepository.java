package com.example.hotdealmoa.product.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.example.hotdealmoa.global.common.response.PageResponse;
import com.example.hotdealmoa.product.dto.ProductDTO;
import com.example.hotdealmoa.product.dto.ProductDetailDTO;
import com.example.hotdealmoa.product.dto.ProductSearchCondition;

public interface ProductCustomRepository {

	PageResponse<ProductDTO> getProductList(final ProductSearchCondition productSearchCondition,
		final Pageable pageable);

	Optional<ProductDetailDTO> getProductDetail(final Long id);
}
