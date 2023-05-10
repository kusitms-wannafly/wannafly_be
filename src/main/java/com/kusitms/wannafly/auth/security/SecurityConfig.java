package com.kusitms.wannafly.auth.security;

import com.kusitms.wannafly.auth.security.authentication.OAuthLoginFailureHandler;
import com.kusitms.wannafly.auth.security.authentication.OAuthLoginSuccessHandler;
import com.kusitms.wannafly.auth.security.authentication.PrincipalOAuth2UserService;
import com.kusitms.wannafly.auth.security.authoriization.JwtAuthorizationFilter;
import com.kusitms.wannafly.auth.security.authoriization.UnAuthorizationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PrincipalOAuth2UserService userService;
    private final OAuthLoginSuccessHandler successHandler;
    private final OAuthLoginFailureHandler failureHandler;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final UnAuthorizationEntryPoint unauthorizationEntryPoint;

    @Value("${cors.allowed.origins}")
    private String corsAllowedOrigins;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors()
                .configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .formLogin().disable()
                .httpBasic().disable()

                .authorizeHttpRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
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

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOriginPatterns(List.of(corsAllowedOrigins.split(",")));
        corsConfig.setAllowedMethods(List.of("HEAD","POST","GET","DELETE","PUT", "OPTIONS"));
        corsConfig.setAllowedHeaders(List.of("*"));
        corsConfig.setAllowCredentials(true);
        corsConfig.setExposedHeaders(List.of(HttpHeaders.LOCATION, HttpHeaders.SET_COOKIE));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }
}
