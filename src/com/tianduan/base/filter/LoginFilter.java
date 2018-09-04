package com.tianduan.base.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tianduan.base.JsonResponse;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
@ServletComponentScan
@WebFilter(urlPatterns = "/*", filterName = "loginFilter")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
        return;
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        String uri = request.getRequestURI();
//        if (uri.contains("/login") || uri.contains("/register")) {
//            filterChain.doFilter(servletRequest, servletResponse);
//            return;
//        } else {
//            HttpSession session = request.getSession();
//            if (session != null) {
//                if (session.getAttribute("token") != null) {
//                    filterChain.doFilter(servletRequest, servletResponse);
//                    return;
//                }
//            }
//        }
//        request.setCharacterEncoding("utf-8");
//        response.setHeader("Content-type", "application/json;charset=UTF-8");
//        response.setCharacterEncoding("utf-8");
//        response.getWriter().append(new ObjectMapper().writeValueAsString(new JsonResponse(null, "用户未登录")));
    }

    @Override
    public void destroy() {

    }
}
