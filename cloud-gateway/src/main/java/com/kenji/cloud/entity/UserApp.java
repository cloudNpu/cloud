package com.kenji.cloud.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: Cjmmy
 * @Date: 2018/12/18 7:54 PM
 */
@Entity
@Table(name = "USER_APP")
@Data
public class UserApp {
    @Id
    private Long id;

    @Column(name = "APPNAME")
    private String appName;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "USERID")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "OPERATORID")
    private User operator;


    @Column(name = "CREATEDATE")
    private Date createDate;
    private String comment;

    @Override
    public String toString() {
        return "UserApp{" +
                "id=" + id +
                ", appName='" + appName + '\'' +
                ", user=" + user +
                ", operator=" + operator +
                ", createDate=" + createDate +
                ", comment='" + comment + '\'' +
                '}';
    }
}
