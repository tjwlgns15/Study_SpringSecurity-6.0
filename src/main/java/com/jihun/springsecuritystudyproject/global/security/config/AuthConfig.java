package com.jihun.springsecuritystudyproject.global.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 비밀번호 인코더를 선택 ( 기본값: bcrypt )
        // 다른 인코더를 사용하려면 DelegatingPasswordEncoder 객체 사용
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


}
