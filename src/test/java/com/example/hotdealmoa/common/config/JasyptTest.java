package com.example.hotdealmoa.common.config;

import static org.assertj.core.api.Assertions.*;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;

public class JasyptTest extends JasyptConfig {

	@Test
	public void jasypt_encrypt_decrypt_test() {
		String plainText = "plainText";

		StandardPBEStringEncryptor jasypt = new StandardPBEStringEncryptor();
		jasypt.setPassword("password"); // Secret key

		String encryptedText = jasypt.encrypt(plainText);
		String decryptedText = jasypt.decrypt(encryptedText);

		System.out.println(encryptedText);

		assertThat(plainText).isEqualTo(decryptedText);
	}
}
