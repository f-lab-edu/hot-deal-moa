package com.example.hotdealmoa.product.controller;

import static com.example.hotdealmoa.global.common.response.ResponseHandler.*;

import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotdealmoa.global.common.annotation.LoginCheck;
import com.example.hotdealmoa.global.common.response.PageResponse;
import com.example.hotdealmoa.global.common.response.ResponseEnum;
import com.example.hotdealmoa.global.common.response.SuccessResponse;
import com.example.hotdealmoa.global.exception.CustomException;
import com.example.hotdealmoa.global.exception.ErrorCode;
import com.example.hotdealmoa.member.domain.UserRole;
import com.example.hotdealmoa.product.dto.ProductCreateRequestDTO;
import com.example.hotdealmoa.product.dto.ProductDTO;
import com.example.hotdealmoa.product.dto.ProductDetailDTO;
import com.example.hotdealmoa.product.dto.ProductSearchCondition;
import com.example.hotdealmoa.product.dto.ProductUpdateDTO;
import com.example.hotdealmoa.product.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/product")
public class ProductController {
	private final ProductService productService;

	@GetMapping
	public SuccessResponse<PageResponse<ProductDTO>> getProductList(
		ProductSearchCondition productSearchCondition, Pageable pageable) {
		PageResponse<ProductDTO> productList = productService.getProductList(productSearchCondition, pageable);
		return success(productList);
	}

	@GetMapping("/{id}")
	public SuccessResponse<ProductDetailDTO> getProductDetail(@PathVariable Long id) {
		ProductDetailDTO productDetail = productService.getProductDetail(id);
		return success(productDetail);
	}

	@LoginCheck(role = UserRole.ROLE_SELLER)
	@PostMapping
	public SuccessResponse<Void> regiProduct(@Valid @RequestBody ProductCreateRequestDTO productCreateRequestDTO) {
		if (!productService.regiProduct(productCreateRequestDTO)) {
			throw new CustomException(ErrorCode.FAIL);
		}
		return success(ResponseEnum.SUCCESS);
	}

	@LoginCheck(role = UserRole.ROLE_SELLER)
	@PutMapping("/{id}")
	public SuccessResponse<ProductUpdateDTO> updateProduct(@PathVariable Long id,
		@Valid @RequestBody ProductUpdateDTO productUpdateDTO) {
		return success(productService.updateProduct(id, productUpdateDTO));
	}

	@LoginCheck(role = UserRole.ROLE_SELLER)
	@DeleteMapping("/{id}")
	public SuccessResponse<Void> deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return success(ResponseEnum.SUCCESS);
	}
}
