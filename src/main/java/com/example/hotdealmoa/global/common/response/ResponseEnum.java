package com.example.hotdealmoa.global.common.response;

import com.example.hotdealmoa.global.util.MessageUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ResponseEnum {

	// common
	SUCCESS("SUCCESS"),

	// Member
	NOT_USED_EMAIL("MEMBER.NOT_USED_EMAIL"),
	PROFILE_UPDATE_SUCCESS("MEMBER.PROFILE_UPDATE_SUCCESS"),

	DUMMY("");

	private final String message;

	public String getMessage() {
		return MessageUtils.getMessage(message);
	}
}
