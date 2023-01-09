package com.example.hotdealmoa.order;

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
import com.example.hotdealmoa.order.controller.OrderController;
import com.example.hotdealmoa.order.domain.OrderStatus;
import com.example.hotdealmoa.order.dto.OrderCreateRequestDTO;
import com.example.hotdealmoa.order.dto.OrderDetailDTO;
import com.example.hotdealmoa.order.dto.OrderInfoResponseDTO;
import com.example.hotdealmoa.order.dto.OrderListDTO;
import com.example.hotdealmoa.order.dto.OrderStatusDTO;
import com.example.hotdealmoa.order.service.OrderService;

@DisplayName("주문 테스트")
@WebMvcTest(value = OrderController.class, includeFilters = {
	@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = MessageUtils.class)})
public class OrderControllerTest extends AbstractControllerTest {

	private final String BASIC_URL = "/api/order";

	@MockBean
	private OrderService orderService;

	@MockBean
	private LoginService loginService;

	@Test
	@DisplayName("주문 전 결제 정보를 가져오다.")
	void getOrderInfo() throws Exception {
		OrderInfoResponseDTO orderInfoResponseDTO = OrderInfoResponseDTO
			.builder()
			.memberId(1L)
			.name("member1")
			.email("aaa@naver.com")
			.phoneNumber("010-1234-5678")
			.productMainImg("/images/product1.jpg")
			.address("seoul")
			.productId(1L)
			.productTitle("iphone")
			.productCount(2)
			.totalPrice(20000)
			.deliveryFee(5000)
			.couponId(1L)
			.discountPrice(3000)
			.build();

		given(orderService.getOrderInfo(any(), any())).willReturn(orderInfoResponseDTO);

		mockMvc.perform(get(BASIC_URL + "/info")
				.param("productId", String.valueOf(1L))
				.param("couponId", String.valueOf(1L))
				.param("productCount", String.valueOf(2)))
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				queryParameters(
					parameterWithName("productId").description("상품 id"),
					parameterWithName("couponId").description("쿠폰 id"),
					parameterWithName("productCount").description("상품 구매 개수")
				),
				customResponseFields(List.of(
					fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("구매자 id"),
					fieldWithPath("data.name").type(JsonFieldType.STRING).description("구매자 이름"),
					fieldWithPath("data.email").type(JsonFieldType.STRING).description("구매자 이메일"),
					fieldWithPath("data.phoneNumber").type(JsonFieldType.STRING).description("구매자 핸드폰 번호"),
					fieldWithPath("data.address").type(JsonFieldType.STRING).description("구매자 주소"),
					fieldWithPath("data.productId").type(JsonFieldType.NUMBER).description("상품 id"),
					fieldWithPath("data.productTitle").type(JsonFieldType.STRING).description("상품 이름"),
					fieldWithPath("data.productMainImg").type(JsonFieldType.STRING).description("상품 이미지"),
					fieldWithPath("data.productCount").type(JsonFieldType.NUMBER).description("상품 구매 개수"),
					fieldWithPath("data.totalPrice").type(JsonFieldType.NUMBER).description("총 상품 가격"),
					fieldWithPath("data.deliveryFee").type(JsonFieldType.NUMBER).description("배송비"),
					fieldWithPath("data.couponId").type(JsonFieldType.NUMBER).description("쿠폰 id"),
					fieldWithPath("data.discountPrice").type(JsonFieldType.NUMBER).description("쿠폰 할인 금액")
				))));
	}

	@Test
	@DisplayName("주문 상세 정보를 가져오다.")
	void getOrderDetailInfo() throws Exception {

		OrderDetailDTO orderDetailDTO = OrderDetailDTO
			.builder()
			.id(1L)
			.productTitle("product1")
			.productCount(2)
			.productMainImg("/images/product1.jpg")
			.requestMessage("quick")
			.orderStatus(OrderStatus.ORDERED)
			.name("member1")
			.email("aaa@naver.com")
			.phoneNumber("010-1234-5678")
			.address("seoul")
			.totalPrice(20000)
			.deliveryFee(5000)
			.discountPrice(3000)
			.paymentPrice(12000)
			.build();

		given(orderService.getOrderDetailInfo(any())).willReturn(orderDetailDTO);

		mockMvc.perform(get(BASIC_URL + "/{id}", 1L))
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				pathParameters(parameterWithName("id").description("주문 ID")),
				customResponseFields(
					List.of(
						fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("구매자 id"),
						fieldWithPath("data.productTitle").type(JsonFieldType.STRING).description("상품 이름"),
						fieldWithPath("data.productCount").type(JsonFieldType.NUMBER).description("상품 구매 개수"),
						fieldWithPath("data.productMainImg").type(JsonFieldType.STRING).description("상품 이미지"),
						fieldWithPath("data.requestMessage").type(JsonFieldType.STRING).description("주문 요청 메세지"),
						fieldWithPath("data.orderStatus").type(JsonFieldType.STRING).description("주문 상태"),
						fieldWithPath("data.name").type(JsonFieldType.STRING).description("구매자 이름"),
						fieldWithPath("data.email").type(JsonFieldType.STRING).description("구매자 이메일"),
						fieldWithPath("data.phoneNumber").type(JsonFieldType.STRING).description("구매자 핸드폰 번호"),
						fieldWithPath("data.address").type(JsonFieldType.STRING).description("구매자 주소"),
						fieldWithPath("data.totalPrice").type(JsonFieldType.NUMBER).description("총 상품 가격"),
						fieldWithPath("data.deliveryFee").type(JsonFieldType.NUMBER).description("배송비"),
						fieldWithPath("data.discountPrice").type(JsonFieldType.NUMBER).description("쿠폰 할인 금액"),
						fieldWithPath("data.paymentPrice").type(JsonFieldType.NUMBER).description("결제할 금액")
					))));
	}

	private PageResponse<OrderListDTO> createOrderListDTOPage() {
		List<OrderListDTO> orderList = new ArrayList<>();

		OrderListDTO orderListDTO = OrderListDTO
			.builder()
			.productId(1L)
			.productName("product1")
			.productMainImg("/images/product1.jpg")
			.orderDate(LocalDateTime.of(2023, 1, 22, 9, 22, 0))
			.productCount(2)
			.orderStatus(OrderStatus.ORDERED)
			.orderId(1L)
			.build();

		orderList.add(orderListDTO);
		return PageResponse.of(new PageImpl<>(orderList, PageRequest.of(0, 10), 1L));
	}

	@Test
	@DisplayName("주문 리스트를 가져오다.")
	void getOrderList() throws Exception {
		PageResponse<OrderListDTO> pageList = createOrderListDTOPage();

		given(orderService.getOrderList(any(), any())).willReturn(pageList);

		mockMvc.perform(get(BASIC_URL))
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				customPageResponseFields(
					List.of(
						fieldWithPath("data.list[].productId").type(JsonFieldType.NUMBER).description("구매자 id"),
						fieldWithPath("data.list[].productName").type(JsonFieldType.STRING).description("상품 이름"),
						fieldWithPath("data.list[].productMainImg").type(JsonFieldType.STRING).description("상품 이미지"),
						fieldWithPath("data.list[].productCount").type(JsonFieldType.NUMBER).description("상품 개수"),
						fieldWithPath("data.list[].orderDate").type(JsonFieldType.STRING).description("주문 날짜"),
						fieldWithPath("data.list[].orderStatus").type(JsonFieldType.STRING).description("주문 상태"),
						fieldWithPath("data.list[].orderId").type(JsonFieldType.NUMBER).description("주문 id")
					))));
	}

	@Test
	@DisplayName("주문을 진행을 성공 하다.")
	void createOrderSuccess() throws Exception {

		OrderCreateRequestDTO orderCreateRequestDTO = OrderCreateRequestDTO
			.builder()
			.phoneNumber("010-1234-5678")
			.requestAddress("seoul")
			.requestMessage("quick")
			.productCount(2)
			.orderStatus(OrderStatus.ORDERED)
			.productId(1L)
			.memberId(1L)
			.couponId(1L)
			.build();

		given(orderService.createOrder(any())).willReturn(true);

		mockMvc.perform(post(BASIC_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(orderCreateRequestDTO)))
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				requestFields(
					fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("구매자 전화번호"),
					fieldWithPath("requestAddress").type(JsonFieldType.STRING).description("상품 구매자 주소"),
					fieldWithPath("requestMessage").type(JsonFieldType.STRING).description("주문 요청 메세지"),
					fieldWithPath("productCount").type(JsonFieldType.NUMBER).description("주문 상품 개수"),
					fieldWithPath("orderStatus").type(JsonFieldType.STRING).description("주문 상태"),
					fieldWithPath("productId").type(JsonFieldType.NUMBER).description("상품 id"),
					fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 id"),
					fieldWithPath("couponId").type(JsonFieldType.NUMBER).description("쿠폰 id")
				), defaultResponseFields()));

	}

	@Test
	@DisplayName("주문을 진행을 실패 하다.")
	void createOrderFail() throws Exception {

		OrderCreateRequestDTO orderCreateRequestDTO = OrderCreateRequestDTO
			.builder()
			.phoneNumber("010-1234-5678")
			.requestAddress("seoul")
			.requestMessage("quick")
			.productCount(2)
			.orderStatus(OrderStatus.ORDERED)
			.productId(1L)
			.memberId(1L)
			.couponId(1L)
			.build();

		given(orderService.createOrder(any())).willReturn(false);

		mockMvc.perform(post(BASIC_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(orderCreateRequestDTO)))
			.andExpect(status().is4xxClientError())
			.andDo(restDocs.document(
				requestFields(
					fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("구매자 전화번호"),
					fieldWithPath("requestAddress").type(JsonFieldType.STRING).description("상품 구매자 주소"),
					fieldWithPath("requestMessage").type(JsonFieldType.STRING).description("주문 요청 메세지"),
					fieldWithPath("productCount").type(JsonFieldType.NUMBER).description("주문 상품 개수"),
					fieldWithPath("orderStatus").type(JsonFieldType.STRING).description("주문 상태"),
					fieldWithPath("productId").type(JsonFieldType.NUMBER).description("상품 id"),
					fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 id"),
					fieldWithPath("couponId").type(JsonFieldType.NUMBER).description("쿠폰 id")
				), errorResponseFields()));

	}

	@Test
	@DisplayName("주문 상태를 업데이트 하다.")
	void updateOrderStatus() throws Exception {

		OrderStatusDTO orderStatusDTO = OrderStatusDTO.builder()
			.orderStatus(OrderStatus.IN_DELIVERY)
			.build();

		given(orderService.updateOrderStatus(any(), any())).willReturn(orderStatusDTO);

		mockMvc.perform(put(BASIC_URL + "/{id}", 1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(orderStatusDTO)))
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				pathParameters(parameterWithName("id").description("주문 ID")),
				requestFields(fieldWithPath("orderStatus").type(JsonFieldType.STRING).description("주문 상태")),
				customResponseFields(
					List.of(fieldWithPath("data.orderStatus").type(JsonFieldType.STRING).description("주문 상태")))
			));
	}
}
