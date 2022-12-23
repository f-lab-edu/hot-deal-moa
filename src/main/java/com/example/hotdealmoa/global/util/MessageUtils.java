package com.example.hotdealmoa.global.util;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageUtils {

	private static MessageSource messageSource;

	public MessageUtils(MessageSource messageSource) {
		MessageUtils.messageSource = messageSource;
	}

	public static String getMessage(String code) {
		return getMessage(code, new Object[] {}, Locale.getDefault());
	}

	public static String getMessage(String code, Object[] arg) {
		return getMessage(code, arg, Locale.getDefault());
	}

	public static String getMessage(String code, Object[] arg, Locale locale) {
		return messageSource.getMessage(code, arg, locale);
	}
}
