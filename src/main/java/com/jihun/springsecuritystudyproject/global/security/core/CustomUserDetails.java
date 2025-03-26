package com.jihun.springsecuritystudyproject.global.security.core;


import com.jihun.springsecuritystudyproject.domain.user.model.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final UserResponseDto responseDto;
    private final List<GrantedAuthority> authorities;


    @Override
    public String getUsername() {
        return responseDto.getUsername();
    }

    @Override
    public String getPassword() {
        return responseDto.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
