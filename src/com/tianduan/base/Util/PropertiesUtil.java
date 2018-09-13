package com.tianduan.base.Util;

import com.tianduan.model.Role;
import com.tianduan.model.User;
import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class PropertiesUtil {

    private static Properties properties = new Properties();

    static {
        init("/properties/authority.properties");
    }

    private static void init(String profile) {
        InputStream inputStream = new BufferedInputStream(PropertiesUtil.class.getResourceAsStream(profile));
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperties(String ksy) {
        return properties.getProperty(ksy);
    }

    public static boolean checkRoleIsOverlap(User user, Role role) {
        return checkRoleIsOverlap(user, role.getName());
    }

    public static boolean checkRoleIsOverlap(User user, String role) {
        Set<Role> roles = user.getRoles();
        if (roles == null) {
            return false;
        }
        String str = getProperties("authority.roles.set.cannot-overlap");
        List<Object> lists = new JSONArray(str).toList();
        for (Object list : lists) {
            for (Role item : roles) {
                if (((List) list).contains(item.getName())) {
                    if (((List) list).contains(role)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


}
