package com.tianduan.base.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tianduan.base.JsonResponse;
import com.tianduan.base.Util.HttpUtil;
import com.tianduan.base.annotation.RequestRole;
import com.tianduan.base.enums.RolesEnum;
import com.tianduan.model.Role;
import com.tianduan.model.User;
import org.apache.log4j.Logger;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class AuthorityFilter implements Filter {

    private static Logger logger = Logger.getLogger(AuthorityFilter.class);

    private String[] excludedUrls;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        excludedUrls = filterConfig.getInitParameter("excludedUrls").replace("\n", "").replace(" ", "").split(",");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("filter two");
        logger.info(Arrays.asList(excludedUrls).toString());
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getServletPath();
        logger.info(path);
        boolean isExcludedUrl = false;
        for (String item : excludedUrls) { //判断是否在过滤url之外
            if (path.contains(item)) {
                isExcludedUrl = true;
                break;
            }
        }
        if (isExcludedUrl) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        } else {
            User user = HttpUtil.getCurrentUser(request);
            String method = request.getMethod();
            String uri = request.getRequestURI();
            String api = method + ":" + uri;
            Method handlerMethod = HttpUtil.getRequestHandlerMethod(api);
            if (handlerMethod == null) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            } else {
                RequestRole requestRole = handlerMethod.getAnnotation(RequestRole.class);
                if (requestRole == null) {
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                } else {
                    RolesEnum[] requestRoles = requestRole.role();
                    Set<Role> userRoles = user.getRoles();
                    Set<String> requestRolesName = new HashSet<>();
                    Set<String> userRolesName = new HashSet<>();
                    for (RolesEnum item : requestRoles) {
                        requestRolesName.add(item.getName());
                    }
                    for (Role role : userRoles) {
                        userRolesName.add(role.getName());
                    }
                    requestRolesName.retainAll(userRoles);
                    if (requestRolesName.size() != 0) {
                        filterChain.doFilter(servletRequest, servletResponse);
                        return;
                    }
                }
            }
            request.setCharacterEncoding("utf-8");
            response.setHeader("Content-type", "application/json;charset=UTF-8");
            response.setCharacterEncoding("utf-8");
            response.getWriter().append(new ObjectMapper().writeValueAsString(new JsonResponse(null, "无权限操作")));
            logger.info("无权限操作");
        }
    }

    @Override
    public void destroy() {

    }
}
