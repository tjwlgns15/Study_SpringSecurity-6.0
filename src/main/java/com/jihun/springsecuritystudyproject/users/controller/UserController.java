package com.jihun.springsecuritystudyproject.users.controller;

import com.jihun.springsecuritystudyproject.domain.dto.AccountDto;
import com.jihun.springsecuritystudyproject.domain.entity.Account;
import com.jihun.springsecuritystudyproject.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	@PostMapping(value="/signup")
	public String signup(AccountDto accountDto) {

		ModelMapper mapper = new ModelMapper();
		Account account = mapper.map(accountDto, Account.class);
		account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
		userService.createUser(account);

		return "redirect:/";
	}
}
