package com.example.subscribify.config;

import com.example.subscribify.service.user.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/", "/signup", "/docs/**").permitAll()
                .anyRequest().authenticated());

        http.formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .permitAll()
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password"));



//        http.logout(httpSecurityLogoutConfigurer -> {
//            httpSecurityLogoutConfigurer.addLogoutHandler((request, response, authentication) -> {
//                // TODO Session과 쿠키 삭제
//                System.out.println("SecurityConfig.filterChain.addLogoutHandler");
//            });
//        });

        http.userDetailsService(customUserDetailsService);


        return http.build();
    }


}
