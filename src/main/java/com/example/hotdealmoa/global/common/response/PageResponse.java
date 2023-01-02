package com.example.hotdealmoa.global.common.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PageResponse<T> {

	private List<T> list = new ArrayList<>();
	private long totalPage;
	private int pageSize;
	private long totalElements;
	private int pageNumber;

	public static <T> PageResponse<T> of(Page<T> response) {
		return new PageResponse<>(
			response.getContent(),
			response.getTotalPages(),
			response.getSize(),
			response.getTotalElements(),
			response.getNumber()
		);
	}

}
