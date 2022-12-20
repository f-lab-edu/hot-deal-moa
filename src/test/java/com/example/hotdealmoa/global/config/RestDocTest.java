package com.example.hotdealmoa.global.config;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.hotdealmoa.test.TestController;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(TestController.class)
public class RestDocTest {

	private MockMvc mockMvc;

	@BeforeEach
	void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
			.apply(documentationConfiguration(restDocumentation))
			.build();
	}

	@Test
	@DisplayName("Spring RestDoc 문서 테스트")
	void restDocTest() throws Exception {

		// then
		mockMvc.perform(get("/test/restDoc"))
			.andDo(print())
			.andExpect(status().isOk())
			.andDo(document("test", responseFields(
				fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
				fieldWithPath("message").type(JsonFieldType.STRING).description("메세지")
			)));

	}

}
