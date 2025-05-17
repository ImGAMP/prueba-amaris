package com.amaris.backend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    public JwtFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @SuppressWarnings("null") 
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI(); // /amaris-back/api/auth/login
        boolean excluded = uri.contains("/api/auth");
        System.out.println(">>> JwtFilter - shouldNotFilter uri: " + uri + ", excluded: " + excluded);
        return excluded;
    }
    
    @SuppressWarnings("null") 
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        System.out.println(">>> JwtFilter - doFilterInternal for: " + request.getServletPath());

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            String username = jwtUtils.extractUsername(jwt);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (jwtUtils.validateToken(jwt)) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            new User(username, "", Collections.emptyList()),
                            null,
                            Collections.emptyList()
                    );
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    System.out.println(">>> JwtFilter - Authenticated user: " + username);
                } else {
                    System.out.println(">>> JwtFilter - Invalid token");
                }
            }
        } else {
            System.out.println(">>> JwtFilter - No valid Authorization header found");
        }

        filterChain.doFilter(request, response);
    }
}
