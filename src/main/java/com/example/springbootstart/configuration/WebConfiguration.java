package com.example.springbootstart.configuration;

import com.example.springbootstart.interceptor.IdempotentRequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final IdempotentRequestInterceptor idempotentRequestInterceptor;

    public WebConfiguration(IdempotentRequestInterceptor idempotentRequestInterceptor) {
        this.idempotentRequestInterceptor = idempotentRequestInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(idempotentRequestInterceptor)
                .addPathPatterns("/idempotency/**");
    }
}
