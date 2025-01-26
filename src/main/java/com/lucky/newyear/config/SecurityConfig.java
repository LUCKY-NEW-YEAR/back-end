package com.lucky.newyear.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowedOriginPatterns(Collections.singletonList("*"));
            config.setAllowCredentials(true);
            return config;
        };
    }

    @Bean
    public SecurityFilterChain applicationSecurity(HttpSecurity http) throws Exception {

        http
                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize // 요청에 대한 권한 설정 메서드
                        .requestMatchers("/", "/test/**").permitAll() // / 경로 요청에 대한 권한을 설정. permitAll() 모든 사용자, 인증되지않은 사용자에게 허용
                        .requestMatchers("/swagger-ui/**", "/api-docs/**", "/swagger-resources/**").permitAll() //swagger 관련 경로 요청 모든 사용자에게 허용
                        .requestMatchers("/oauth/**", "/user/**", "/auth/**","/study/**", "/userSearch/**").permitAll()
                        .anyRequest().authenticated()// 다른 나머지 모든 요청에 대한 권한 설정, authenticated()는 인증된 사용자에게만 허용, 로그인해야만 접근 가능
                );

        return http.build();
    }
}