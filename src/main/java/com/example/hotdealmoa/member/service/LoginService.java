package com.example.hotdealmoa.member.service;

import static com.example.hotdealmoa.global.common.RedisKey.*;

import org.apache.commons.lang3.StringUtils;
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
	public boolean login(LoginDTO loginDTO) {
		Member member = memberService.findByEmail(loginDTO.getEmail());

		if (!EncryptionUtils.isMatch(loginDTO.getPassword(), member.getPassword())) {
			throw new CustomException(ErrorCode.NOT_FOUND);
		}

		httpSession.setAttribute(USER_ID, member.getEmail());

		return StringUtils.equals(getCurrentUser(), loginDTO.getEmail());
	}

	public void logout() {
		httpSession.invalidate();
	}

	public String getCurrentUser() {
		return (String)httpSession.getAttribute(USER_ID);
	}
}
