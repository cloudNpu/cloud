package com.kenji.cloud.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Fly
 * @Description:
 * @Date: 2019/1/9 22:56
 * @Modified By:
 */
@Data
public class RoleReturnVo {
    private Long id;
    private String name;
    private String value;
    private String description;
    private Date createDate;
}
