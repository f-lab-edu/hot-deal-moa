package com.example.hotdealmoa.product;

import static com.example.hotdealmoa.global.config.RestDocsConfig.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import com.example.hotdealmoa.global.common.response.PageResponse;
import com.example.hotdealmoa.global.config.AbstractControllerTest;
import com.example.hotdealmoa.global.util.MessageUtils;
import com.example.hotdealmoa.member.service.LoginService;
import com.example.hotdealmoa.product.controller.ProductController;
import com.example.hotdealmoa.product.dto.ProductCreateRequestDTO;
import com.example.hotdealmoa.product.dto.ProductDTO;
import com.example.hotdealmoa.product.dto.ProductDetailDTO;
import com.example.hotdealmoa.product.dto.ProductUpdateDTO;
import com.example.hotdealmoa.product.service.ProductService;

@DisplayName("상품 테스트")
@WebMvcTest(value = ProductController.class, includeFilters = {
	@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = MessageUtils.class)})
public class ProductControllerTest extends AbstractControllerTest {

	private final String BASIC_URL = "/api/product";
	private final Long ID = 1L;

	@MockBean
	private LoginService loginService;
	@MockBean
	private ProductService productService;

	private PageResponse<ProductDTO> createProductDTOPage() {
		List<ProductDTO> productList = new ArrayList<>();

		ProductDTO productDTO = ProductDTO.builder()
			.id(ID)
			.title("bbb")
			.mainImg("/image/aaa.jpg")
			.stock(21)
			.starAvg(2.5)
			.reviewCount(2L)
			.totalPrice(222)
			.startAt(LocalDateTime.of(2023, 1, 22, 9, 22, 0))
			.endAt(LocalDateTime.of(2023, 2, 22, 22, 22, 22))
			.categoryName("Sports")
			.build();

		productList.add(productDTO);
		return PageResponse.of(new PageImpl<>(productList, PageRequest.of(0, 10), 1L));
	}

