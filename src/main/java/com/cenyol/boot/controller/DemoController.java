package com.cenyol.boot.controller;


import com.cenyol.boot.dao.DemoDao;
import com.cenyol.boot.meta.entity.Demo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Chenhanqun mail: chenhanqun1@corp.netease.com
 * @date 2018/10/22 20:20
 * H2参考：https://blog.csdn.net/zhangjq520/article/details/53931003
 * Swaggers参考：http://blog.didispace.com/springbootswagger2/
 *              https://blog.csdn.net/hry2015/article/details/80614315
 */
@RestController
@Api(tags = "Demo", description = "相关接口")
public class DemoController {

    @Autowired
    private DemoDao demoDao;

    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @ApiImplicitParam(name = "demo", value = "用户详细实体user", required = true, dataType = "Demo")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(@RequestBody Demo demo){
        demoDao.save(demo);
        return "ok";
    }

    @ApiOperation(value = "返回用户Demo列表.")
    @RequestMapping(value = "find", method = RequestMethod.GET)
    public List<Demo> find(){
        return (List<Demo>) demoDao.findAll();
    }

    @ApiOperation(value = "根据用户名进行查询", response = Demo.class)
    @ApiImplicitParam(name = "name", value = "用户姓名", required = true, paramType = "query", dataType = "String")
    @RequestMapping(value = "findByName", method = RequestMethod.GET)
    public Demo findByName(String name){
        return demoDao.findByName(name);
    }
}
