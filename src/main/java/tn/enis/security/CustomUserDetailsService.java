package tn.enis.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import tn.enis.dao.AdminRepository;
import tn.enis.entities.Admin;

@Service
@AllArgsConstructor 
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username);

        if (admin == null) {
            throw new UsernameNotFoundException("Admin non trouvé avec l'username: " + username);
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(admin.getUsername())
                .password(admin.getPassword()) 
                .roles("ADMIN") 
                .build();
    }
}