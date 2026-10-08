package com.onboarding_project.common;

import com.onboarding_project.common.exception.CustomException;
import com.onboarding_project.common.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final HandlerExceptionResolver handlerExceptionResolver;
//    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(JwtUtil jwtUtil,
                          @Qualifier("handlerExceptionResolver") HandlerExceptionResolver handlerExceptionResolver) {
        this.jwtUtil = jwtUtil;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                // 세션 미사용 적용
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/signup", "/signin").permitAll()
                        .requestMatchers(HttpMethod.GET, "/menu", "/menu/{menuId}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/menu").hasRole("OWNER")
                        .requestMatchers(HttpMethod.PUT, "/menu/{menuId}").hasRole("OWNER")
                        .requestMatchers(HttpMethod.DELETE, "/menu/{menuId}").hasRole("OWNER")
                        .requestMatchers(HttpMethod.POST, "/order").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.POST, "/order").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.PATCH, "/order/{orderId}/cancel").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.PATCH, "/order/{orderId}/status").hasRole("OWNER")
                        .requestMatchers(HttpMethod.PATCH, "/order/{orderId}/cancel").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.PATCH, "/order/{orderId}/status").hasRole("OWNER")
                        .requestMatchers(HttpMethod.POST, "/order/{orderId}/payment").hasRole("CUSTOMER")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, e) ->
                                handlerExceptionResolver.resolveException(request, response, null,
                                        new CustomException(ErrorCode.UN_AUTHENTICATION)))
                        .accessDeniedHandler((request, response, e) ->
                                handlerExceptionResolver.resolveException(request, response, null, new CustomException(ErrorCode.FORBIDDEN)))
                )
                .addFilterBefore(new JwtAuthorizationFilter(jwtUtil),
                        UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
}
