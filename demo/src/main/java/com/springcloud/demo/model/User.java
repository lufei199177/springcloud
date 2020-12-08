package com.springcloud.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lufei
 * @date 2020/10/15
 * @desc
 */
@Data
@ApiModel(value = "User",description = "用户实体类")
public class User {

    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "姓名",required = true)
    private String name;
    @ApiModelProperty(value = "年龄",required = true)
    private int age;
}
