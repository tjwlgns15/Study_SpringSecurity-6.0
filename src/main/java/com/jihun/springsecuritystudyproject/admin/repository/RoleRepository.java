package com.jihun.springsecuritystudyproject.admin.repository;

import com.jihun.springsecuritystudyproject.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String name);

    @Override
    void delete(Role role);

    @Query("select r from Role r where r.isExpression = 'N'")
    List<Role> findAllRolesWithoutExpression();
}