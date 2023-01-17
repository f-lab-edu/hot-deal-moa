package com.example.hotdealmoa.review;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import com.example.hotdealmoa.global.common.response.SliceResponse;
import com.example.hotdealmoa.global.config.AbstractControllerTest;
import com.example.hotdealmoa.global.util.MessageUtils;
import com.example.hotdealmoa.member.service.LoginService;
import com.example.hotdealmoa.review.DTO.ReviewCreateRequestDTO;
import com.example.hotdealmoa.review.DTO.ReviewDTO;
import com.example.hotdealmoa.review.DTO.ReviewUpdateDTO;
import com.example.hotdealmoa.review.controller.ReviewController;
import com.example.hotdealmoa.review.service.ReviewService;

@DisplayName("리뷰 테스트")
@WebMvcTest(value = ReviewController.class, includeFilters = {
	@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = MessageUtils.class)})
public class ReviewTestControllerTest extends AbstractControllerTest {

	private final String BASIC_URL = "/api/review";
	private final Long ID = 1L;

	@MockBean
	private ReviewService reviewService;
	@MockBean
	private LoginService loginService;

	private SliceResponse<ReviewDTO> createReviewDTOPage() {
		List<ReviewDTO> reviewList = new ArrayList<>();
		ReviewDTO reviewDTO = ReviewDTO.builder()
			.id(1L)
			.buyerName("ccc")
			.reviewImg("/images/aaa.jpg")
			.productName("bbb")
			.content("aaa")
			.star(5)
			.orderCount(2)
			.build();

		reviewList.add(reviewDTO);
		return SliceResponse.of(reviewList, PageRequest.of(0, 10));
	}

	@Test
	@DisplayName("리뷰를 조회하다.")
	void getReviewList() throws Exception {
		SliceResponse<ReviewDTO> pageList = createReviewDTOPage();

		given(reviewService.getReviewList(any(), any())).willReturn(pageList);

		mockMvc.perform(get(BASIC_URL)
				.queryParam("productId", String.valueOf(1L))
				.queryParam("buyerName", "ccc")
				.queryParam("lastReviewId", String.valueOf(1L)))
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				queryParameters(
					parameterWithName("productId").description("제품 ID"),
					parameterWithName("buyerName").description("구매자 이름"),
					parameterWithName("lastReviewId").description("마지막 리뷰 id")
				),
				customSliceResponseFields(
					List.of(
						fieldWithPath("data.list[].id").type(JsonFieldType.NUMBER).description("아이디"),
						fieldWithPath("data.list[].reviewImg").type(JsonFieldType.STRING).description("리뷰이미지"),
						fieldWithPath("data.list[].star").type(JsonFieldType.NUMBER).description("별점"),
						fieldWithPath("data.list[].content").type(JsonFieldType.STRING).description("리뷰 내용"),
						fieldWithPath("data.list[].buyerName").type(JsonFieldType.STRING).description("구매자 이름"),
						fieldWithPath("data.list[].productName").type(JsonFieldType.STRING).description("제품 명"),
						fieldWithPath("data.list[].orderCount").type(JsonFieldType.NUMBER).description("구매 개수")
					)
				)));
	}

	@Test
	@DisplayName("리뷰를 등록하다.")
	void createReview() throws Exception {

		ReviewCreateRequestDTO reviewCreateRequestDTO = ReviewCreateRequestDTO.builder()
			.reviewImg("/image/aa.jpg")
			.content("aaa")
			.star(3)
			.orderId(1L)
			.build();

		given(reviewService.createReview(any(), any())).willReturn(true);

		mockMvc.perform(post(BASIC_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(reviewCreateRequestDTO)))
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				requestFields(
					fieldWithPath("reviewImg").type(JsonFieldType.STRING).description("리뷰 이미지"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("리뷰 내용"),
					fieldWithPath("star").type(JsonFieldType.NUMBER).description("평점"),
					fieldWithPath("orderId").type(JsonFieldType.NUMBER).description("주문 ID")
				), defaultResponseFields()));

	}

	@Test
	@DisplayName("리뷰 정보를 수정하다.")
	void updateReview() throws Exception {
		ReviewUpdateDTO reviewUpdateDTO = ReviewUpdateDTO.builder()
			.reviewImg("/image/aa.jpg")
			.content("aaa")
			.star(3)
			.build();

		given(reviewService.updateReview(any(), any())).willReturn(reviewUpdateDTO);

		mockMvc.perform(put(BASIC_URL + "/{id}", ID)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(reviewUpdateDTO)))
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				pathParameters(
					parameterWithName("id").description("리뷰 ID")
				),
				requestFields(
					fieldWithPath("reviewImg").type(JsonFieldType.STRING).description("리뷰 이미지"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("리뷰 내용"),
					fieldWithPath("star").type(JsonFieldType.NUMBER).description("평점")
				), customResponseFields(
					List.of(
						fieldWithPath("data.reviewImg").type(JsonFieldType.STRING).description("리뷰 이미지"),
						fieldWithPath("data.content").type(JsonFieldType.STRING).description("리뷰 내용"),
						fieldWithPath("data.star").type(JsonFieldType.NUMBER).description("평점"))
				)));
	}

	@Test
	@DisplayName("리뷰를 삭제하다.")
	void deleteReview() throws Exception {
		doNothing().when(reviewService).deleteReview(ID);

		mockMvc.perform(
				delete(BASIC_URL + "/{id}", ID))
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				pathParameters(
					parameterWithName("id").description("리뷰 ID")
				)
			));
	}
}
