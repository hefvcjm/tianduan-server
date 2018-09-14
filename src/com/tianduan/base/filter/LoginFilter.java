package com.tianduan.base.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tianduan.base.JsonResponse;
import com.tianduan.chat.ChatServer;
import org.apache.log4j.Logger;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = "/*", filterName = "loginFilter")
@Order(1)
public class LoginFilter implements Filter {

    private static Logger logger = Logger.getLogger(LoginFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.debug("filter one");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();
        logger.info(uri);
        if (uri.contains("/login") || uri.contains("/register") || uri.contains("/chat")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        } else {
            HttpSession session = request.getSession();
            if (session != null) {
                if (session.getAttribute("user") != null) {
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }
            }
        }
        request.setCharacterEncoding("utf-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        response.getWriter().append(new ObjectMapper().writeValueAsString(new JsonResponse(null, "用户未登录")));
        logger.info("用户未登录");
    }

    @Override
    public void destroy() {

    }
}
