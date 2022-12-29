package com.example.hotdealmoa.global.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.hotdealmoa.global.exception.CustomException;

public class ResponseHandler {

	public static <T> SuccessResponse<T> success(String message) {
		return new SuccessResponse<>(message);
	}

	public static <T> SuccessResponse<T> success(ResponseEnum responseEnum) {
		return new SuccessResponse<>(responseEnum.getMessage());
	}

	public static <T> SuccessResponse<T> success(T data) {
		return new SuccessResponse<>(data);
	}

	public static <T> SuccessResponse<T> success(ResponseEnum responseEnum, T data) {
		return new SuccessResponse<>(responseEnum.getMessage(), data);
	}

	public static ResponseEntity<ErrorResponse> error(final CustomException e) {
		return ResponseEntity.status(e.getStatus())
			.body(new ErrorResponse(e.getStatus(), e.getMessage()));
	}

	public static ResponseEntity<ErrorResponse> error(HttpStatus httpStatus, String message) {
		return ResponseEntity.status(httpStatus.value())
			.body(new ErrorResponse(httpStatus, message));
	}

	public static ResponseEntity<ErrorResponse> error(HttpStatus httpStatus, String field, String message) {
		return ResponseEntity.status(httpStatus.value())
			.body(new ErrorResponse(httpStatus, field, message));
	}
}