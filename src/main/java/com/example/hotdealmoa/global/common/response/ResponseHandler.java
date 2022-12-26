package com.example.hotdealmoa.global.common.response;

import org.springframework.http.HttpStatus;

import com.example.hotdealmoa.global.exception.CustomException;
import com.example.hotdealmoa.global.exception.ErrorCode;

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

	public static <T> SuccessResponse<T> success(String message, T data) {
		return new SuccessResponse<>(message, data);
	}

	public static ErrorResponse error(final CustomException e) {
		return new ErrorResponse(e.getErrorCode());
	}

	public static ErrorResponse error(ErrorCode errorCode) {
		return new ErrorResponse(errorCode);
	}

	public static ErrorResponse error(HttpStatus httpStatus, String message) {
		return new ErrorResponse(httpStatus, message);
	}

	public static ErrorResponse error(HttpStatus httpStatus, String field, String message) {
		return new ErrorResponse(httpStatus, field, message);
	}
}