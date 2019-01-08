package com.kenji.cloud.repository;

import com.kenji.cloud.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: Cjmmy
 * @Date: 2018/12/27 9:03 AM
 */
public interface MenuRepository extends JpaRepository<Menu,Long> {
    /**
     * 查询所有
     * @return
     */
    @Override
    @Query(value = "from Menu menu")
    List<Menu> findAll();
}
