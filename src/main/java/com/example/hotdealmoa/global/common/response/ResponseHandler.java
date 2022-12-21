package com.example.hotdealmoa.global.common.response;

import com.example.hotdealmoa.global.exception.CustomException;
import com.example.hotdealmoa.global.exception.ErrorCode;

public class ResponseHandler {

	public static <T> ResponseResult<T> success() {
		return new ResponseResult<>(true);
	}

	public static <T> ResponseResult<T> success(T response) {
		return new ResponseResult<>(response);
	}

	public static <T> ResponseResult<T> error(final CustomException e) {
		return new ResponseResult<>(new ErrorResponse(e.getErrorCode()));
	}

	public static <T> ResponseResult<T> error(ErrorCode errorCode) {
		return new ResponseResult<>(new ErrorResponse(errorCode));
	}
}