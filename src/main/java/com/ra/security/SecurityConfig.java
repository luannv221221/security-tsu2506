package com.ra.security;

import com.ra.security.jwt.JwtAuthTokenFilter;
import com.ra.security.jwt.JwtEntrypoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // bat @PreAuthorize, @PostAuthorize
public class SecurityConfig {
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private JwtAuthTokenFilter jwtAuthTokenFilter;
    @Autowired
    private JwtEntrypoint jwtEntrypoint;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth->{
                    auth.requestMatchers("/cart").authenticated();
                    auth.requestMatchers("/admin").hasAuthority("ADMIN");
                    auth.requestMatchers("/admin/sale").hasAuthority("SALE");
//                    auth.requestMatchers(HttpMethod.GET,"/api/v1/blogs").hasAnyAuthority("ADMIN","SALE","BLOGGER");
//                    auth.requestMatchers(HttpMethod.POST,"/api/v1/blogs/**").hasAnyAuthority("BLOGGER");
//                    auth.requestMatchers(HttpMethod.PUT,"/api/v1/blogs/**").hasAnyAuthority("BLOGGER");
//                    auth.requestMatchers(HttpMethod.DELETE,"/api/v1/blogs/**").hasAnyAuthority("BLOGGER");
                    auth.anyRequest().permitAll();
                }).sessionManagement(auth->auth.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception->{
                    exception.authenticationEntryPoint(jwtEntrypoint);
                }).addFilterAfter(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
}
