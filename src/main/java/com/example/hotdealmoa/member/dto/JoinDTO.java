package com.example.hotdealmoa.member.dto;

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
	private String name;

	private String password;

	private String phoneNumber;

	private UserRole userRole;

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
