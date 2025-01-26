package com.lucky.newyear.config;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CookieInterceptor())
                .addPathPatterns("/**");  // 모든 요청에 대해 인터셉터 적용
    }
}
