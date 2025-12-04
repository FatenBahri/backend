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
        
        // 1. L'origine de votre frontend Angular
        configuration.setAllowedOrigins(List.of("http://localhost:4200")); 
        
        // 2. Méthodes acceptées (OPTIONS est nécessaire pour le preflight)
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); 
        
        // 3. En-têtes acceptés (Authorization est vital pour le JWT)
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        
        configuration.setAllowCredentials(true); 

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Applique la configuration à toutes les routes (/**)
        source.registerCorsConfiguration("/**", configuration); 
        return source;
    }

    // 1. Définit l'encodeur de mot de passe (garder ce bean)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    

    // 2. Expose l'AuthenticationManager (garder ce bean)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        // Spring utilise automatiquement le CustomUserDetailsService et le PasswordEncoder
        // grâce à votre définition de bean 'passwordEncoder()' et votre classe '@Service CustomUserDetailsService'
        return authConfig.getAuthenticationManager();
    }
    

    // 3. La chaîne de filtres de sécurité (garder ce bean)
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