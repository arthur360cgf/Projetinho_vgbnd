package com.vgbnd.auth_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    public SecurityConfig(OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler) {
        this.oAuth2LoginSuccessHandler = oAuth2LoginSuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desativado para facilitar os testes em microsserviços
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**", "/auth/login/**").permitAll() // Rotas públicas
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .successHandler(oAuth2LoginSuccessHandler)
            )
            .headers(headers -> headers.frameOptions(frame -> frame.disable())); // Necessário para ver a consola do H2

        return http.build();
    }
}
