package tn.enis.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider; // Peut être retiré si vous supprimez le bean
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.Customizer; // NOUVEAU
import org.springframework.web.cors.CorsConfiguration; // NOUVEAU
import org.springframework.web.cors.CorsConfigurationSource; // NOUVEAU
import org.springframework.web.cors.UrlBasedCorsConfigurationSource; // NOUVEAU
import java.util.Arrays; // NOUVEAU
import java.util.List;   // NOUVEAU
@Configuration
@EnableWebSecurity
public class SecurityConfigura {

    private final JwtTokenRequest jwtReq;
    private final CustomUserDetailsService userDetailsService; 

    public SecurityConfigura(JwtTokenRequest jwtReq, CustomUserDetailsService userDetailsService) {
        this.jwtReq = jwtReq;
        this.userDetailsService = userDetailsService;
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        configuration.setAllowedOrigins(List.of("http://localhost:4200")); 
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); 
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true); 

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); 
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
        http.cors(Customizer.withDefaults()).csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/log/login", "/log/inscription").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtReq, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}