	@Test
	@DisplayName("상품 리스트를 조회하다.")
	void getProductList() throws Exception {
		PageResponse<ProductDTO> pageList = createProductDTOPage();

		given(productService.getProductList(any(), any())).willReturn(pageList);

		mockMvc.perform(get(BASIC_URL)
				.queryParam("category", "Sports")
				.queryParam("productName", "자전거"))
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				queryParameters(
					parameterWithName("category").description("카테고리 이름"),
					parameterWithName("productName").description("상품 이름")
				),
				customPageResponseFields(
					List.of(
						fieldWithPath("data.list[].id").type(JsonFieldType.NUMBER).description("아이디"),
						fieldWithPath("data.list[].title").type(JsonFieldType.STRING).description("상품 이름"),
						fieldWithPath("data.list[].mainImg").type(JsonFieldType.STRING).description("상품 메인 이미지"),
						fieldWithPath("data.list[].stock").type(JsonFieldType.NUMBER).description("재고 개수"),
						fieldWithPath("data.list[].starAvg").type(JsonFieldType.NUMBER).description("리뷰 평균 평점"),
						fieldWithPath("data.list[].reviewCount").type(JsonFieldType.NUMBER).description("리뷰 전체 개수"),
						fieldWithPath("data.list[].totalPrice").type(JsonFieldType.NUMBER).description("상품 가격"),
						fieldWithPath("data.list[].startAt").type(JsonFieldType.STRING).description("판매 시작 시간"),
						fieldWithPath("data.list[].endAt").type(JsonFieldType.STRING).description("판매 종료 시간"),
						fieldWithPath("data.list[].categoryName").type(JsonFieldType.STRING).description("카테고리 이름")
					)
				)));

	}

	@Test
	@DisplayName("상품 상세 정보를 조회하다.")
	void getProductDetail() throws Exception {
		ProductDetailDTO productDetailDTO = ProductDetailDTO
			.builder()
			.id(ID)
			.title("good1")
			.content("상품 정보")
			.mainImg("/image/main.jpg")
			.detailImg("/image/detail.jpg")
			.starAvg(2.5)
			.reviewCount(3L)
			.stock(33)
			.totalPrice(9999)
			.deliveryFee(4442)
			.startAt(LocalDateTime.of(2023, 1, 22, 9, 22, 0))
			.endAt(LocalDateTime.of(2023, 2, 22, 22, 22, 22))
			.categoryName("Sports")
			.build();

		given(productService.getProductDetail(ID)).willReturn(productDetailDTO);

		mockMvc.perform(get(BASIC_URL + "/{id}", ID))
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				pathParameters(
					parameterWithName("id").description("상품 ID")
				),
				customResponseFields(
					List.of(
						fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("아이디"),
						fieldWithPath("data.title").type(JsonFieldType.STRING).description("상품 이름"),
						fieldWithPath("data.content").type(JsonFieldType.STRING).description("상품 설명"),
						fieldWithPath("data.mainImg").type(JsonFieldType.STRING).description("상품 메인 이미지"),
						fieldWithPath("data.detailImg").type(JsonFieldType.STRING).description("상품 상세 이미지"),
						fieldWithPath("data.starAvg").type(JsonFieldType.NUMBER).description("리뷰 평균 평점"),
						fieldWithPath("data.reviewCount").type(JsonFieldType.NUMBER).description("리뷰 전체 개수"),
						fieldWithPath("data.stock").type(JsonFieldType.NUMBER).description("재고 개수"),
						fieldWithPath("data.totalPrice").type(JsonFieldType.NUMBER).description("상품 가격"),
						fieldWithPath("data.deliveryFee").type(JsonFieldType.NUMBER).description("배송 가격"),
						fieldWithPath("data.startAt").type(JsonFieldType.STRING).description("판매 시작 시간"),
						fieldWithPath("data.endAt").type(JsonFieldType.STRING).description("판매 종료 시간"),
						fieldWithPath("data.categoryName").type(JsonFieldType.STRING).description("카테고리 이름")
					)
				)));
	}

	@Test
	@DisplayName("상품 등록을 수행하다.")
	void regiProduct() throws Exception {
		ProductCreateRequestDTO productCreateRequestDTO = ProductCreateRequestDTO
			.builder()
			.title("good1")
			.content("상품 정보")
			.mainImg("/image/main.jpg")
			.detailImg("/image/detail.jpg")
			.stock(33)
			.totalPrice(9999)
			.deliveryFee(4442)
			.startAt(LocalDateTime.of(2023, 1, 22, 9, 22, 0))
			.endAt(LocalDateTime.of(2023, 2, 22, 22, 22, 22))
			.categoryId(ID)
			.sellerId(ID)
			.build();

		given(productService.regiProduct(any())).willReturn(true);

		mockMvc.perform(post(BASIC_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(productCreateRequestDTO)))
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				requestFields(
					fieldWithPath("title").type(JsonFieldType.STRING).description("상품 이름"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("상품 정보"),
					fieldWithPath("mainImg").type(JsonFieldType.STRING).description("상품 메인 이미지"),
					fieldWithPath("detailImg").type(JsonFieldType.STRING).description("상품 상세 이미지"),
					fieldWithPath("stock").type(JsonFieldType.NUMBER).description("상품 재고 개수"),
					fieldWithPath("totalPrice").type(JsonFieldType.NUMBER).description("상품 가격"),
					fieldWithPath("deliveryFee").type(JsonFieldType.NUMBER).description("배송 가격"),
					fieldWithPath("startAt").type(JsonFieldType.STRING).description("판매 시작 시간")
						.attributes(field("constraints", "yyyy-MM-dd'T'HH:mm:ss")),
					fieldWithPath("endAt").type(JsonFieldType.STRING).description("판매 종료 시간")
						.attributes(field("constraints", "yyyy-MM-dd'T'HH:mm:ss")),
					fieldWithPath("categoryId").type(JsonFieldType.NUMBER).description("카테고리 ID"),
					fieldWithPath("sellerId").type(JsonFieldType.NUMBER).description("판매자 ID")
				), defaultResponseFields()));
	}

	@Test
	@DisplayName("상품 정보를 수정하다.")
	void updateProduct() throws Exception {
		ProductUpdateDTO productUpdateDTO = ProductUpdateDTO.builder()
			.title("good1")
			.content("상품 정보")
			.mainImg("/image/main.jpg")
			.detailImg("/image/detail.jpg")
			.stock(33)
			.totalPrice(9999)
			.deliveryFee(4442)
			.categoryId(ID)
			.build();

		given(productService.updateProduct(any(), any())).willReturn(productUpdateDTO);

		mockMvc.perform(put(BASIC_URL + "/{id}", ID)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(productUpdateDTO)))
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				pathParameters(
					parameterWithName("id").description("리뷰 ID")
				),
				requestFields(
					fieldWithPath("title").type(JsonFieldType.STRING).description("상품 이름"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("상품 정보"),
					fieldWithPath("mainImg").type(JsonFieldType.STRING).description("상품 메인 이미지"),
					fieldWithPath("detailImg").type(JsonFieldType.STRING).description("상품 상세 이미지"),
					fieldWithPath("stock").type(JsonFieldType.NUMBER).description("상품 재고 개수"),
					fieldWithPath("totalPrice").type(JsonFieldType.NUMBER).description("상품 가격"),
					fieldWithPath("deliveryFee").type(JsonFieldType.NUMBER).description("배송 가격"),
					fieldWithPath("categoryId").type(JsonFieldType.NUMBER).description("카테고리 ID")
				), customResponseFields(
					List.of(
						fieldWithPath("data.title").type(JsonFieldType.STRING).description("상품 이름"),
						fieldWithPath("data.content").type(JsonFieldType.STRING).description("상품 정보"),
						fieldWithPath("data.mainImg").type(JsonFieldType.STRING).description("상품 메인 이미지"),
						fieldWithPath("data.detailImg").type(JsonFieldType.STRING).description("상품 상세 이미지"),
						fieldWithPath("data.stock").type(JsonFieldType.NUMBER).description("상품 재고 개수"),
						fieldWithPath("data.totalPrice").type(JsonFieldType.NUMBER).description("상품 가격"),
						fieldWithPath("data.deliveryFee").type(JsonFieldType.NUMBER).description("배송 가격"),
						fieldWithPath("data.categoryId").type(JsonFieldType.NUMBER).description("카테고리 ID")
					)
				)));
	}

	@Test
	@DisplayName("상품을 삭제하다.")
	void deleteProduct() throws Exception {
		doNothing().when(productService).deleteProduct(ID);

		mockMvc.perform(
				delete(BASIC_URL + "/{id}", ID))
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				pathParameters(
					parameterWithName("id").description("상품 ID")
				), defaultResponseFields()
			));
	}

}
