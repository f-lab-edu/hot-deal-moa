package com.example.hotdealmoa.global.common.response;

import com.example.hotdealmoa.global.util.MessageUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ResponseEnum {

	// 회원 가입
	NOT_USED_EMAIL("MEMBER.NOT_USED_EMAIL"),
	CREATE_SUCCESS("MEMBER.CREATE_SUCCESS"),

	DUMMY("");

	private final String message;

	public String getMessage() {
		return MessageUtils.getMessage(message);
	}
}
