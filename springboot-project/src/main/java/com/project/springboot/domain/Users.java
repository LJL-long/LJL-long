package com.project.springboot.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 用户信息表
 * @TableName users
 */
@TableName(value ="users")
@Data
public class Users implements Serializable {
    /**
     * 主键，自增长
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 密码 
     */
    @TableField(value = "password")
    private String password;

    /**
     * 用户邮箱（可选） 
     */
    @TableField(value = "email")
    private String email;

    /**
     * 用户电话号码（可选） 
     */
    @TableField(value = "phone_number")
    private String phoneNumber;

    /**
     * 1管理员，2拾金取者，3遗失者
     */
    @TableField(value = "role_type")
    private Integer roleType;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}