package com.jihun.springsecuritystudyproject.global.security.provider;

import com.jihun.springsecuritystudyproject.global.security.core.CustomUserDetails;
import com.jihun.springsecuritystudyproject.global.security.details.CustomWebAuthenticationDetails;
import com.jihun.springsecuritystudyproject.global.security.exception.SecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("authenticationProvider")
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        CustomUserDetails customUserDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);

        if (!passwordEncoder.matches(password, customUserDetails.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }

        // Authentication 객체 내부의 details 즉 WebAuthenticationDetails 객체 내부에서 파라미터의 값을 가져와, 비밀 문자열과 비교
        String secretKey = ((CustomWebAuthenticationDetails) authentication.getDetails()).getSecretKey();
        if (secretKey == null || !secretKey.equals("secret")) {
            throw new SecurityException("Invalid Secret");
        }

        return new UsernamePasswordAuthenticationToken(
                customUserDetails.getResponseDto(),
                null,
                customUserDetails.getAuthorities()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
