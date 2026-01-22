package com.drzhao.portal.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 1. 关闭 CSRF
            .csrf(AbstractHttpConfigurer::disable)
            
            // 2. 权限控制：直接全部放行 (Debug模式)
            // 先确保能连通，连通后再把 permitAll() 改回 authenticated()
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() 
            )
            
            // 3. 允许 H2 控制台
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))
            
            .authenticationProvider(authenticationProvider());
            
        return http.build();
    }

    /**
     * [终极方案] 显式注册 CorsFilter Bean
     * 这种方式比在 SecurityFilterChain 里配置 .cors() 更直接、优先级更高
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        // 允许携带 Token
        config.setAllowCredentials(true);
        // 允许所有域名 (解决 403 的核心)
        config.addAllowedOriginPattern("*");
        // 允许所有头信息
        config.addAllowedHeader("*");
        // 允许所有请求方法 (GET, POST, OPTIONS 等)
        config.addAllowedMethod("*");
        
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}