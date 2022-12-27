package com.example.hotdealmoa.global.config;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(RestDocumentationExtension.class)
@Import(RestDocsConfig.class)
public abstract class AbstractControllerTest {

	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

	@Autowired
	protected RestDocumentationResultHandler restDocs;

	@BeforeEach
	void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
			.apply(documentationConfiguration(restDocumentation))
			.alwaysDo(MockMvcResultHandlers.print())
			.alwaysDo(restDocs)
			.addFilters(new CharacterEncodingFilter("UTF-8", true))
			.build();
	}

	protected ResponseFieldsSnippet defaultResponseFields() {
		return responseFields(fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태 코드"),
			fieldWithPath("data").type(JsonFieldType.NULL).description("데이터"),
			fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"));
	}

	protected ResponseFieldsSnippet ErrorResponseFields() {
		return responseFields(fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태 코드"),
			fieldWithPath("field").type(JsonFieldType.STRING).description("필드"),
			fieldWithPath("message").type(JsonFieldType.STRING).description("중복된 이메일"));
	}

}