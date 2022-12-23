package com.example.hotdealmoa.global.common.response;

import org.springframework.http.HttpStatus;

import com.example.hotdealmoa.global.exception.CustomException;
import com.example.hotdealmoa.global.exception.ErrorCode;

public class ResponseHandler {

	public static <T> HttpResponseEntity<T> success(String message) {
		return new HttpResponseEntity<>(message);
	}

	public static <T> HttpResponseEntity<T> success(ResponseEnum responseEnum) {
		return new HttpResponseEntity<>(responseEnum.getMessage());
	}

	public static <T> HttpResponseEntity<T> success(T data) {
		return new HttpResponseEntity<>(data);
	}

	public static <T> HttpResponseEntity<T> success(String message, T data) {
		return new HttpResponseEntity<>(message, data);
	}

	public static <T> HttpResponseEntity<T> error(final CustomException e) {
		return new HttpResponseEntity<>(e.getErrorCode());
	}

	public static <T> HttpResponseEntity<T> error(ErrorCode errorCode) {
		return new HttpResponseEntity<>(errorCode);
	}

	public static <T> HttpResponseEntity<T> error(HttpStatus httpStatus, String message) {
		return new HttpResponseEntity<>(httpStatus, message);
	}
}