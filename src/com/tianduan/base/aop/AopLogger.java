package com.tianduan.base.aop;

import com.tianduan.base.JsonResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Service
@Scope("prototype")
@Aspect
public class AopLogger {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void logPointCut() {
    }

    @Before("logPointCut()")
    public void beforeRequestLog(JoinPoint joinPoint) {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        Enumeration<String> haeders = request.getHeaderNames();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String api = method + ":" + uri;

    }

    @After("logPointCut()")
    public void afterRequestLog() {

    }

    @AfterThrowing(pointcut = "logPointCut()", throwing = "e")
    public void exceptionLog(Exception e) {

    }

    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResponse")
    public void returningLog(JsonResponse jsonResponse) {

    }
}
