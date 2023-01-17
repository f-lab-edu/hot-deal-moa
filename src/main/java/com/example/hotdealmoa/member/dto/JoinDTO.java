package com.example.hotdealmoa.member.dto;

import org.hibernate.validator.constraints.Length;

import com.example.hotdealmoa.global.validation.RegexEnum;
import com.example.hotdealmoa.global.validation.annotation.EnumValid;
import com.example.hotdealmoa.global.validation.annotation.Regex;
import com.example.hotdealmoa.member.domain.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinDTO {

	@Email
	@NotBlank
	private String email;

	@NotBlank
	@Length(min = 1, max = 20)
	private String name;

	@NotBlank
	@Regex(RegexEnum.PASSWORD)
	private String password;

	@NotBlank
	@Regex(RegexEnum.PHONE_NUMBER)
	private String phoneNumber;

	@EnumValid(message = "VALID.USER_ROLE")
	private UserRole userRole = UserRole.ROLE_USER;
	@NotBlank
	@Length(min = 1, max = 30)
	private String address;

	@Builder
	public JoinDTO(String email, String name, String password, String phoneNumber, UserRole userRole, String address) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.userRole = userRole;
		this.address = address;
	}
}
