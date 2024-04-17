package com.boldpaws.common.security.config;

import com.boldpaws.common.security.jwt.component.CustomAuthenticationEntryPoint;
import com.boldpaws.common.security.jwt.filter.JwtFilter;
import com.boldpaws.common.security.jwt.handler.CustomAccessDeniedHandler;
import com.boldpaws.common.security.oauth.handler.OAuthSuccessHandler;
import com.boldpaws.common.security.oauth.service.OAuth2UserService;
import com.boldpaws.member.domain.entity.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final JwtFilter jwtFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final OAuth2UserService oauth2UserService;
    private final OAuthSuccessHandler oauthSuccessHandler;

    /**
     * 비밀번호 암호화를 위한 인코더 등록
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationManagerBuilder 를 통해서가 아닌 직접 AuthenticationManager 를 사용하기 위함
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * 시큐리티 체이닝 설정
     */
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
                        .accessDeniedHandler(customAccessDeniedHandler)
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                )

                /** API 엔드포인트, static 등 각 자원에 대한 권한 설정 */
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        /** 메트릭 */
                        .requestMatchers("/actuator", "/actuator/**").hasRole(Role.ADMIN.name())
                        /** 정적 리소스 접근 허용 */
                        .requestMatchers("/static/**", "/favicon.ico", "/views/**").permitAll()
                        /** 권한이 필요하지 않은 경로 */
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .requestMatchers("/swagger-ui/**", "/api-docs/**", "/login", "/", "/error", "/main", "/forbidden").permitAll()
                        .requestMatchers("/api").hasRole(Role.USER.name())
                        .anyRequest().authenticated()
                )

                /** Jwt 사용을 위해 세션 상태 값 설정 */
/*                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )*/;

        /** oauth2 설정 */
        http.oauth2Login(oauth2 -> oauth2
                .loginPage("/login")
                .userInfoEndpoint(userInfoEndPoint -> userInfoEndPoint.userService(oauth2UserService))
                .successHandler(oauthSuccessHandler)
                .failureUrl("/error")
        );

        /** 로그아웃 시 로그인 페이지로 이동 */
        http.logout(logout -> logout.logoutSuccessUrl("/login"));
        return http.build();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        /** 프로메테우스 폴링 제외 */
        return web -> web.ignoring().requestMatchers("/actuator/prometheus");
    }
}
