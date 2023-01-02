package com.example.hotdealmoa.global.config;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
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
		return responseFields(getDefaultResponseFields());
	}

	protected ResponseFieldsSnippet customResponseFields(List<FieldDescriptor> fieldDescriptors) {
		List<FieldDescriptor> list = getResponseFields();
		list.addAll(fieldDescriptors);
		return responseFields(list);
	}

	protected ResponseFieldsSnippet errorResponseFields() {
		return responseFields(List.of(
			fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태 코드"),
			fieldWithPath("field").type(JsonFieldType.STRING).description("필드"),
			fieldWithPath("message").type(JsonFieldType.STRING).description("메세지")));
	}

	protected List<FieldDescriptor> getResponseFields() {
		List<FieldDescriptor> list = new ArrayList<>();
		list.add(fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태 코드"));
		list.add(fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"));
		return list;
	}

	protected List<FieldDescriptor> getDefaultResponseFields() {
		List<FieldDescriptor> list = new ArrayList<>();
		list.add(fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태 코드"));
		list.add(fieldWithPath("data").type(JsonFieldType.NULL).description("데이터"));
		list.add(fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"));
		return list;
	}

	protected ResponseFieldsSnippet customPageResponseFields(List<FieldDescriptor> fieldDescriptors) {
		List<FieldDescriptor> list = getResponseFields();
		list.addAll(fieldDescriptors);
		list.add(fieldWithPath("data.totalPage").ignored());
		list.add(fieldWithPath("data.pageSize").ignored());
		list.add(fieldWithPath("data.totalElements").ignored());
		list.add(fieldWithPath("data.pageNumber").ignored());
		return responseFields(list);
	}
}