/*
 * Author: Jahiem Allen
 */

package com.robotdelivery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> {
            try {
                csrf.disable().authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return http.build();
    } // allow all requests from any domain to be allowed for TESTINg
}