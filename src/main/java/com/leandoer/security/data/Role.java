package com.leandoer.security.data;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum Role {
    ADMIN;

    public Set<? extends GrantedAuthority> getGrantedAuthorities() {
        return new HashSet<>(Arrays.asList(
                new SimpleGrantedAuthority("ROLE_" + this.name())
        ));
    }
}
