package com.tianduan.base.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tianduan.base.JsonResponse;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ChatFilter implements Filter {

    private static Logger logger = Logger.getLogger(LoginFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.debug("ChatFilter");
        filterChain.doFilter(servletRequest, servletResponse);
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        String uri = request.getRequestURI();
//        logger.info(uri);
//        if (uri.contains("/chat")) {
//            request.setAttribute("httpSession", request.getSession());
//            filterChain.doFilter(request, servletResponse);
//            return;
//        } else {
//            HttpSession session = request.getSession();
//            if (session != null) {
//                if (session.getAttribute("user") != null) {
//                    filterChain.doFilter(servletRequest, servletResponse);
//                    return;
//                }
//            }
//        }
    }

    @Override
    public void destroy() {

    }
}
