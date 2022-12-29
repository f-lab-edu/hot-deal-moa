package com.example.hotdealmoa.member.dto;

import com.example.hotdealmoa.global.validation.RegexEnum;
import com.example.hotdealmoa.global.validation.annotation.Regex;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginDTO {

	@Email
	@NotBlank
	private String email;

	@NotBlank
	@Regex(RegexEnum.PASSWORD)
	private String password;

	@Builder
	public LoginDTO(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
