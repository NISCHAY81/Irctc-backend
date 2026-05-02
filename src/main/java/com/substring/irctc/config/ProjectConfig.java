package com.substring.irctc.config;

import com.substring.irctc.interceptors.MyCustomInterceptors;
import com.substring.irctc.interceptors.TimeLoggerInterceptor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ProjectConfig implements WebMvcConfigurer {

//    @Autowired
//    private MyCustomInterceptors myCustomInterceptors;
//
//    @Autowired
//    private TimeLoggerInterceptor timeLoggerInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(timeLoggerInterceptor)
//                .addPathPatterns("/trains/**", "/users/**");
//
//    }


@Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
