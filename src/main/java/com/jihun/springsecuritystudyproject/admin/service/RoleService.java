package com.jihun.springsecuritystudyproject.admin.service;


import com.jihun.springsecuritystudyproject.domain.entity.Role;

import java.util.List;

public interface RoleService {
    Role getRole(long id);
    List<Role> getRoles();
    List<Role> getRolesWithoutExpression();

    void createRole(Role role);

    void deleteRole(long id);
}
