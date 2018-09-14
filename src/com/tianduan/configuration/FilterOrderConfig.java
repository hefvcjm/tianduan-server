package com.tianduan.configuration;

import com.tianduan.base.filter.AuthorityFilter;
import com.tianduan.base.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.Collections;

@Configuration
public class FilterOrderConfig {

    @Bean
    public Filter loginFilter() {
        return new LoginFilter();
    }

    @Bean
    public FilterRegistrationBean loginFilterRegistrationBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(loginFilter());
        bean.setUrlPatterns(Collections.singleton("/*"));
        bean.setOrder(10);
        return bean;
    }

    @Bean
    public Filter authorityFilter() {
        return new AuthorityFilter();
    }

    @Bean
    public FilterRegistrationBean authorityFilterRegistrationBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(authorityFilter());
        bean.setUrlPatterns(Collections.singleton("/*"));
        bean.setOrder(11);
        return bean;
    }

}
