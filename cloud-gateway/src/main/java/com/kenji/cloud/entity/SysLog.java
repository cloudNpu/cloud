package com.kenji.cloud.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "SYSLOG")
@Data
public class SysLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "USERID")
    private User user;

    private String operation;

    private String comment;

    @Column(name = "CREATEDATE")
    private Date createDate;


    @Override
    public String toString() {
        return "SysLog{" +
                "id=" + id +
                ", operation='" + operation + '\'' +
                ", comment='" + comment + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
