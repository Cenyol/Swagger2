package com.cenyol.boot.meta.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.*;

/**
 * @author Chenhanqun mail: chenhanqun1@corp.netease.com
 * @date 2018/10/22 20:19
 */
@Entity
@Data
@ApiModel( description = "用户信息类，有*号表示必填")
public class Demo {
    @Id
    @GeneratedValue
    @ApiModelProperty(notes = "主键ID", example = "6", required = true, position = 0)
    private Long id;

    @NotBlank
    @ApiModelProperty(notes = "用户名称", example = "张三", required = true, position = 1)
    @Size(min = 2, max = 16)
    private String name;

    @Min(0)
    @Max(120)
    @ApiModelProperty(notes = "用户年龄", example = "18", required = false, position = 2)
    private int age;
}
