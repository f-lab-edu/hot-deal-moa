package com.example.hotdealmoa.global.common.response;

import com.example.hotdealmoa.global.util.MessageUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ResponseEnum {

	// Member
	NOT_USED_EMAIL("MEMBER.NOT_USED_EMAIL"),
	CREATE_SUCCESS("MEMBER.CREATE_SUCCESS"),
	LOGIN_SUCCESS("MEMBER.LOGIN_SUCCESS"),
	LOGOUT_SUCCESS("MEMBER.LOGOUT_SUCCESS"),
	PROFILE_UPDATE_SUCCESS("MEMBER.PROFILE_UPDATE_SUCCESS"),

	DUMMY("");

	private final String message;

	public String getMessage() {
		return MessageUtils.getMessage(message);
	}
}
