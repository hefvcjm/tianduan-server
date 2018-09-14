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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@WebFilter(urlPatterns = "/*", filterName = "authorityFilter")
@Order(2)
public class AuthorityFilter implements Filter {

    private static Logger logger = Logger.getLogger(AuthorityFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        User user = HttpUtil.getCurrentUser(request);
        if (user == null) {
            request.setCharacterEncoding("utf-8");
            response.setHeader("Content-type", "application/json;charset=UTF-8");
            response.setCharacterEncoding("utf-8");
            response.getWriter().append(new ObjectMapper().writeValueAsString(new JsonResponse(null, "用户未登录")));
            logger.info("用户未登录");
            return;
        }
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String api = method + ":" + uri;
        Method handlerMethod = HttpUtil.getRequestHandlerMethod(api);
        if (handlerMethod == null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            RequestRole requestRole = handlerMethod.getAnnotation(RequestRole.class);
            if (requestRole == null) {
                filterChain.doFilter(servletRequest, servletResponse);
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
                }
            }
        }
        request.setCharacterEncoding("utf-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        response.getWriter().append(new ObjectMapper().writeValueAsString(new JsonResponse(null, "无权限操作")));
        logger.info("无权限操作");
    }

    @Override
    public void destroy() {

    }
}
