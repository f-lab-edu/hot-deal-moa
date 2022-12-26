package com.example.hotdealmoa.global.validation.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.example.hotdealmoa.global.util.MessageUtils;
import com.example.hotdealmoa.global.validation.annotation.Regex;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class RegexValidator implements ConstraintValidator<Regex, String> {

	private Pattern pattern;
	private String message;

	@Override
	public void initialize(Regex regex) {
		this.pattern = Pattern.compile(regex.value().getRegex());
		this.message = MessageUtils.getMessage(regex.value().getMessage());
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		boolean isValid = pattern.matcher(value).matches();

		if (!isValid) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
		}

		return isValid;
	}
}
