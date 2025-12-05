package tn.enis.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails; // <-- NOUVEL IMPORT
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tn.enis.service.jwtUsingCreation;
import tn.enis.security.CustomUserDetailsService; // <-- Assurez-vous que cette classe existe

@Component
public class JwtTokenRequest extends OncePerRequestFilter {

    private  jwtUsingCreation jwt;
    private final CustomUserDetailsService userDetailsService; 
    public JwtTokenRequest(jwtUsingCreation jwt, CustomUserDetailsService userDetailsService) {
        this.jwt = jwt;
        this.userDetailsService = userDetailsService; 
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
                                    throws ServletException, java.io.IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            
            if (jwt.validerToken(token)) {
                try {
                    String username = jwt.getUsernameFromToken(token); 
                    
                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username); 
                        
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails, 
                                        null, 
                                        userDetails.getAuthorities());
                        
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } catch (Exception e) {
                    System.out.println("Erreur d'authentification JWT: " + e.getMessage());
                }
            }
        }

        chain.doFilter(request, response);
    }
}