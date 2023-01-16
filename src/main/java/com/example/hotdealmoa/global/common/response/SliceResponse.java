package com.example.hotdealmoa.global.common.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SliceResponse<T> {

	private List<T> list = new ArrayList<>();
	private int size;
	private long totalElements;
	private boolean last;

	public static <T> SliceResponse<T> of(List<T> results, Pageable pageable) {
		boolean hasNext = false;

		if (results.size() > pageable.getPageSize()) {
			hasNext = true;
			results.remove(pageable.getPageSize());
		}

		SliceImpl<T> response = new SliceImpl<>(results, pageable, hasNext);

		return new SliceResponse<>(
			response.getContent(),
			response.getSize(),
			response.getNumberOfElements(),
			response.isLast()
		);
	}

}
