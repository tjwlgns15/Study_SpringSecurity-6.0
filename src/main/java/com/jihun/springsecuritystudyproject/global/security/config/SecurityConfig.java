package com.jihun.springsecuritystudyproject.global.security.config;

import com.jihun.springsecuritystudyproject.global.security.dsl.CustomRestApiDsl;
import com.jihun.springsecuritystudyproject.global.security.entrypoint.CustomRestAuthenticationEntryPoint;
import com.jihun.springsecuritystudyproject.global.security.filter.CustomRestAuthenticationFilter;
import com.jihun.springsecuritystudyproject.global.security.handler.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

//    private final UserDetailsService userDetailsService;  // CustomUserDetailsService
    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationProvider customRestAuthenticationProvider;
    private final AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;  // CustomWebAuthenticationDetailsSource
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final CustomRestAuthenticationSuccessHandler customRestAuthenticationSuccessHandler;
    private final CustomRestAuthenticationFailureHandler customRestAuthenticationFailureHandler;
    private final CustomRestLogoutHandler customRestLogoutHandler;




    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/images/**", "/js/**", "/favicon.*", "*/icon-*").permitAll() // 정적자원 허용
                        .requestMatchers("/", "/signup", "/login*").permitAll()
                        .requestMatchers("/user").hasAuthority("ROLE_USER")
                        .requestMatchers("/manager").hasAuthority("ROLE_MANAGER")
                        .requestMatchers("/admin").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .authenticationDetailsSource(authenticationDetailsSource)
                        .successHandler(customAuthenticationSuccessHandler)
                        .failureHandler(customAuthenticationFailureHandler)
                        .permitAll()
                )

                // 인증
//                .userDetailsService(userDetailsService)          // UserDetails 사용한 인증 방식
                .authenticationProvider(authenticationProvider)  // AuthenticationProvider 사용한 인증 방식

                // 접근 거부
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(new CustomAccessDeniedHandler("/denied"))
                )

                // 로그아웃
                .logout(logout -> logout
                        .logoutUrl("/logout")                           // 로그아웃 요청을 처리할 URL
                        .logoutSuccessUrl("/")                          // 로그아웃 성공 후 리다이렉트할 URL
                        .deleteCookies("JSESSIONID") // 로그아웃 시 삭제할 쿠키
                        .clearAuthentication(true)                      // 인증 정보 제거
                        .invalidateHttpSession(true)                    // HTTP 세션 무효화
                        .permitAll()
                )

        ;
        return http.build();
    }

    @Bean
    @Order(1) // rest 방식을 먼저 처리하도록 순서 지정
    public SecurityFilterChain restSecurityFilterChain(HttpSecurity http) throws Exception {

        // AuthenticationManager
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(customRestAuthenticationProvider);
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http
                .securityMatcher("/api/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/images/**", "/js/**", "/favicon.*", "/*/icon-*").permitAll()
                        .requestMatchers("/api","/api/login").permitAll()
                        .requestMatchers("/api/user").hasAuthority("ROLE_USER")
                        .requestMatchers("/api/manager").hasAuthority("ROLE_MANAGER")
                        .requestMatchers("/api/admin").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated())

                // Rest 요청은 thymleaf가 아닌 js로 전달하기 때문에 csrf토큰을 직접 전달해야 한다.
//                 .csrf(AbstractHttpConfigurer::disable)

                // DSL 설정 이후 사용x
//                .addFilterBefore(
//                        restAuthenticationFilter(http, authenticationManager),
//                        UsernamePasswordAuthenticationFilter.class
//                )

                .authenticationManager(authenticationManager)

                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new CustomRestAuthenticationEntryPoint())  // 경로 이동은 dsl에서 지정하고 응답만 설정 
                        .accessDeniedHandler(new CustomRestAccessDeniedHandler())
                )

                // REST API 로그아웃 설정 추가
                .logout(logout -> logout
                        .logoutUrl("/api/logout")
                        .logoutSuccessHandler(customRestLogoutHandler)
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .permitAll()
                )

                .with(new CustomRestApiDsl <>(), restDsl -> restDsl
                        .setRestSuccessHandler(customRestAuthenticationSuccessHandler)
                        .setRestFailureHandler(customRestAuthenticationFailureHandler)
                        .loginProcessingUrl("/api/login") // 로그인 요청 경로
                        .loginPage("/api/login")  // 인증 필요시 이동 url
                )
        ;
        return http.build();
    }

    // DSL 이후 사용 x
//    private CustomRestAuthenticationFilter restAuthenticationFilter(HttpSecurity http, AuthenticationManager authenticationManager) {
//        CustomRestAuthenticationFilter customRestAuthenticationFilter = new CustomRestAuthenticationFilter(http);
//        customRestAuthenticationFilter.setAuthenticationManager(authenticationManager);
//
//        // 필터에서 핸들러 호출
//        customRestAuthenticationFilter.setAuthenticationSuccessHandler(customRestAuthenticationSuccessHandler);
//        customRestAuthenticationFilter.setAuthenticationFailureHandler(customRestAuthenticationFailureHandler);
//
//        return customRestAuthenticationFilter;
//    }
}
