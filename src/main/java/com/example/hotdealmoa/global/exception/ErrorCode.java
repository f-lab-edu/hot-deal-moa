package com.example.hotdealmoa.global.exception;

import org.springframework.http.HttpStatus;

import com.example.hotdealmoa.global.util.MessageUtils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	BAD_REQUEST(HttpStatus.BAD_REQUEST, "ERROR_CODE.BAD_REQUEST"),
	DUPLICATION(HttpStatus.CONFLICT, "ERROR_CODE.DUPLICATION"),
	NOT_FOUND(HttpStatus.NOT_FOUND, "ERROR_CODE.NOT_FOUND"),
	FAIL(HttpStatus.CONFLICT, "ERROR_CODE.FAIL"),
	PASSWORD_NOT_EQUAL(HttpStatus.CONFLICT, "ERROR_CODE.PASSWORD_NOT_EQUAL"),
	INPUT_ERROR(HttpStatus.BAD_REQUEST, "ERROR_CODE.INPUT_ERROR"),
	ORDER_ERROR(HttpStatus.BAD_REQUEST, "ERROR_CODE.ORDER_COUNT"),
	COUPON_ERROR(HttpStatus.BAD_REQUEST, "ERROR_CODE.COUPON_USED"),
	COUPON_OWN_MEMBER(HttpStatus.BAD_REQUEST, "ERROR_CODE.COUPON_OWN_MEMBER"),
	NOT_LOGIN(HttpStatus.UNAUTHORIZED, "ERROR_CODE.NOT_LOGIN"),
	NOT_AUTHORIZED(HttpStatus.UNAUTHORIZED, "ERROR_CODE.NOT_AUTHORIZED"),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_CODE.INTERNAL_SERVER_ERROR"),
	DUMMY(null, "");

	private final HttpStatus status;
	private final String message;

	public String getMessage() {
		return MessageUtils.getMessage(message);
	}
}
