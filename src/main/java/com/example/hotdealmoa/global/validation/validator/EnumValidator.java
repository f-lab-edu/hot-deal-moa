package com.example.hotdealmoa.global.validation.validator;

import com.example.hotdealmoa.global.util.MessageUtils;
import com.example.hotdealmoa.global.validation.annotation.EnumValid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<EnumValid, Enum<?>> {

	private String message;

	@Override
	public void initialize(EnumValid enumValid) {
		this.message = MessageUtils.getMessage(enumValid.message());
	}

	@Override
	public boolean isValid(Enum value, ConstraintValidatorContext context) {
		if (value == null) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
			return false;
		}

		return true;
	}
}
