package com.example.hotdealmoa.global.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.hotdealmoa.global.common.response.HttpResponseEntity;
import com.example.hotdealmoa.global.common.response.ResponseHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 *  Custom Exception
	 */
	@ExceptionHandler(CustomException.class)
	protected HttpResponseEntity<?> handleCustomException(final CustomException e) {
		log.error("CustomException: {}", e.getErrorCode());
		return ResponseHandler.error(e);
	}
}