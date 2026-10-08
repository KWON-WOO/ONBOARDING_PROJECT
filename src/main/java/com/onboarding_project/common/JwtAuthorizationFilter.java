package com.onboarding_project.common;

import com.onboarding_project.common.exception.CustomException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthorizationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(JwtUtil.AUTHORIZATION_HEADER);

        if (StringUtils.hasText(header) && header.startsWith(JwtUtil.BEARER_PREFIX)) {
            String token = header.substring(JwtUtil.BEARER_PREFIX.length());

            if (jwtUtil.validateToken(token)) {
                try {
                    Claims claims = jwtUtil.getUserInfoFromToken(token);
                    UserRole role = jwtUtil.getUserRole(claims);
                    setAuthentication(claims.getSubject(), role);
                } catch (CustomException ignored) {
                    // 인증정보 없이 진행 시 EntryPoint에서 401 처리.
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String username, UserRole role) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                username, null, List.of(new SimpleGrantedAuthority(role.getAuthority())));

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }
}
