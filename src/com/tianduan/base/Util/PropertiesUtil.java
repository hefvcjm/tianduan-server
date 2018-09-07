package com.tianduan.base.Util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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

}
