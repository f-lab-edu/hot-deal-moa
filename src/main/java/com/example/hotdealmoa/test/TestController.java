package com.example.hotdealmoa.test;

import static com.example.hotdealmoa.global.common.response.ResponseHandler.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotdealmoa.global.common.response.ResponseResult;
import com.example.hotdealmoa.global.exception.CustomException;
import com.example.hotdealmoa.global.exception.ErrorCode;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

	@GetMapping
	public String test() {

		String ip;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		}

		return "server: " + ip + " deploy test version 1";
	}

	@GetMapping("/restDoc")
	public ResponseEntity<Map<String, Object>> testRestDoc() {

		Map<String, Object> map = new HashMap<>();

		map.put("name", "aa");
		map.put("message", "hello!");

		return ResponseEntity.ok()
			.body(map);
	}

	// 에러 restapi
	@GetMapping("/success")
	public ResponseResult<?> testSuccess() {
		List<TestMember> list = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			list.add(new TestMember("aa", i));
		}

		return success(list);
	}

	@GetMapping("/success1")
	public ResponseResult<?> testSuccess1() {
		return success();
	}

	@GetMapping("/error")
	public void testError() {
		throw new CustomException(ErrorCode.BAD_REQUEST);
	}

	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class TestMember {
		private String name;
		private Integer age;

		public TestMember(String name, Integer age) {
			this.name = name;
			this.age = age;
		}
	}
}