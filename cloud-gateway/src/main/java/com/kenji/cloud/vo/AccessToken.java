package com.kenji.cloud.vo;

import lombok.Data;

@Data
public class AccessToken {
    private String access_token;
    private String token_type;
    private Long expires_in;

}
