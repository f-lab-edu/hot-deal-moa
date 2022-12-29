package com.example.hotdealmoa.member.service;

import static com.example.hotdealmoa.global.common.RedisKey.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hotdealmoa.global.exception.CustomException;
import com.example.hotdealmoa.global.exception.ErrorCode;
import com.example.hotdealmoa.global.util.EncryptionUtils;
import com.example.hotdealmoa.member.domain.Member;
import com.example.hotdealmoa.member.dto.LoginDTO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

	private final MemberService memberService;
	private final HttpSession httpSession;

	@Transactional(readOnly = true)
	public void login(LoginDTO loginDTO) {
		Member member = memberService.findByEmail(loginDTO.getEmail());

		if (!EncryptionUtils.isMatch(loginDTO.getPassword(), member.getPassword())) {
			throw new CustomException(ErrorCode.USER_NOT_FOUND);
		}

		// Redis 서버에 로그인 세션 저장
		httpSession.setAttribute(USER_ID, member.getEmail());
	}

	public void logout() {
		httpSession.invalidate();
	}

	public String getCurrentUser() {
		return (String)httpSession.getAttribute(USER_ID);
	}
}
