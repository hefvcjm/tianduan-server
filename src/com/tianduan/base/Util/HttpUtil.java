package com.tianduan.base.Util;

import com.tianduan.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    private static Map<String, Method> methodMap = new HashMap<>();

    private static boolean initFlag = false;

    public static void initHandlerMethodMap(Map<RequestMappingInfo, HandlerMethod> map) {
        if (initFlag) {
            return;
        }
        initFlag = true;
        for (RequestMappingInfo key : map.keySet()) {
            HandlerMethod handlerMethod = map.get(key);
            Method method = handlerMethod.getMethod();
            PatternsRequestCondition prc = key.getPatternsCondition();
            Set<String> patterns = prc.getPatterns();
            RequestMethod[] methods = handlerMethod.getMethod().getAnnotation(RequestMapping.class).method();
            for (RequestMethod requestMethod : methods) {
                for (String uStr : patterns) {
                    String api = requestMethod + ":/tianduan" + uStr;
                    if (!methodMap.keySet().contains(api))
                        methodMap.put(api, method);
                }
            }
        }
        System.out.println(methodMap.keySet());
    }

    public static Method getRequestHandlerMethod(String api) {
        if (methodMap.keySet().contains(api)) {
            return methodMap.get(api);
        }
        return null;
    }

}
