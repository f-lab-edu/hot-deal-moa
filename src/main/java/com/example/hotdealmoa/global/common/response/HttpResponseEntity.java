package com.example.hotdealmoa.global.common.response;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.example.hotdealmoa.global.exception.ErrorCode;

import lombok.Getter;

@Getter
public class HttpResponseEntity<T> {
	private final LocalDateTime timestamp = LocalDateTime.now();
	private final int status;
	private final boolean result;
	private final T data;
	private final String message;

	public HttpResponseEntity(String message) {
		this.status = HttpStatus.OK.value();
		this.result = true;
		this.data = null;
		this.message = message;
	}

	public HttpResponseEntity(T data) {
		this.status = HttpStatus.OK.value();
		this.result = true;
		this.data = data;
		this.message = "";
	}

	public HttpResponseEntity(String message, T data) {
		this.status = HttpStatus.OK.value();
		this.result = true;
		this.data = data;
		this.message = message;
	}

	public HttpResponseEntity(ErrorCode errorCode) {
		this.status = errorCode.getStatus().value();
		this.result = false;
		this.data = null;
		this.message = errorCode.getMessage();
	}
}