package com.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.backend.user.security.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

        private final CustomUserDetailsService customUserDetailsService;

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                http
                                .csrf(csrf -> csrf.disable())

                                .authorizeHttpRequests(auth -> auth
                                                // 公開 API（不用登入）
                                                .requestMatchers("/api/users/register").permitAll()
                                                .requestMatchers("/api/users/login").permitAll()

                                                // 後台 API
                                                .requestMatchers("/api/admin/**").hasRole("ADMIN")

                                                // 其餘 API 需要登入
                                                .requestMatchers("/api/**").authenticated()

                                                // 其他
                                                .anyRequest().permitAll())

                                // MVP 階段使用 Basic Auth
                                .httpBasic(Customizer.withDefaults())

                                // ✅ 指定 UserDetailsService
                                .userDetailsService(customUserDetailsService);

                return http.build();
        }

}
