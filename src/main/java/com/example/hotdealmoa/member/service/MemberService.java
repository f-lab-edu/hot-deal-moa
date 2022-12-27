package com.example.hotdealmoa.member.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hotdealmoa.global.exception.CustomException;
import com.example.hotdealmoa.global.exception.ErrorCode;
import com.example.hotdealmoa.global.util.EncryptionUtils;
import com.example.hotdealmoa.member.domain.Member;
import com.example.hotdealmoa.member.dto.JoinDTO;
import com.example.hotdealmoa.member.mapper.JoinMapper;
import com.example.hotdealmoa.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final JoinMapper joinMapper;

	@Transactional
	public boolean isExistsEmail(String email) {
		if (StringUtils.isEmpty(email)) {
			throw new CustomException(ErrorCode.INPUT_ERROR);
		}
		return memberRepository.findByEmail(email).isPresent();
	}

	@Transactional
	public void join(JoinDTO joinDTO) {

		// 중복 이메일 체크
		if (isExistsEmail(joinDTO.getEmail())) {
			throw new CustomException(ErrorCode.DUPLICATION_EMAIL);
		}
		
		Member member = joinMapper.toEntity(joinDTO);

		String encryptPass = EncryptionUtils.encrypt(member.getPassword());  // 패스워드 암호화 처리
		member.encryptPassword(encryptPass);
		memberRepository.save(member);
	}
}