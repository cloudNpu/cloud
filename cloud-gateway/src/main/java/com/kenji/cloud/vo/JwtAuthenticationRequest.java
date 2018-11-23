package com.kenji.cloud.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtAuthenticationRequest implements Serializable {
    private static final Long serialVersionUID = -8445943548965154778L;

    private String username;
    private String password;

    public JwtAuthenticationRequest() {
        super();
    }

    public JwtAuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
