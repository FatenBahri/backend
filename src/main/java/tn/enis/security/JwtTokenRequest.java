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
    // 1. Déclarez le service pour charger les utilisateurs
    private final CustomUserDetailsService userDetailsService; 

    // 2. Modifiez le constructeur pour injecter le CustomUserDetailsService
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
                    // 3. Extrait le username à partir du token
                    String username = jwt.getUsernameFromToken(token); 
                    
                    // Vérifie si le contexte de sécurité n'est pas déjà rempli
                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        
                        // 4. Charge les détails de l'utilisateur
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username); 
                        
                        // 5. Crée le token d'authentification avec l'objet UserDetails et ses autorités
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails, 
                                        null, // Le mot de passe n'est pas nécessaire ici
                                        userDetails.getAuthorities());
                        
                        // 6. Définit l'objet d'authentification dans le contexte
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } catch (Exception e) {
                    // Gestion d'erreur (token valide mais utilisateur non trouvé, etc.)
                    // Pour simplifier, on laisse passer sans authentification pour que Spring Security gère le 403/401
                    System.out.println("Erreur d'authentification JWT: " + e.getMessage());
                }
            }
        }

        chain.doFilter(request, response);
    }
}