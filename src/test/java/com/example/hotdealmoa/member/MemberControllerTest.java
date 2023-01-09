package com.example.hotdealmoa.member;

import static com.example.hotdealmoa.global.config.RestDocsConfig.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

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
import com.example.hotdealmoa.member.dto.LoginDTO;
import com.example.hotdealmoa.member.dto.MemberDTO;
import com.example.hotdealmoa.member.dto.UpdateMemberDTO;
import com.example.hotdealmoa.member.dto.UpdatePasswordDTO;
import com.example.hotdealmoa.member.service.LoginService;
import com.example.hotdealmoa.member.service.MemberService;

@DisplayName("회원 테스트")
@WebMvcTest(value = MemberController.class, includeFilters = {
	@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = MessageUtils.class)})
public class MemberControllerTest extends AbstractControllerTest {

	private final String BASIC_URL = "/api/member/";

	@MockBean
	private MemberService memberService;
	@MockBean
	private LoginService loginService;

	@Test
	@DisplayName("회원 가입에 성공하다.")
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
	@DisplayName("회원 가입에 실패하다.")
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
	@DisplayName("해당 이메일 사용이 가능하다.")
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
	@DisplayName("해당 이메일 사용이 불가능하다.")
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

	@Test
	@DisplayName("로그인에 성공하다.")
	void login_success() throws Exception {
		LoginDTO loginDTO = LoginDTO.builder()
			.email("test1234@naver.com")
			.password("Qwe123!@#")
			.build();

		given(loginService.login(any())).willReturn(true);

		mockMvc.perform(post(BASIC_URL + "login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginDTO)))
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				requestFields(fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
					fieldWithPath("password").type(JsonFieldType.STRING).description("패스워드")
						.attributes(field("constraints", "8 ~ 15자"))
				),
				defaultResponseFields()));
	}

	@Test
	@DisplayName("로그인에 실패하다.")
	void login_fail() throws Exception {
		LoginDTO loginDTO = LoginDTO.builder()
			.email("test1234@naver.com")
			.password("Qwe321#@!")
			.build();

		given(loginService.login(loginDTO)).willReturn(false);
		doThrow(new CustomException(ErrorCode.NOT_FOUND)).when(loginService)
			.login(any());

		mockMvc.perform(post(BASIC_URL + "login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginDTO)))
			.andExpect(status().is4xxClientError())
			.andDo(restDocs.document(
				requestFields(fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
					fieldWithPath("password").type(JsonFieldType.STRING).description("패스워드")
						.attributes(field("constraints", "8 ~ 15자"))
				),
				errorResponseFields()));
	}

	@Test
	@DisplayName("로그아웃을 하다.")
	void logout() throws Exception {
		doNothing().when(loginService).logout();
		mockMvc.perform(get(BASIC_URL + "logout"))
			.andExpect(status().isOk())
			.andDo(restDocs.document(defaultResponseFields()));
	}

	MemberDTO getMemberDTO() {
		return MemberDTO.builder()
			.id(1L)
			.email("test123@naver.com")
			.name("test123")
			.phoneNumber("010-1234-5678")
			.userRole(UserRole.ROLE_USER)
			.address("서울시")
			.build();
	}

	@Test
	@DisplayName("회원 상세 정보를 가져오기오다.")
	void getProfile() throws Exception {
		given(memberService.getProfile(any())).willReturn(getMemberDTO());

		mockMvc.perform(get(BASIC_URL + "profile"))
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				customResponseFields(
					List.of(fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("아이디"),
						fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
						fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름"),
						fieldWithPath("data.phoneNumber").type(JsonFieldType.STRING).description("휴대폰번호"),
						fieldWithPath("data.address").type(JsonFieldType.STRING).description("주소"),
						fieldWithPath("data.userRole").type(JsonFieldType.STRING).description("계정권한"))
				)
			));
	}

	@Test
	@DisplayName("회원 정보를 수정하다.")
	void update_profile() throws Exception {
		UpdateMemberDTO updateMemberDTO = UpdateMemberDTO.builder()
			.name("test123")
			.password("Qwe123!@#")
			.phoneNumber("010-1234-1234")
			.userRole(UserRole.ROLE_SELLER)
			.address("seoul")
			.build();

		given(memberService.updateProfile(any(), any())).willReturn(getMemberDTO());

		mockMvc.perform(put(BASIC_URL + "profile")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updateMemberDTO)))
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				requestFields(
					fieldWithPath("name").type(JsonFieldType.STRING).description("이름")
						.attributes(field("constraints", "1 ~ 20자")),
					fieldWithPath("password").type(JsonFieldType.STRING).description("패스워드")
						.attributes(field("constraints", "8 ~ 15자")),
					fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("휴대폰번호"),
					fieldWithPath("address").type(JsonFieldType.STRING).description("주소")
						.attributes(field("constraints", "1 ~ 30자")),
					fieldWithPath("userRole").type(JsonFieldType.STRING).description("계정권한")
				),
				customResponseFields(
					List.of(fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("아이디"),
						fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
						fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름"),
						fieldWithPath("data.phoneNumber").type(JsonFieldType.STRING).description("휴대폰번호"),
						fieldWithPath("data.address").type(JsonFieldType.STRING).description("주소"),
						fieldWithPath("data.userRole").type(JsonFieldType.STRING).description("계정권한"))
				)
			));

	}

	UpdatePasswordDTO getUpdatePasswordDTO() {
		return UpdatePasswordDTO.builder()
			.currentPassword("Qwe123!@#")
			.newPassword("Qwe321#@!")
			.build();
	}

	@Test
	@DisplayName("패스워드 변경에 성공하다.")
	void change_password_success() throws Exception {
		String email = "test1234@naver.com";

		doNothing().when(memberService).changePassword(email, getUpdatePasswordDTO());

		mockMvc.perform(put(BASIC_URL + "change-password")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(getUpdatePasswordDTO())))
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				requestFields(
					fieldWithPath("currentPassword").type(JsonFieldType.STRING).description("기존 패스워드")
						.attributes(field("constraints", "8 ~ 15자")),
					fieldWithPath("newPassword").type(JsonFieldType.STRING).description("새로운 패스워드")
						.attributes(field("constraints", "8 ~ 15자"))
				), defaultResponseFields()
			));
	}

	@Test
	@DisplayName("패스워드 변경에 실패하다.")
	void change_password_fail() throws Exception {
		doThrow(new CustomException(ErrorCode.PASSWORD_NOT_EQUAL))
			.when(memberService).changePassword(any(), any());

		mockMvc.perform(put(BASIC_URL + "change-password")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(getUpdatePasswordDTO())))
			.andExpect(status().is4xxClientError())
			.andDo(restDocs.document(
				requestFields(
					fieldWithPath("currentPassword").type(JsonFieldType.STRING).description("기존 패스워드")
						.attributes(field("constraints", "8 ~ 15자")),
					fieldWithPath("newPassword").type(JsonFieldType.STRING).description("새로운 패스워드")
						.attributes(field("constraints", "8 ~ 15자"))
				), errorResponseFields()
			));
	}

}
