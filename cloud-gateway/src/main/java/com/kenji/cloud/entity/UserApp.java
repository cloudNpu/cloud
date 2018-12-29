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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getOperator() {
        return operator;
    }

    public void setOperator(User operator) {
        this.operator = operator;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

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
