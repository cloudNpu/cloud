package com.kenji.cloud.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Cjmmy
 * @Date: 2018/12/6 5:20 PM
 */
@Data
public class ServiceInfoVO implements Serializable {
    private Integer code;
    private String message;
    private Object data;

    public ServiceInfoVO(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ServiceInfoVO() {
    }

    public ServiceInfoVO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
