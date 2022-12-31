package com.example.hotdealmoa.member.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.hotdealmoa.member.domain.Member;
import com.example.hotdealmoa.member.dto.MemberDTO;

@Mapper(componentModel = "spring")
public interface MemberMapper {
	MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

	MemberDTO toDto(Member member);
}
