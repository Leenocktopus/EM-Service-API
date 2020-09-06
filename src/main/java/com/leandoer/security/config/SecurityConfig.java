package com.leandoer.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leandoer.exception.JwtExceptionHandler;
import com.leandoer.security.data.Role;
import com.leandoer.security.filter.JwtAuthenticationFilter;
import com.leandoer.security.filter.JwtRefreshFilter;
import com.leandoer.security.filter.JwtValidationFilter;
import com.leandoer.security.service.JwtService;
import com.leandoer.security.service.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    JwtService jwtAccessService;
    JwtService jwtRefreshService;
    ObjectMapper objectMapper;
    JwtExceptionHandler jwtExceptionHandler;

    @Autowired
    public SecurityConfig(@Qualifier("JwtAccessService") JwtService jwtAccessService,
                          @Qualifier("JwtRefreshService") JwtService jwtRefreshService,
                          ObjectMapper objectMapper,
                          JwtExceptionHandler jwtExceptionHandler) {
        this.jwtAccessService = jwtAccessService;
        this.jwtRefreshService = jwtRefreshService;
        this.objectMapper = objectMapper;
        this.jwtExceptionHandler = jwtExceptionHandler;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtAccessService, jwtRefreshService, objectMapper))
                .addFilterAfter(new JwtValidationFilter(jwtAccessService, objectMapper, jwtExceptionHandler), JwtAuthenticationFilter.class)
                .addFilterAfter(jwtRefreshFilter().getFilter(), JwtValidationFilter.class)
                .authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/**").permitAll()
                .mvcMatchers(HttpMethod.POST, "/api/v1/products/*/comments", "/api/v1/orders", "/refresh").permitAll()
                .mvcMatchers(HttpMethod.PUT, "/**").hasRole(Role.ADMIN.name())
                .mvcMatchers(HttpMethod.POST, "/**").hasRole(Role.ADMIN.name())
                .mvcMatchers(HttpMethod.DELETE, "/**").hasRole(Role.ADMIN.name());
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new JpaUserDetailsService();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public FilterRegistrationBean<JwtRefreshFilter> jwtRefreshFilter() {
        FilterRegistrationBean<JwtRefreshFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtRefreshFilter(jwtAccessService, jwtRefreshService, jwtExceptionHandler, objectMapper));
        registrationBean.addUrlPatterns("/refresh");
        return registrationBean;
    }
}
