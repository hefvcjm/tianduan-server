package com.tianduan.base.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class PasswordUtil {

    private static final String SECRET = "tianduan-app";

    @Autowired
    private static Md5PasswordEncoder encoder;

    public static String encode(CharSequence rawPassword) {
        return encoder.encodePassword(rawPassword.toString(), SECRET);
    }


    public static boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encoder.isPasswordValid(encodedPassword, rawPassword.toString(), SECRET);
    }
}
