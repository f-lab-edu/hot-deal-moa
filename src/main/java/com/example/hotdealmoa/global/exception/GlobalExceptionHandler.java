package com.example.hotdealmoa.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.hotdealmoa.global.common.response.ErrorResponse;
import com.example.hotdealmoa.global.common.response.ResponseHandler;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 *  Custom Exception
	 */
	@ExceptionHandler(CustomException.class)
	protected ResponseEntity<ErrorResponse> handleCustomException(final CustomException e) {
		log.error("CustomException", e);
		return ResponseHandler.error(e);
	}

	/**
	 *  Validation Exception
	 */
	@ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
	protected ResponseEntity<ErrorResponse> handleValidException(final Exception e) {
		log.error("Validation Exception", e);

		// @Valid 검증 에러
		if (e instanceof MethodArgumentNotValidException) {
			return ((MethodArgumentNotValidException)e).getBindingResult()
				.getFieldErrors()
				.stream()
				.findFirst()
				.map(fieldError -> ResponseHandler.error(HttpStatus.BAD_REQUEST, fieldError.getField(),
					fieldError.getDefaultMessage()))
				.orElseGet(() -> ResponseHandler.error(HttpStatus.BAD_REQUEST, e.getMessage()));
		}

		// @Validated 검증 에러
		return ((ConstraintViolationException)e).getConstraintViolations()
			.stream()
			.findFirst()
			.map(constraintViolation -> ResponseHandler.error(HttpStatus.BAD_REQUEST, constraintViolation.getMessage()))
			.orElseGet(() -> ResponseHandler.error(HttpStatus.BAD_REQUEST, e.getMessage()));
	}
}