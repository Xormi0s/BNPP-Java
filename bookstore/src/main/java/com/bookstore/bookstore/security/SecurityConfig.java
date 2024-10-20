package com.bookstore.bookstore.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

// Tried to implement basic security as best as I could in this case
// Since I never used this before. So this is based on found examples and youtube tutorials

@Configuration
public class SecurityConfig {

    // Configure the security filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection (common for REST APIs)
                .cors(withDefaults()) // Enable and configure CORS protection
                .authorizeRequests(authz -> authz
                        // Specify publicly accessible routes
                        .requestMatchers("/api/v1/users/login", "/api/v1/users/register", "/api/v1/books", "/api/v1/books/*").permitAll()
                        .anyRequest().authenticated() // Require authentication for all other routes
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)) // Allow session management (for stateful authentication)
                .httpBasic(withDefaults()); // Enable basic authentication

        return http.build();
    }

    // Add an in-memory user -> This user created to have basic authentication without retrieving users and roles from MongoDB
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("bookstore")
                .password(passwordEncoder().encode("test123")) // Encode the password with BCrypt
                .roles("USER") // Assign the "USER" role to this user
                .build();

        return new InMemoryUserDetailsManager(user); // Return an in-memory user manager
    }

    // Password encoder for secure password handling
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCrypt for password encoding
    }
}
