package com.example.hotdealmoa.member.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.hotdealmoa.global.common.GenericMapper;
import com.example.hotdealmoa.member.domain.Member;
import com.example.hotdealmoa.member.dto.JoinDTO;

@Mapper(componentModel = "spring")
public interface JoinMapper extends GenericMapper<JoinDTO, Member> {
	JoinMapper INSTANCE = Mappers.getMapper(JoinMapper.class);
}