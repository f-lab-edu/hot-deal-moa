package com.example.hotdealmoa.global.util;

import org.apache.commons.lang3.StringUtils;

public class ConfigUtils {

	public static final boolean IS_DEV;

	static {
		String profile = System.getProperty("spring.profiles.active");
		IS_DEV = StringUtils.equals(profile, "dev") || StringUtils.isEmpty(profile);
	}

}
