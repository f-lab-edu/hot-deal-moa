package com.example.hotdealmoa.global.common.response;

import com.example.hotdealmoa.global.exception.ErrorCode;

import lombok.Getter;

@Getter
public class ErrorResponse {

	private final int status;
	private final String error;
	private final String message;

	public ErrorResponse(ErrorCode errorCode) {
		this.status = errorCode.getStatus().value();
		this.error = errorCode.getStatus().name();
		this.message = errorCode.getMessage();
	}
}