package dev.angel.relaciones._1_1_bidirec.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filter(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())                // sin CSRF (para POST/PUT/DELETE desde fetch)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()                  // TODO permitido
                )
                .httpBasic(basic -> basic.disable())         // sin basic auth
                .formLogin(form -> form.disable())           // sin login form
                .build();
    }
}
