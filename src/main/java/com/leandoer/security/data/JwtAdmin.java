package com.leandoer.security.data;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


@Data
public class JwtAdmin implements UserDetails {
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;


    public static JwtAdmin fromAdmin(Admin admin) {
        JwtAdmin jwtAdmin = new JwtAdmin();
        jwtAdmin.setUsername(admin.getUsername());
        jwtAdmin.setPassword(admin.getPassword());
        jwtAdmin.setAuthorities(admin.getRole().getGrantedAuthorities());
        jwtAdmin.setAccountNonExpired(true);
        jwtAdmin.setAccountNonLocked(true);
        jwtAdmin.setCredentialsNonExpired(true);
        jwtAdmin.setEnabled(true);
        return jwtAdmin;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
