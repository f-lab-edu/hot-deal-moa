package com.example.hotdealmoa.global.util;

import org.mindrot.jbcrypt.BCrypt;

public class EncryptionUtils {

	public static String encrypt(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	public static boolean isMatch(String password, String hashedPassword) {
		return BCrypt.checkpw(password, hashedPassword);
	}

}
