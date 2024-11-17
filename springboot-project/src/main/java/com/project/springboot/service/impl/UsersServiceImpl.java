package com.project.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.springboot.domain.Users;
import com.project.springboot.domain.dto.CreateUserDTO;
import com.project.springboot.service.UsersService;
import com.project.springboot.mapper.UsersMapper;
import org.springframework.stereotype.Service;

/**
 * @author yanzp
 * @description 针对表【users(用户信息表)】的数据库操作Service实现
 * @createDate 2024-11-16 09:44:31
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

    @Override
    public int createUser(CreateUserDTO createUserDTO) {
        Users users = BeanUtil.copyProperties(createUserDTO, Users.class);
        this.save(users);
        return users.getUserId();
    }
}




