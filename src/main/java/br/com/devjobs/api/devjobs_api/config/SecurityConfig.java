package br.com.devjobs.api.devjobs_api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(withDefaults()) // 👈 ENABLE CORS CONFIGURATION HERE
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // --- Public Routes ---
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/jobs/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/companies/").permitAll() // Assuming you want this public too

                        // --- Recruiter Routes ---
                        .requestMatchers(HttpMethod.POST, "/jobs/").hasRole("RECRUITER")
                        .requestMatchers(HttpMethod.POST, "/companies/").hasRole("RECRUITER")
                        .requestMatchers(HttpMethod.GET, "/jobs/*/applications").hasRole("RECRUITER") // List candidates for a job

                        // --- Candidate Route ---
                        .requestMatchers(HttpMethod.POST, "/jobs/*/apply").hasRole("CANDIDATE") // Apply for a job

                        // --- All Other Routes ---
                        .anyRequest().authenticated() // Require authentication for anything else
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // --- Password Encoder Bean ---
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}