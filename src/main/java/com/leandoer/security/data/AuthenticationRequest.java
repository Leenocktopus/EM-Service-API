package com.leandoer.security.data;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String username;
    private String password;
}
