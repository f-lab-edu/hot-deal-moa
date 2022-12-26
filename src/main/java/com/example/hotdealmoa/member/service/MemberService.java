package com.example.hotdealmoa.member.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hotdealmoa.global.exception.CustomException;
import com.example.hotdealmoa.global.exception.ErrorCode;
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

	@Transactional(readOnly = true)
	public Member findById(Long id) {
		return memberRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
	}

	@Transactional
	public void isExistsEmail(String email) {
		if (StringUtils.isEmpty(email)) {
			throw new CustomException(ErrorCode.INPUT_ERROR);
		}

		memberRepository.findByEmail(email).ifPresent(member -> {
			throw new CustomException(ErrorCode.DUPLICATION_EMAIL);
		});
	}

	@Transactional
	public void join(JoinDTO joinDTO) {

		// 중복 이메일 체크
		isExistsEmail(joinDTO.getEmail());

		Member member = joinMapper.toEntity(joinDTO);

		// 패스워드 암호화 처리
		member.encryptPassword();

		memberRepository.save(member);
	}
}