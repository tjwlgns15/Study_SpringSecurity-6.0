package com.jihun.springsecuritystudyproject.domain.user.service;

import com.jihun.springsecuritystudyproject.domain.user.model.entity.User;
import com.jihun.springsecuritystudyproject.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void createUser(User user) {
        userRepository.save(user);
    }
}
