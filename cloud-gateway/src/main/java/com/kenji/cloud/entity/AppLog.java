package com.kenji.cloud.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "APPLOG")
@Data
public class AppLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "INSTANCEINFOID")
    private InstanceInfo instanceInfo;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "USERID")
    private User user;

    @Column(name = "APPNAME")
    private String appName;

    private String operation;

    private String comment;

    @Column(name = "CREATEDATE")
    private Date createdate;
}
