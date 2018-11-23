package com.kenji.cloud.vo;

import java.io.Serializable;

public class JwtAuthenticationResponse implements Serializable {
    private static final Long serialVersionUID = 1250166508152483573L;

    private final String token;

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
