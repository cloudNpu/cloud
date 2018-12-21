package com.kenji.cloud.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Cjmmy
 * @Date: 2018/12/18 2:14 PM
 */
@Entity
@Data
public class Dept {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "DEPTNAME")
    private String deptName;
    private String description;
    //操作时间
    @Column(name = "OPERDATE")
    private Date operDate;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "dept")
    private List<User> users = new ArrayList<>();

    @Override
    public String toString() {
        return "Dept{" +
                "id=" + id +
                ", deptName='" + deptName + '\'' +
                ", description='" + description + '\'' +
                ", operDate=" + operDate +
                '}';
    }
}
