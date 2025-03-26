package com.jihun.springsecuritystudyproject.domain.user.controller.api;

import com.jihun.springsecuritystudyproject.domain.user.model.dto.UserRegistrationDto;
import com.jihun.springsecuritystudyproject.domain.user.model.dto.UserResponseDto;
import com.jihun.springsecuritystudyproject.domain.user.model.entity.User;
import com.jihun.springsecuritystudyproject.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserRestController {

    @GetMapping("/user")
    public UserResponseDto user(@AuthenticationPrincipal UserResponseDto responseDto) {
        return responseDto;
    }

    @GetMapping("/manager")
    public UserResponseDto manager(@AuthenticationPrincipal UserResponseDto responseDto) {
        return responseDto;
    }

    @GetMapping("/admin")
    public UserResponseDto admin(@AuthenticationPrincipal UserResponseDto responseDto) {
        return responseDto;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "logout";
    }








}
