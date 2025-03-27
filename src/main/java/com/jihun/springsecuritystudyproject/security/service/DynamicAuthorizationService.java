package com.jihun.springsecuritystudyproject.security.service;

import com.jihun.springsecuritystudyproject.security.mapper.UrlRoleMapper;

import java.util.Map;
import java.util.Set;

public class DynamicAuthorizationService {
    private final UrlRoleMapper delegate;

    public DynamicAuthorizationService(UrlRoleMapper delegate) {
        this.delegate = delegate;
    }

    public Map<String, String> getUrlRoleMappings() {
        return delegate.getUrlRoleMappings();
    }
}
