package com.jihun.springsecuritystudyproject.users.repository;

import com.jihun.springsecuritystudyproject.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);

}
