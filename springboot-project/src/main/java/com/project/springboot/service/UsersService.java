package com.project.springboot.service;

import com.project.springboot.domain.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import com.project.springboot.domain.dto.CreateUserDTO;

/**
* @author yanzp
* @description 针对表【users(用户信息表)】的数据库操作Service
* @createDate 2024-11-16 09:44:31
*/
public interface UsersService extends IService<Users> {
    /**
     * 创建用户
     * @param createUserDTO
     * @return
     */

    int createUser(CreateUserDTO createUserDTO);
}
