package com.example.factory.Enum;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    /**
     * роли пользователя
     * **/
    ROLE_USER, ROLE_ADMIN;

    /**
     * метод из GrantedAuthority
     * **/
    @Override
    public String getAuthority() {
        return name();
    }

}