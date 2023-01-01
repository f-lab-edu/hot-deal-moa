package com.example.hotdealmoa.member.dto;

import org.hibernate.validator.constraints.Length;

import com.example.hotdealmoa.global.validation.RegexEnum;
import com.example.hotdealmoa.global.validation.annotation.EnumValid;
import com.example.hotdealmoa.global.validation.annotation.Regex;
import com.example.hotdealmoa.member.domain.UserRole;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateMemberDTO {

	@NotBlank
	@Regex(RegexEnum.PASSWORD)
	private String password;

	@NotBlank
	@Length(min = 1, max = 20)
	private String name;

	@NotBlank
	@Regex(RegexEnum.PHONE_NUMBER)
	private String phoneNumber;

	@NotBlank
	@Length(min = 1, max = 30)
	private String address;

	@EnumValid(message = "VALID_MEMBER.USER_ROLE")
	private UserRole userRole = UserRole.ROLE_USER;

	@Builder
	public UpdateMemberDTO(String password, String name, String phoneNumber, String address, UserRole userRole) {
		this.password = password;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.userRole = userRole;
	}
}
