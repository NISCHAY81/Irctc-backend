package com.substring.irctc.config;

import com.substring.irctc.interceptors.MyCustomInterceptors;
import com.substring.irctc.interceptors.TimeLoggerInterceptor;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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

@Bean    // for frontend configuration
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("http://localhost:3000", "http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        .allowCredentials(true); //allow cookies if needed

            }
        };
    }


@Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public OpenAPI openAPI () {
     return new OpenAPI()
             .info(
                     new Info()
                             .title("IRCTC API")
                     .version("1.0")
                     .description("This is the IRCTC API")
                             .termsOfService("https://www.irctc.com/terms-of-service")
                             .contact(new io.swagger.v3.oas.models.info.Contact()
                                     .name("IRCTC API")
                                     .url("https://www.irctc.com")
                                     .email("IRCTCAPI@gmail.com")
                             ));
    }
}
