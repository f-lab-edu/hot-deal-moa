package com.example.hotdealmoa.global.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.hotdealmoa.global.common.annotation.LoginCheck;
import com.example.hotdealmoa.global.exception.CustomException;
import com.example.hotdealmoa.global.exception.ErrorCode;
import com.example.hotdealmoa.global.util.ConfigUtils;
import com.example.hotdealmoa.member.service.LoginService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginCheckInterceptor implements HandlerInterceptor {

	private final LoginService loginService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

		// dev 환경에서 로그인 체크 무시
		if (ConfigUtils.IS_DEV) {
			return true;
		}

		if (handler instanceof HandlerMethod handlerMethod) {
			LoginCheck loginCheck = handlerMethod.getMethodAnnotation(LoginCheck.class);

			// LoginCheck 어노테이션 여부 체크
			if (null == loginCheck) {
				return true;
			}

			// 현재 로그인 세션 체크
			if (StringUtils.isEmpty(loginService.getCurrentUser())) {
				throw new CustomException(ErrorCode.NOT_LOGIN);
			}
		}

		return true;
	}
}
