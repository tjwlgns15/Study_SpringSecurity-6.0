package com.jihun.springsecuritystudyproject.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jihun.springsecuritystudyproject.domain.user.model.dto.UserRegistrationDto;
import com.jihun.springsecuritystudyproject.global.security.token.RestAuthenticationToken;
import com.jihun.springsecuritystudyproject.global.security.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;


import java.io.IOException;

public class CustomRestAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    // 인증 필터 동작 조건 정의
    public CustomRestAuthenticationFilter() {
        super(new AntPathRequestMatcher("/api/login", "POST"));
    }

    public SecurityContextRepository getSecurityContextRepository(HttpSecurity http) {
        SecurityContextRepository securityContextRepository = http.getSharedObject(SecurityContextRepository.class);

        if (securityContextRepository == null) {
            securityContextRepository = new DelegatingSecurityContextRepository(
                    new RequestAttributeSecurityContextRepository(),
                    new HttpSessionSecurityContextRepository()
            );
        }

        return securityContextRepository;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        // POST 방식, 비동기 통신 체크
        if (!HttpMethod.POST.name().equals(request.getMethod()) || !WebUtil.isAjax(request)) {
            throw new IllegalArgumentException("Unsupported HTTP method: " + request.getMethod());
        }

        UserRegistrationDto dto = objectMapper.readValue(request.getReader(), UserRegistrationDto.class);

        // username, pw가 모두 들어 있는지 확인
        if (!StringUtils.hasText(dto.getUsername()) || !StringUtils.hasText(dto.getPassword())) {
            throw new AuthenticationServiceException("Username or Password not provided");
        }

        // 아직 인증 받지 못한 유저 객체
        RestAuthenticationToken token = new RestAuthenticationToken(dto.getUsername(), dto.getPassword());

        // AuthenticationManager에서 인증을 수행
        return getAuthenticationManager().authenticate(token);
    }
}
