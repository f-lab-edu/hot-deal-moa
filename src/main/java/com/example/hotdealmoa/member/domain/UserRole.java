package com.example.hotdealmoa.member.domain;

import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRole {

	/**
	 * 관리자
	 */
	ROLE_ADMIN("ROLE_ADMIN"),

	/**
	 * 판매자
	 */
	ROLE_SELLER("ROLE_SELLER"),

	/**
	 * 일반 사용자
	 */
	ROLE_USER("ROLE_USER");

	private final String value;

	@JsonCreator(mode = JsonCreator.Mode.DELEGATING)
	public static UserRole fromUserRole(String value) {
		return Stream.of(UserRole.values())
			.filter(userRole -> StringUtils.equals(userRole.getValue(), value))
			.findFirst()
			.orElse(null);
	}
}

