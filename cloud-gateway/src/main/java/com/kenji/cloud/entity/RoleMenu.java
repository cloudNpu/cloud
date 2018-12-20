package com.kenji.cloud.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: Cjmmy
 * @Date: 2018/12/18 2:55 PM
 */
@Entity
@Table(name = "ROLE_MENU")
@Data
public class RoleMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLEID")
    private Role role;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "MENUID")
    private Menu menu;

    @Column(name = "CREATEDATE")
    private Date createdate;


    @Override
    public String toString() {
        return "RoleMenu{" +
                "id=" + id +
                ", createdate=" + createdate +
                '}';
    }
}
