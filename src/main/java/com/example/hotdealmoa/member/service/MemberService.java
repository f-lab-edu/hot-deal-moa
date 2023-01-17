package com.example.hotdealmoa.member.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hotdealmoa.global.exception.CustomException;
import com.example.hotdealmoa.global.exception.ErrorCode;
import com.example.hotdealmoa.global.util.EncryptionUtils;
import com.example.hotdealmoa.member.domain.Member;
import com.example.hotdealmoa.member.dto.JoinDTO;
import com.example.hotdealmoa.member.dto.MemberDTO;
import com.example.hotdealmoa.member.dto.UpdateMemberDTO;
import com.example.hotdealmoa.member.dto.UpdatePasswordDTO;
import com.example.hotdealmoa.member.mapper.JoinMapper;
import com.example.hotdealmoa.member.mapper.MemberMapper;
import com.example.hotdealmoa.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final JoinMapper joinMapper;
	private final MemberMapper memberMapper;

	@Transactional
	public boolean isExistsEmail(String email) {
		if (StringUtils.isEmpty(email)) {
			throw new CustomException(ErrorCode.INPUT_ERROR);
		}
		return memberRepository.findByEmail(email).isPresent();
	}

	@Transactional
	public boolean join(JoinDTO joinDTO) {
		if (isExistsEmail(joinDTO.getEmail())) {
			throw new CustomException(ErrorCode.DUPLICATION);
		}

		Member member = joinMapper.toEntity(joinDTO);

		String encryptPass = EncryptionUtils.encrypt(member.getPassword());
		member.encryptPassword(encryptPass);

		return memberRepository.save(member).getId() > 0;
	}

	@Transactional(readOnly = true)
	public Member findByEmail(String email) {
		if (StringUtils.isEmpty(email)) {
			throw new CustomException(ErrorCode.INPUT_ERROR);
		}

		return memberRepository.findByEmail(email)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
	}

	public MemberDTO getProfile(String email) {
		Member member = findByEmail(email);
		return memberMapper.toDto(member);
	}

	@Transactional
	public MemberDTO updateProfile(String email, UpdateMemberDTO updateMemberDTO) {
		if (StringUtils.isEmpty(email)) {
			throw new CustomException(ErrorCode.INPUT_ERROR);
		}

		Member member = findByEmail(email);
		String encryptPass = EncryptionUtils.encrypt(member.getPassword());
		member.updateMember(updateMemberDTO, encryptPass);
		return memberMapper.toDto(member);
	}

	@Transactional
	public void changePassword(String email, UpdatePasswordDTO updatePasswordDTO) {
		if (StringUtils.isEmpty(email)) {
			throw new CustomException(ErrorCode.INPUT_ERROR);
		}

		Member member = findByEmail(email);

		if (!EncryptionUtils.isMatch(updatePasswordDTO.getCurrentPassword(), member.getPassword())) {
			throw new CustomException(ErrorCode.PASSWORD_NOT_EQUAL);
		}

		String encryptPassword = EncryptionUtils.encrypt(updatePasswordDTO.getNewPassword());
		member.encryptPassword(encryptPassword);
	}
}