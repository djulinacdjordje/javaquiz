package com.example.project.constant;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN,
    CONTESTANT;

    @Override
    public String getAuthority() {
        return name();
    }
}
