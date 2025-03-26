package com.jihun.springsecuritystudyproject.domain.user.model.entity;

import com.jihun.springsecuritystudyproject.domain.user.model.dto.UserRegistrationDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private int age;

    private String roles;

    public static User fromRegistrationDto(UserRegistrationDto dto, String encodedPassword) {
        return User.builder()
                .username(dto.getUsername())
                .password(encodedPassword)
                .age(dto.getAge())
                .roles(dto.getRoles())
                .build();
    }
}
