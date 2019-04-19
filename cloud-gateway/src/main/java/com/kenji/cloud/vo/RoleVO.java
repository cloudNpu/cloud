package com.kenji.cloud.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author: Cjmmy
 * @Date: 2019/1/10 9:51 AM
 */
@Data
public class RoleVO {
    private Long id;
    private String name;
    private String value;
    private String description;
    private List<String> roleMenu;
}
