package com.jihun.springsecuritystudyproject.admin.repository;

import com.jihun.springsecuritystudyproject.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserManagementRepository extends JpaRepository<Account, Long> { }
