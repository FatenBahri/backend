package tn.enis.service;

import org.springframework.security.authentication.AuthenticationManager; 
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; 
import org.springframework.security.core.Authentication; 
import org.springframework.security.core.AuthenticationException; 
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import tn.enis.dao.AdminRepository;
import tn.enis.entities.Admin;

@AllArgsConstructor
@Service
public class AdminService {

    private final AdminRepository adminrep;
    private final jwtUsingCreation jwtusing;
    private final PasswordEncoder passwordEncoder; 

    private final AuthenticationManager authenticationManager; 

    public String verifLogin(String username, String password) {
        
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
            );

            return jwtusing.genererToken(authentication.getName());

        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username or password", e);
        }
    }
    public Admin createNewAdmin(String username, String password) {
        Admin a = Admin.builder()
                .username(username)
                .password(passwordEncoder.encode(password)) 
                .build();
        return adminrep.save(a);
    }
}