// src/main/java/com/erpca/erpcaapp/config/WebConfig.java
package com.erpca.erpcaapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;

    @Autowired
    public WebConfig(LoginInterceptor loginInterceptor) {
        this.loginInterceptor = loginInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
          .addInterceptor(loginInterceptor)
          .addPathPatterns("/**")          // intercept all requests
          .excludePathPatterns(
              "/login", 
              "/logout", 
              "/css/**", 
              "/js/**", 
              "/images/**",
              "/error"                     // allow Spring Boot’s error page
          );
    }
}
