package com.project.springboot.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("创建用户DTO")
public class CreateUserDTO implements Serializable {
    /**
     * 用户名
     */

    @ApiModelProperty(value = "用户名", name = "username", required = true)
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", name = "password", required = true)
    private String password;

    /**
     * 用户邮箱（可选）
     */
    @ApiModelProperty(value = "用户邮箱（可选）", name = "email", required = false)
    private String email;

    /**
     * 用户电话号码（可选）
     */
    @ApiModelProperty(value = "用户电话号码（可选）", name = "phoneNumber", required = false)
    private String phoneNumber;

    /**
     * 1管理员，2拾金取者，3遗失者
     */
    @ApiModelProperty(value = "身份类型；1管理员，2拾金取者，3遗失者", name = "roleType", required = true)
    private Integer roleType;
}
