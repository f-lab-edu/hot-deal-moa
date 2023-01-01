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
	public SuccessResponse<Void> isExistsEmail(@Email @RequestParam("email") String email) {
		if (memberService.isExistsEmail(email)) {
			throw new CustomException(ErrorCode.DUPLICATION_EMAIL);
		}
		return success(ResponseEnum.NOT_USED_EMAIL);
	}

	@PostMapping("/join")
	public SuccessResponse<Void> join(@Valid @RequestBody JoinDTO joinDTO) {
		if (!memberService.join(joinDTO)) {
			throw new CustomException(ErrorCode.FAIL);
		}
		return success(ResponseEnum.SUCCESS);
	}

	@PostMapping("/login")
	public SuccessResponse<Void> login(@Valid @RequestBody LoginDTO loginDTO) {
		if (!loginService.login(loginDTO)) {
			throw new CustomException(ErrorCode.FAIL);
		}
		return success(ResponseEnum.SUCCESS);
	}

	@LoginCheck
	@GetMapping("/logout")
	public SuccessResponse<Void> logout() {
		loginService.logout();
		return success(ResponseEnum.SUCCESS);
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
	public SuccessResponse<Void> changePassword(@CurrentUser String email,
		@Valid @RequestBody UpdatePasswordDTO updatePasswordDTO) {
		memberService.changePassword(email, updatePasswordDTO);
		return success(ResponseEnum.PROFILE_UPDATE_SUCCESS);
	}
}
