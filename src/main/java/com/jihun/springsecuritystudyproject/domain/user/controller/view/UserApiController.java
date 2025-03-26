package com.jihun.springsecuritystudyproject.domain.user.controller.view;

import com.jihun.springsecuritystudyproject.domain.user.model.dto.UserResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/api")
public class UserApiController {

    @GetMapping
    public String restDashBoard() {
        return "rest/dashboard";
    }

    @GetMapping("/login")
    public String restLogin() {
        return "rest/login";
    }
}
