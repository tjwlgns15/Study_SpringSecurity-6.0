package com.jihun.springsecuritystudyproject.domain.user.controller.view;

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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception,
                        Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "login/login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "login/signup";
    }

    @PostMapping("/signup")
    public String signup(UserRegistrationDto userRegistrationDto) {

        User user = User.fromRegistrationDto(
                userRegistrationDto,
                passwordEncoder.encode(userRegistrationDto.getPassword())
        );

        userService.createUser(user);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/login";
    }

    @GetMapping("/denied")
    public String accessDenied(@RequestParam(value = "exception",required = false) String exception,
                               @AuthenticationPrincipal UserResponseDto user,
                               Model model) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("exception", exception);

        return "login/denied";

    }
}
