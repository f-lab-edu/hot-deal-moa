package com.example.hotdealmoa.global.common.response;

import com.example.hotdealmoa.global.util.MessageUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ResponseEnum {

	// common
	SUCCESS("SUCCESS.OK"),

	// Member
	NOT_USED_EMAIL("SUCCESS.NOT_USED_EMAIL"),
	PROFILE_UPDATE_SUCCESS("SUCCESS.PROFILE_UPDATE_SUCCESS"),

	DUMMY("");

	private final String message;

	public String getMessage() {
		return MessageUtils.getMessage(message);
	}
}
