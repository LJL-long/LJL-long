package com.project.springboot.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel("忘记密码DTO")
public class ForgotPasswordDTO implements Serializable {
    @ApiModelProperty(value = "用户id", name = "id", required = true)
    Integer id;
    @ApiModelProperty(value = "新密码", name = "newPassword", required = true)
    String newPassword;
}
