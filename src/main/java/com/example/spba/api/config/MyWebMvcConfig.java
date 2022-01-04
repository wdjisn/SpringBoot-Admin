package com.example.spba.api.config;

import com.example.spba.api.interceptor.SpbaInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer
{

    @Bean
    public SpbaInterceptor spbaInterceptor()
    {
        return new SpbaInterceptor();
    }

    /**
     * 拦截器
     * addPathPatterns 用于添加拦截规则
     * excludePathPatterns 用于排除拦截
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        registry.addInterceptor(spbaInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login");
    }
}
