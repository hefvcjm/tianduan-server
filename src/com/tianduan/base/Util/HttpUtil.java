package com.tianduan.base.Util;

import com.tianduan.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HttpUtil {

    public static User getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            Object obj = session.getAttribute("token");
            if (obj != null) {
                User user = (User) obj;
                return user;
            }
        }
        return null;
    }

}
