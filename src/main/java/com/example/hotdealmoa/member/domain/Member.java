package com.example.hotdealmoa.member.domain;

import com.example.hotdealmoa.global.common.BaseTimeEntity;
import com.example.hotdealmoa.member.dto.UpdateMemberDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String email;

	@Column
	private String name;

	@Column
	private String password;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Enumerated(EnumType.STRING)
	@Column(name = "user_role")
	private UserRole userRole;

	@Column
	private String address;

	@Builder
	public Member(Long id, String email, String name, String password, String phoneNumber, UserRole userRole,
		String address) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.userRole = userRole;
		this.address = address;
	}

	/**
	 * 패스워드 암호화 처리
	 */
	public void encryptPassword(String password) {
		this.password = password;
	}

	/**
	 * 회원 정보 업데이트
	 */
	public Member updateMember(UpdateMemberDTO updateMemberDTO, String password) {
		this.name = updateMemberDTO.getName();
		this.address = updateMemberDTO.getAddress();
		this.phoneNumber = updateMemberDTO.getPhoneNumber();
		this.password = password;
		return this;
	}

}