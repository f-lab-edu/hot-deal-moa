package com.example.hotdealmoa.test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}