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
@ApiModel("登录DTO")
public class LoginDTO implements Serializable {
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
     * 1管理员，2拾金取者，3遗失者
     */
    @ApiModelProperty(value = "身份类型；1管理员，2拾金取者，3遗失者", name = "roleType", required = true)
    private Integer roleType;

    @ApiModelProperty(value = "图形验证码", name = "password", required = false)
    private String captcha;

    @ApiModelProperty(value = "是否验证码登录，1为是，0为否；默认验证码登录", name = "flag", required = false)
    private Integer flag = 1;
}
