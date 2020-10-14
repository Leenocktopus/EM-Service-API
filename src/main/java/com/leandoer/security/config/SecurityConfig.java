package com.leandoer.security.config;

import com.leandoer.security.data.Role;
import com.leandoer.security.service.impl.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	OncePerRequestFilter jwtValidationFilter;

	@Autowired
	public SecurityConfig(@Qualifier("jwtValidationFilter") OncePerRequestFilter jwtValidationFilter) {
		this.jwtValidationFilter = jwtValidationFilter;
	}

    @Override
    protected void configure(HttpSecurity http) throws Exception {
	    http
			    .csrf().disable()
			    .cors()
			    .and()
			    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			    .and()
			    .addFilterAfter(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class)
			    .authorizeRequests()
			    .antMatchers(HttpMethod.GET, "/users/{username}").access("@userSecurity.hasUsername(authentication,#username)")
			    .antMatchers(HttpMethod.PUT, "/users/{username}").access("@userSecurity.hasUsername(authentication,#username)")
			    .mvcMatchers(HttpMethod.GET, "/**").permitAll()
			    .mvcMatchers(HttpMethod.POST, "/api/v1/products/*/comments", "/api/v1/orders", "/refresh", "/login").permitAll()
			    .mvcMatchers(HttpMethod.PUT, "/**").hasRole(Role.ADMIN.name())
			    .mvcMatchers(HttpMethod.POST, "/**").hasRole(Role.ADMIN.name())
			    .mvcMatchers(HttpMethod.DELETE, "/**").hasRole(Role.ADMIN.name())
			    .and()
			    .logout(AbstractHttpConfigurer::disable);
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new JpaUserDetailsService();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
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
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
