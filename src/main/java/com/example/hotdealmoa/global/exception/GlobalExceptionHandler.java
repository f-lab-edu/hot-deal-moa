package com.example.hotdealmoa.global.exception;

import static com.example.hotdealmoa.global.common.response.ResponseHandler.*;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.hotdealmoa.global.common.response.ResponseResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 *  Custom Exception
	 */
	@ExceptionHandler(CustomException.class)
	protected ResponseResult<?> handleCustomException(final CustomException e) {
		log.error("CustomException: {}", e.getErrorCode());
		return error(e);
	}
}