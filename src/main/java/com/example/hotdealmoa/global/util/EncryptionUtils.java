package com.example.hotdealmoa.global.util;

import org.mindrot.jbcrypt.BCrypt;

public class EncryptionUtils {

	public static String encrypt(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	/**
	 * @param plainPassword 평문 패스워드
	 * @param hashedPassword 암호화된 패스워드
	 * @return true/false
	 */
	public static boolean isMatch(String plainPassword, String hashedPassword) {
		return BCrypt.checkpw(plainPassword, hashedPassword);
	}

}
