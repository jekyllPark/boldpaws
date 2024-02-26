package com.dangdang.boldpaws.common.security.config;

import com.dangdang.boldpaws.common.security.component.JwtAuthenticationEntryPoint;
import com.dangdang.boldpaws.common.security.filter.JwtFilter;
import com.dangdang.boldpaws.common.security.handler.JwtAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final JwtFilter jwtFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    /**
     * 비밀번호 암호화를 위한 인코더 등록
     * */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /**
     * AuthenticationManagerBuilder 를 통해서가 아닌 직접 AuthenticationManager 를 사용하기 위함
     * */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    /**
     * 시큐리티 체이닝 설정
     * */
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                /** Cross Site Request Forgery 차단 */
                .csrf(AbstractHttpConfigurer::disable)
                /** Jwt 사용을 위해 httpBasic 차단 */
                .httpBasic(HttpBasicConfigurer::disable)
                /** 폼 로그인 차단 */
                .formLogin(formLogin -> formLogin.disable())
                /** h2 콘솔 사용 등을 위함 */
                .headers(headers ->
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )

                /** UsernamePasswordAuthenticationFilter 의 선행 처리 */
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                /** 커스텀 에러 핸들링 */
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )

                /** API 엔드포인트, static 등 각 자원에 대한 권한 설정 */
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        /** 권한이 필요하지 않은 경로 */
                        .requestMatchers(
                                "/member/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/api-docs/**",
                                "/favicon.ico",
                                "/error"
                        ).permitAll()
                        /** 권한이 필요한 경로, 추후 API 설계 후 설정 */
//                        .requestMatchers(
//                                ""
//                        ).authenticated()
                        .anyRequest().authenticated()
                )

                /** Jwt 사용을 위해 세션 상태 값 설정 */
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }
}
