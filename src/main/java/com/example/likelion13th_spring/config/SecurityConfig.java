package com.example.likelion13th_spring.config;

import com.example.likelion13th_spring.jwt.JwtAuthenticationFilter;
import com.example.likelion13th_spring.service.CustomOAuth2UserService;
import com.example.likelion13th_spring.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService; //UserDetailsService DI. 주입 (일반 로그인)
    private final CustomOAuth2UserService customOAuth2UserService; //추가
    private final JwtAuthenticationFilter jwtFilter; //w27

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors((SecurityConfig::corsAllow))
                .csrf(AbstractHttpConfigurer::disable) // 비활성화 -> csrf 공격이 들어올 가능성이 없음.
                .sessionManagement((manager) -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //세션 로그인 안함
                .httpBasic(AbstractHttpConfigurer::disable) // http basic auth 기반 로그인 인증창 뜨지 않게
                .formLogin(AbstractHttpConfigurer::disable) // 기본 로그인 페이지 없애기
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/join", "/login").permitAll()
                       .requestMatchers("/**").authenticated()) // 인증된 사용자만 허용
//                .oauth2Login(oauth -> oauth
//                        .userInfoEndpoint(userInfo -> userInfo
//                                .userService(customOAuth2UserService)
//                        )
//                )
//                .formLogin(Customizer.withDefaults()) // 자체적으로 usename 이 아닌 name 으로 추가할 수 있도록 수정해줘야 제대로 잘 작동할 것.
//                .logout(Customizer.withDefaults())
                .userDetailsService(customUserDetailsService);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    private static void corsAllow(CorsConfigurer<HttpSecurity> corsConfigurer) {
        corsConfigurer.configurationSource(request -> {
            CorsConfiguration configuration = new CorsConfiguration();

            configuration.setAllowedMethods(Collections.singletonList("*")); // 모든 메서드 허용
            configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // 프론트에서 오는 요청 허용
            configuration.setAllowedHeaders(Collections.singletonList("*")); // 모든 헤더 허용
            configuration.setAllowCredentials(true);
            configuration.setMaxAge(3600L); // 1시간(3600초) 동안 오는 요청이 처리됨

            return configuration;
        });
    }
}