package com.kenji.cloud.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "APPNAME")
    private String appName;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "USERID")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "OPERATORID")
    private User operator;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "CREATEDATE")
    private Date createDate;

    @Column(name = "COMMENT")
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


    @JsonCreator
    public UserApp(@JsonProperty("id") long id,
                   @JsonProperty("appName") String appName,
                   @JsonProperty("user") User user,
                   @JsonProperty("operator") User operator,
                   @JsonProperty("createDate") Date createDate,
                   @JsonProperty("comment") String comment){
        this.id = id;
        this.appName = appName;
        this.user = user;
        this.operator = operator;
        this.createDate = createDate;
        this.comment = comment;
    }

}
