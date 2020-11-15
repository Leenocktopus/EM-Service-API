package com.leandoer;

import com.leandoer.exception.CustomExceptionHandler;
import jdk.nashorn.internal.ir.annotations.Immutable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfig() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowCredentials(true);
            }
        };
    }

}
/*
* TODO
*
* Possible types of errors:
* 1. Empty/Extra columns in the request body ||Bad request
* 2. Not found for put/delete
* 3. SQL error - value is too long for column, index violation, foreign key fails
* 4. No such HTTP method
* 5. No handler found for URL
*
* 1. JWT Expired
* 2. JWT Malformed
* 3. No cookie for refresh
* 4. No user for refresh
* 5. JWT version don't match
* 6. User has not been found by username
* 7. Same password on chane
* 8. Same name on change
* */