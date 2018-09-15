package com.tianduan.base.Util;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {

    private static final String salt = "fadsg45fs64a4b6e54rfgh6vasd";

    private static final Md5PasswordEncoder encoder = new Md5PasswordEncoder();

    public static String encode(String rawPass) {
        return encoder.encodePassword(rawPass, salt);
    }

    public static boolean match(String encPass, String rawPass) {
        return encoder.isPasswordValid(encPass, rawPass, salt);
    }
}
