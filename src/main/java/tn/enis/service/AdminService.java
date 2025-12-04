package tn.enis.service;

import org.springframework.security.authentication.AuthenticationManager; // NOUVEL IMPORT
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // NOUVEL IMPORT
import org.springframework.security.core.Authentication; // NOUVEL IMPORT
import org.springframework.security.core.AuthenticationException; // NOUVEL IMPORT
import org.springframework.security.crypto.password.PasswordEncoder; // L'Encoder n'est plus nécessaire ici pour le login
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import tn.enis.dao.AdminRepository;
import tn.enis.entities.Admin;

@AllArgsConstructor
@Service
public class AdminService {

    private final AdminRepository adminrep;
    private final jwtUsingCreation jwtusing;
    private final PasswordEncoder passwordEncoder; // Garder pour l'inscription

    // ⭐ NOUVELLE INJECTION : L'outil d'authentification de Spring Security
    private final AuthenticationManager authenticationManager; 

    // Vérification login
    public String verifLogin(String username, String password) {
        
        try {
            // 1. Déléger l'authentification à Spring Security
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
            );

            // 2. Si l'authentification réussit (aucune exception levée)
            // On génère le token JWT en utilisant l'username qui se trouve dans l'objet Authentication
            return jwtusing.genererToken(authentication.getName());

        } catch (AuthenticationException e) {
            // Si le mot de passe ou l'username est incorrect
            // Spring Security lève des exceptions comme BadCredentialsException.
            // Nous les interceptons et levons une exception générique (ou une custom HTTP 401)
            throw new RuntimeException("Invalid username or password", e);
        }
    }

    // Création admin (méthode existante, inchangée)
    public Admin createNewAdmin(String username, String password) {
        Admin a = Admin.builder()
                .username(username)
                .password(passwordEncoder.encode(password)) 
                .build();
        return adminrep.save(a);
    }
}