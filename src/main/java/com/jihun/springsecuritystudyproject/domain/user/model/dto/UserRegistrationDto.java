package com.jihun.springsecuritystudyproject.domain.user.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {
    private String username;
    private String password;
    private int age;
    private String roles;
}
