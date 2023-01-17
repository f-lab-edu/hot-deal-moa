package com.example.hotdealmoa.global.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RegexEnum {

	PHONE_NUMBER("\\d{3}-\\d{4}-\\d{4}", "VALID.PHONE_NUMBER"),
	PASSWORD("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,15}$",
		"VALID.PASSWORD"),
	DUMMY("", "");

	private final String regex;
	private final String message;

}
