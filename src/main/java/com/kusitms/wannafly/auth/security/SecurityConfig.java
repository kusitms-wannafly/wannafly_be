package com.kusitms.wannafly.auth.security;

import com.kusitms.wannafly.auth.security.authentication.OAuthLoginFailureHandler;
import com.kusitms.wannafly.auth.security.authentication.OAuthLoginSuccessHandler;
import com.kusitms.wannafly.auth.security.authentication.PrincipalOAuth2UserService;
import com.kusitms.wannafly.auth.security.authoriization.JwtAuthorizationFilter;
import com.kusitms.wannafly.auth.security.authoriization.UnAuthorizationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PrincipalOAuth2UserService userService;
    private final OAuthLoginSuccessHandler successHandler;
    private final OAuthLoginFailureHandler failureHandler;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final UnAuthorizationEntryPoint unauthorizationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors().and()
                .csrf().disable()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .formLogin().disable()
                .httpBasic().disable()

                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
                .and()

                .oauth2Login()
                .userInfoEndpoint().userService(userService).and()
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .and()

                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling()
                .authenticationEntryPoint(unauthorizationEntryPoint)
                .and()

                .build();
    }
}
