package com.example.hotdealmoa.member.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.hotdealmoa.member.domain.Member;
import com.example.hotdealmoa.member.dto.JoinDTO;

@Mapper(componentModel = "spring")
public interface JoinMapper {
	JoinMapper INSTANCE = Mappers.getMapper(JoinMapper.class);

	@Mapping(target = "id", ignore = true)
	Member toEntity(JoinDTO joinDTO);
}