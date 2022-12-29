package com.example.hotdealmoa.member.dto;

import com.example.hotdealmoa.member.domain.UserRole;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDTO {

	private Long id;
	private String email;
	private String name;
	private String phoneNumber;
	private String address;
	private UserRole userRole;

	@Builder
	public MemberDTO(Long id, String email, String name, String phoneNumber, String address, UserRole userRole) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.userRole = userRole;
	}
}