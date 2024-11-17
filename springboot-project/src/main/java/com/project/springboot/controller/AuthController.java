package com.project.springboot.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.project.springboot.common.domain.ResponseModel;
import com.project.springboot.domain.Users;
import com.project.springboot.domain.dto.CreateUserDTO;
import com.project.springboot.domain.dto.ForgotPasswordDTO;
import com.project.springboot.domain.dto.LoginDTO;
import com.project.springboot.service.UsersService;
import com.project.springboot.util.CaptchaUtil;
import com.project.springboot.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户信息接口
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UsersService usersService;


    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CaptchaUtil captchaUtil;

    @GetMapping("/captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        captchaUtil.createCaptcha(request, response);
    }

    /**
     * 创建用户返回对应的用户id
     *
     * @param createUserDTO
     * @return
     */
    @PostMapping("/createUser")
    @ApiOperation("创建用户返回对应的用户id")
    public ResponseModel createUser(@RequestBody CreateUserDTO createUserDTO) {
        try {
            int userId = usersService.createUser(createUserDTO);
            return ResponseModel.ok("注册成功!", userId);
        } catch (Exception e) {
            return ResponseModel.error("注册失败!");
        }
    }

    @PostMapping("/login")
    public ResponseModel login(@RequestBody LoginDTO loginDTO, HttpServletRequest request) {

        //开启验证码登录
        if (1 == loginDTO.getFlag()) {
            String sessionCaptcha = (String) request.getSession().getAttribute("captcha");
            if (ObjectUtil.isEmpty(loginDTO.getCaptcha()) || !loginDTO.getCaptcha().equalsIgnoreCase(sessionCaptcha)) {
                return ResponseModel.error("图片验证码错误");
            }
        }

        LambdaQueryWrapper<Users> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Users::getUsername, loginDTO.getUsername());
        lambdaQueryWrapper.eq(Users::getPassword, loginDTO.getPassword());
        lambdaQueryWrapper.eq(Users::getRoleType, loginDTO.getRoleType());
        lambdaQueryWrapper.last(" limit 1");
        Users user = usersService.getOne(lambdaQueryWrapper);
        if (ObjectUtil.isEmpty(user)) {
            return ResponseModel.error("账号或密码错误!");
        }

        return ResponseModel.ok("登录成功", null, "Bearer " + jwtUtil.generateToken(user.getUsername(), user.getRoleType()));
    }

    @PostMapping("/forgotPassword")
    public ResponseModel forgotPassword(@RequestBody ForgotPasswordDTO forgotPasswordDTO) {
        Users users = new Users();
        users.setUserId(forgotPasswordDTO.getId());
        users.setPassword(forgotPasswordDTO.getNewPassword());
        if (usersService.updateById(users)) {
            return ResponseModel.ok("密码更新成功");
        } else {
            return ResponseModel.error("用户名不存在");
        }
    }
}
