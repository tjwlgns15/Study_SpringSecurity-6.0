package com.jihun.springsecuritystudyproject.global.security.core;

import com.jihun.springsecuritystudyproject.domain.user.model.dto.UserResponseDto;
import com.jihun.springsecuritystudyproject.domain.user.model.entity.User;
import com.jihun.springsecuritystudyproject.domain.user.repository.UserRepository;
import com.jihun.springsecuritystudyproject.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

// 스프링 시큐리티는 Authentication Provider에서 UserDetails를 가져다 인증을 수행하기 때문에
// 직접 커스텀하게 구현하지 않아도 된다.
@Service("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRoles()));
        UserResponseDto response = UserResponseDto.fromEntity(user);

        return new CustomUserDetails(response, authorities);
    }
}
