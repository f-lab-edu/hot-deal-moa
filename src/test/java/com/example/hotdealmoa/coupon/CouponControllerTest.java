package com.example.hotdealmoa.coupon;

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
import org.springframework.restdocs.payload.JsonFieldType;

import com.example.hotdealmoa.coupon.controller.CouponController;
import com.example.hotdealmoa.coupon.dto.CouponDTO;
import com.example.hotdealmoa.coupon.service.CouponService;
import com.example.hotdealmoa.global.common.response.PageResponse;
import com.example.hotdealmoa.global.config.AbstractControllerTest;
import com.example.hotdealmoa.global.util.MessageUtils;
import com.example.hotdealmoa.member.service.LoginService;

@DisplayName("쿠폰 테스트")
@WebMvcTest(value = CouponController.class, includeFilters = {
	@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = MessageUtils.class)})
public class CouponControllerTest extends AbstractControllerTest {

	private final String BASIC_URL = "/api/coupon";
	private final Long ID = 1L;

	@MockBean
	private LoginService loginService;
	@MockBean
	private CouponService couponService;

	private PageResponse<CouponDTO> createCouponDTOPage() {
		List<CouponDTO> couponList = new ArrayList<>();

		CouponDTO couponDTO = CouponDTO.builder()
			.id(ID)
			.title("2000원 할인쿠폰")
			.discountPrice(2000)
			.isUsed(false)
			.isExpired(false)
			.expiredAt(LocalDateTime.of(2023, 2, 25, 12, 32, 33))
			.build();

		couponList.add(couponDTO);
		return PageResponse.of(new PageImpl<>(couponList, PageRequest.of(0, 10), 1L));
	}

	@Test
	@DisplayName("쿠폰을 조회하다.")
	void getCouponList() throws Exception {
		PageResponse<CouponDTO> pageList = createCouponDTOPage();

		given(couponService.getCouponList(any(), any())).willReturn(pageList);

		mockMvc.perform(get(BASIC_URL)
				.queryParam("memberId", String.valueOf(ID))
				.queryParam("isUsed", "false")
				.queryParam("isExpired", "false"))
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				queryParameters(
					parameterWithName("memberId").description("회원 id값"),
					parameterWithName("isUsed").description("쿠폰 사용 여부"),
					parameterWithName("isExpired").description("쿠폰 만료 여부")
				),
				customPageResponseFields(
					List.of(
						fieldWithPath("data.list[].id").type(JsonFieldType.NUMBER).description("아이디"),
						fieldWithPath("data.list[].title").type(JsonFieldType.STRING).description("쿠폰 이름"),
						fieldWithPath("data.list[].discountPrice").type(JsonFieldType.NUMBER).description("할인 금액"),
						fieldWithPath("data.list[].isUsed").type(JsonFieldType.BOOLEAN).description("쿠폰 사용 여부"),
						fieldWithPath("data.list[].isExpired").type(JsonFieldType.BOOLEAN).description("쿠폰 만료 여부"),
						fieldWithPath("data.list[].expiredAt").type(JsonFieldType.STRING).description("쿠폰 만료 날짜")
					)
				)));
	}
}
