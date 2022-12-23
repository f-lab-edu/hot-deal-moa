package com.example.hotdealmoa.global.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.hotdealmoa.global.common.response.HttpResponseEntity;
import com.example.hotdealmoa.global.common.response.ResponseHandler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
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

	/**
	 *  Validation Exception
	 */
	@ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
	protected HttpResponseEntity<?> handleValidException(final Exception e) {

		// @Valid 검증 에러
		if (e instanceof MethodArgumentNotValidException) {
			return ((MethodArgumentNotValidException)e).getBindingResult().getAllErrors()
				.stream().findFirst().map(DefaultMessageSourceResolvable::getDefaultMessage)
				.map(s -> ResponseHandler.error(HttpStatus.BAD_REQUEST, s))
				.orElseGet(() -> ResponseHandler.error(HttpStatus.BAD_REQUEST, e.getMessage()));
		}

		// @Validated 검증 에러
		return ((ConstraintViolationException)e).getConstraintViolations().stream()
			.findFirst().map(ConstraintViolation::getMessage)
			.map(s -> ResponseHandler.error(HttpStatus.BAD_REQUEST, s))
			.orElseGet(() -> ResponseHandler.error(HttpStatus.BAD_REQUEST, e.getMessage()));
	}
}