package com.example.hotdealmoa.member;

import static com.example.hotdealmoa.global.config.RestDocsConfig.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import com.example.hotdealmoa.global.config.AbstractControllerTest;
import com.example.hotdealmoa.global.exception.CustomException;
import com.example.hotdealmoa.global.exception.ErrorCode;
import com.example.hotdealmoa.global.util.MessageUtils;
import com.example.hotdealmoa.member.controller.MemberController;
import com.example.hotdealmoa.member.domain.UserRole;
import com.example.hotdealmoa.member.dto.JoinDTO;
import com.example.hotdealmoa.member.service.MemberService;

@WebMvcTest(value = MemberController.class, includeFilters = {
	@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = MessageUtils.class)})
public class MemberControllerTest extends AbstractControllerTest {

	private final String BASIC_URL = "/api/member/";

	@MockBean
	private MemberService memberService;

	@Test
	@DisplayName("회원 가입 성공 테스트")
	void join_success() throws Exception {
		JoinDTO joinDTO = JoinDTO.builder()
			.email("test1234@naver.com")
			.password("Qwe123!@#")
			.name("test1234")
			.phoneNumber("010-1234-5678")
			.userRole(UserRole.ROLE_USER)
			.address("서울시")
			.build();

		given(memberService.join(any())).willReturn(true);

		mockMvc.perform(post(BASIC_URL + "join").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(joinDTO)))
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				requestFields(fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
					fieldWithPath("password").type(JsonFieldType.STRING).description("패스워드")
						.attributes(field("constraints", "8 ~ 15자")),
					fieldWithPath("name").type(JsonFieldType.STRING).description("이름")
						.attributes(field("constraints", "1 ~ 20자")),
					fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("휴대폰번호"),
					fieldWithPath("userRole").type(JsonFieldType.STRING).description("계정권한"),
					fieldWithPath("address").type(JsonFieldType.STRING).description("주소")
						.attributes(field("constraints", "1 ~ 30자")))
				, defaultResponseFields()));
	}

	@Test
	@DisplayName("회원 가입 실패 테스트")
	void join_fail() throws Exception {
		JoinDTO joinDTO = JoinDTO.builder()
			.email("test1234@naver.com")
			.password("Qwe123!@#")
			.name("test1234")
			.phoneNumber("010-1234-5678")
			.userRole(UserRole.ROLE_USER)
			.address("서울시")
			.build();

		given(memberService.join(any())).willReturn(false);

		mockMvc.perform(post(BASIC_URL + "join").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(joinDTO)))
			.andExpect(status().is4xxClientError())
			.andDo(restDocs.document(
				requestFields(fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
					fieldWithPath("password").type(JsonFieldType.STRING).description("패스워드")
						.attributes(field("constraints", "8 ~ 15자")),
					fieldWithPath("name").type(JsonFieldType.STRING).description("이름")
						.attributes(field("constraints", "1 ~ 20자")),
					fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("휴대폰번호"),
					fieldWithPath("userRole").type(JsonFieldType.STRING).description("계정권한"),
					fieldWithPath("address").type(JsonFieldType.STRING).description("주소")
						.attributes(field("constraints", "1 ~ 30자")))
				, errorResponseFields()));
	}

	@Test
	@DisplayName("사용 가능한 이메일")
	void email_notExists() throws Exception {

		String email = "aaa@naver.com";

		given(memberService.isExistsEmail(email)).willReturn(false);

		mockMvc.perform(get(BASIC_URL + "email-exists")
				.contentType(MediaType.APPLICATION_JSON)
				.param("email", email))
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				queryParameters(
					parameterWithName("email").description("이메일")
				),
				defaultResponseFields()));
	}

	@Test
	@DisplayName("이미 사용중 인 이메일")
	void email_exists() throws Exception {

		String email = "bbb@naver.com";

		doThrow(new CustomException(ErrorCode.DUPLICATION_EMAIL)).when(memberService)
			.isExistsEmail(email);

		mockMvc.perform(get(BASIC_URL + "email-exists")
				.contentType(MediaType.APPLICATION_JSON)
				.param("email", email))
			.andExpect(status().is4xxClientError())
			.andDo(restDocs.document(
				queryParameters(
					parameterWithName("email").description("이메일")
				),
				errorResponseFields()));
	}

}
