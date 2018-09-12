package com.tianduan.base.Util;

import com.tianduan.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HttpUtil {

    public static String KEY_USER = "user";

    public static User getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            Object obj = session.getAttribute(KEY_USER);
            if (obj != null) {
                User user = (User) obj;
                return user;
            }
        }
        return null;
    }

}
