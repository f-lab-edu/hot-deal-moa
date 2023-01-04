package com.example.hotdealmoa.product.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hotdealmoa.global.common.response.PageResponse;
import com.example.hotdealmoa.global.exception.CustomException;
import com.example.hotdealmoa.global.exception.ErrorCode;
import com.example.hotdealmoa.product.domain.Product;
import com.example.hotdealmoa.product.dto.ProductCreateRequestDTO;
import com.example.hotdealmoa.product.dto.ProductDTO;
import com.example.hotdealmoa.product.dto.ProductDetailDTO;
import com.example.hotdealmoa.product.dto.ProductSearchCondition;
import com.example.hotdealmoa.product.dto.ProductUpdateDTO;
import com.example.hotdealmoa.product.mapper.ProductCreateRequestMapper;
import com.example.hotdealmoa.product.mapper.ProductUpdateMapper;
import com.example.hotdealmoa.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final ProductUpdateMapper productUpdateMapper;
	private final ProductCreateRequestMapper productCreateRequestMapper;

	@Transactional(readOnly = true)
	public PageResponse<ProductDTO> getProductList(final ProductSearchCondition productSearchCondition,
		final Pageable pageable) {
		return productRepository.getProductList(productSearchCondition, pageable);
	}

	@Transactional(readOnly = true)
	public ProductDetailDTO getProductDetail(final Long id) {
		return productRepository.getProductDetail(id)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
	}

	@Transactional
	public boolean regiProduct(final ProductCreateRequestDTO productCreateRequestDTO) {
		Product product = productCreateRequestMapper.toEntity(productCreateRequestDTO);
		return productRepository.save(product).getId() > 0;
	}

	@Transactional
	public ProductUpdateDTO updateProduct(final ProductUpdateDTO productUpdateDTO) {
		Product product = productRepository.findById(productUpdateDTO.getId())
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

		product.updateProduct(productUpdateDTO);
		return productUpdateMapper.toDto(product);
	}

	@Transactional
	public void deleteProduct(final Long id) {
		Product product = productRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

		productRepository.delete(product);
	}
}
