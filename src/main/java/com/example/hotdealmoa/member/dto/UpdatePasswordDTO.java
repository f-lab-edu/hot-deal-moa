package com.example.hotdealmoa.member.dto;

import com.example.hotdealmoa.global.validation.RegexEnum;
import com.example.hotdealmoa.global.validation.annotation.Regex;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdatePasswordDTO {

	@NotBlank
	@Regex(RegexEnum.PASSWORD)
	private String currentPassword;

	@NotBlank
	@Regex(RegexEnum.PASSWORD)
	private String newPassword;

	@Builder
	public UpdatePasswordDTO(String currentPassword, String newPassword) {
		this.currentPassword = currentPassword;
		this.newPassword = newPassword;
	}



}
