package com.jihun.springsecuritystudyproject.admin.repository;

import com.jihun.springsecuritystudyproject.domain.entity.RoleHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleHierarchyRepository extends JpaRepository<RoleHierarchy, Long> {
}
