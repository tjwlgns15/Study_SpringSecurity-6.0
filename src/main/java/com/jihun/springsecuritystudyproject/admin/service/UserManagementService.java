package com.jihun.springsecuritystudyproject.admin.service;


import com.jihun.springsecuritystudyproject.domain.dto.AccountDto;
import com.jihun.springsecuritystudyproject.domain.entity.Account;

import java.util.List;

public interface UserManagementService {

    void modifyUser(AccountDto accountDto);

    List<Account> getUsers();
    AccountDto getUser(Long id);

    void deleteUser(Long idx);

}
