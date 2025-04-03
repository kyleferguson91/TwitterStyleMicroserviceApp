package org.ac.cst8277.ferguson.kyle.usermanagementservice.config;

import org.ac.cst8277.ferguson.kyle.usermanagementservice.security.OAuth2AuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final OAuth2AuthenticationSuccessHandler successHandler;

    public SecurityConfig(OAuth2AuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http

        .authorizeHttpRequests(authorize -> authorize
    .requestMatchers("/api/users/token/**").permitAll()

    .requestMatchers("/api/users/login").permitAll()
    .anyRequest().authenticated()
)
        .oauth2Login(oauth2 -> oauth2
            .successHandler(successHandler)
        );
    return http.build();
}

}
