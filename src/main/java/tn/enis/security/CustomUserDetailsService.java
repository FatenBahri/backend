package tn.enis.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import tn.enis.dao.AdminRepository;
import tn.enis.entities.Admin;

// Annotation pour indiquer qu'il s'agit d'un service et pour l'injection des dépendances
@Service
@AllArgsConstructor 
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    /**
     * Cette méthode est appelée par Spring Security pour charger l'utilisateur 
     * lors de l'authentification (via DaoAuthenticationProvider) 
     * et lors de la vérification du JWT (via JwtTokenRequest).
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Recherche l'Admin dans la base de données
        Admin admin = adminRepository.findByUsername(username);

        if (admin == null) {
            throw new UsernameNotFoundException("Admin non trouvé avec l'username: " + username);
        }

        // Mappe l'objet Admin (votre entité) vers un objet UserDetails de Spring Security.
        // Ici, nous supposons que tous les admins ont le rôle "ADMIN".
        // La liste des autorités (rôles) est vitale pour les contrôles d'autorisation.
        return org.springframework.security.core.userdetails.User.builder()
                .username(admin.getUsername())
                .password(admin.getPassword()) // Le mot de passe crypté
                // Pour cet exemple, nous ajoutons un rôle par défaut "ADMIN"
                .roles("ADMIN") 
                .build();
    }
}