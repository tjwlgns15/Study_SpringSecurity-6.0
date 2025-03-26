package com.jihun.springsecuritystudyproject.admin.repository;

import com.jihun.springsecuritystudyproject.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserManagementRepository extends JpaRepository<Account, Long> { }
