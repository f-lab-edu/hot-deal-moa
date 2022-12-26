package com.example.hotdealmoa.global.common.response;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class SuccessResponse<T> implements Serializable {
	private final LocalDateTime timestamp = LocalDateTime.now();
	private final int status = HttpStatus.OK.value();
	private final T data;
	private final String message;

	public SuccessResponse(String message) {
		this.data = null;
		this.message = message;
	}

	public SuccessResponse(T data) {
		this.data = data;
		this.message = "";
	}

	public SuccessResponse(String message, T data) {
		this.data = data;
		this.message = message;
	}
}