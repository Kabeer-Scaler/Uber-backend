package com.Kabeer.Uber.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.Kabeer.Uber.service.CustomUserDetailsService;
import com.Kabeer.Uber.util.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String token;
        final String username;

        // 1. Check if Header is present and formatted correctly
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("⚠️ No valid Auth Header found for: " + request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Extract Token
        token = authHeader.substring(7);

        try {
            // 3. Extract Username FIRST
            username = jwtService.getUsername(token); // Ensure your JwtService has this method

            // 4. If username exists and we aren't already authenticated
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                // 5. Validate Token against the loaded UserDetails
                // Note: Ensure your jwtService.isTokenValid accepts (token, userDetails)
                if (jwtService.isTokenValid(token)) {

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // CRITICAL: This is what logs the user in
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("✅ User Authenticated: " + username);
                } else {
                    System.out.println("❌ Token Validation Failed for: " + username);
                }
            }
        } catch (Exception e) {
            // This catches ExpiredJwtException or SignatureException
            System.out.println("❌ JWT Error: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}


