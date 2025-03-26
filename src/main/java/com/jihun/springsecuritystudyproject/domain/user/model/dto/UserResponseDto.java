package com.jihun.springsecuritystudyproject.domain.user.model.dto;

import com.jihun.springsecuritystudyproject.domain.user.model.entity.User;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String username;
    private String password;
    private int age;
    private String roles;

    public static UserResponseDto fromEntity(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .age(user.getAge())
                .roles(user.getRoles())
                .build();
    }

}
