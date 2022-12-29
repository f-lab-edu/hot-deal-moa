package com.example.hotdealmoa.member.controller;

import static com.example.hotdealmoa.global.common.response.ResponseHandler.*;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotdealmoa.global.common.annotation.CurrentUser;
import com.example.hotdealmoa.global.common.annotation.LoginCheck;
import com.example.hotdealmoa.global.common.response.ResponseEnum;
import com.example.hotdealmoa.global.common.response.SuccessResponse;
import com.example.hotdealmoa.global.exception.CustomException;
import com.example.hotdealmoa.global.exception.ErrorCode;
import com.example.hotdealmoa.member.dto.JoinDTO;
import com.example.hotdealmoa.member.dto.LoginDTO;
import com.example.hotdealmoa.member.dto.MemberDTO;
import com.example.hotdealmoa.member.dto.UpdateMemberDTO;
import com.example.hotdealmoa.member.dto.UpdatePasswordDTO;
import com.example.hotdealmoa.member.service.LoginService;
import com.example.hotdealmoa.member.service.MemberService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/member")
public class MemberController {

	private final MemberService memberService;
	private final LoginService loginService;

	@GetMapping("/email-exists")
	public SuccessResponse<?> isExistsEmail(@Email @RequestParam("email") String email) {
		if (memberService.isExistsEmail(email)) {
			throw new CustomException(ErrorCode.DUPLICATION_EMAIL);
		}
		return success(ResponseEnum.NOT_USED_EMAIL);
	}

	@PostMapping("/join")
	public SuccessResponse<?> join(@Valid @RequestBody JoinDTO joinDTO) {
		if (!memberService.join(joinDTO)) {
			throw new CustomException(ErrorCode.MEMBER_SIGNUP_FAIL);
		}
		return success(ResponseEnum.CREATE_SUCCESS);
	}

	@PostMapping("/login")
	public SuccessResponse<?> login(@Valid @RequestBody LoginDTO loginDTO) {
		loginService.login(loginDTO);
		return success(ResponseEnum.LOGIN_SUCCESS);
	}

	@LoginCheck
	@GetMapping("/logout")
	public SuccessResponse<?> logout() {
		loginService.logout();
		return success(ResponseEnum.LOGOUT_SUCCESS);
	}

	@LoginCheck
	@GetMapping("/profile")
	public SuccessResponse<MemberDTO> getProfile(@CurrentUser String email) {
		return success(memberService.getProfile(email));
	}

	@LoginCheck
	@PutMapping("/profile")
	public SuccessResponse<MemberDTO> updateProfile(@CurrentUser String email,
		@Valid @RequestBody UpdateMemberDTO updateMemberDTO) {
		return success(ResponseEnum.PROFILE_UPDATE_SUCCESS, memberService.updateProfile(email, updateMemberDTO));
	}

	@LoginCheck
	@PutMapping("/change-password")
	public SuccessResponse<?> changePassword(@CurrentUser String email,
		@Valid @RequestBody UpdatePasswordDTO updatePasswordDTO) {
		memberService.changePassword(email, updatePasswordDTO);
		return success(ResponseEnum.PROFILE_UPDATE_SUCCESS);
	}
}
