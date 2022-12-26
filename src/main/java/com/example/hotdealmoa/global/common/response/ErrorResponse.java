package com.example.hotdealmoa.global.common.response;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.example.hotdealmoa.global.exception.ErrorCode;

import lombok.Getter;

@Getter
public class ErrorResponse implements Serializable {
	private final LocalDateTime timestamp = LocalDateTime.now();
	private final int status;
	private final String field;
	private final String message;

	public ErrorResponse(ErrorCode errorCode) {
		this.status = errorCode.getStatus().value();
		this.field = "";
		this.message = errorCode.getMessage();
	}

	public ErrorResponse(HttpStatus httpStatus, String message) {
		this.status = httpStatus.value();
		this.field = "";
		this.message = message;
	}

	public ErrorResponse(HttpStatus httpStatus, String field, String message) {
		this.status = httpStatus.value();
		this.field = field;
		this.message = message;
	}
}
