package com.example.hotdealmoa.global.common.response;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;

@Getter
public class ResponseResult<T> {

	private final LocalDateTime timestamp = LocalDateTime.now();
	private boolean success;
	private T response;
	private ErrorResponse error;

	public ResponseResult(boolean success) {
		this.success = success;
	}

	public ResponseResult(T response) {
		this.success = true;
		this.response = response;
	}

	public ResponseResult(ErrorResponse error) {
		this.error = error;
	}
}