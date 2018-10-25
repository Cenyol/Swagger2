package com.cenyol.boot.dao;

import com.cenyol.boot.meta.entity.Demo;
import org.springframework.data.repository.CrudRepository;
/**
 * @author Chenhanqun mail: chenhanqun1@corp.netease.com
 * @date 2018/10/22 20:19
 */

public interface DemoDao extends CrudRepository<Demo, Long>{
    Demo findByName(String name);
}

