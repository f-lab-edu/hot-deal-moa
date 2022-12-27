package com.example.hotdealmoa.member.controller;

import static com.example.hotdealmoa.global.common.response.ResponseHandler.*;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotdealmoa.global.common.response.ResponseEnum;
import com.example.hotdealmoa.global.common.response.SuccessResponse;
import com.example.hotdealmoa.global.exception.CustomException;
import com.example.hotdealmoa.global.exception.ErrorCode;
import com.example.hotdealmoa.member.dto.JoinDTO;
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

	@GetMapping("/email-exists")
	public SuccessResponse<Void> isExistsEmail(@Email @RequestParam("email") String email) {
		if (memberService.isExistsEmail(email)) {
			throw new CustomException(ErrorCode.DUPLICATION_EMAIL);
		}
		return success(ResponseEnum.NOT_USED_EMAIL);
	}

	@PostMapping("/join")
	public SuccessResponse<Void> join(@Valid @RequestBody JoinDTO joinDTO) {
		memberService.join(joinDTO);
		return success(ResponseEnum.CREATE_SUCCESS);
	}
}
