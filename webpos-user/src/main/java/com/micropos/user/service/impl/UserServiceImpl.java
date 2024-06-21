package com.micropos.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.micropos.api.exception.BadRequestException;
import com.micropos.user.config.JwtProperties;
import com.micropos.user.mapper.UserMapper;
import com.micropos.user.model.User;
import com.micropos.user.model.dto.LoginFormDTO;
import com.micropos.user.model.vo.UserLoginVO;
import com.micropos.user.service.IUserService;
import com.micropos.user.utils.JwtTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final PasswordEncoder passwordEncoder;

    private final JwtTool jwtTool;

    private final JwtProperties jwtProperties;

    @Override
    public UserLoginVO login(LoginFormDTO loginDTO) {
        // 1.数据校验
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        System.out.println("login username:" + username);
        System.out.println("login password:" + password);

        // 2.根据用户名或手机号查询
        User user = lambdaQuery().eq(User::getUsername, username).one();
//        Assert.notNull(user, "用户名错误");
        if (user == null) {
            throw new BadRequestException("用户不存在");
        }
        // 4.校验密码
        System.out.println(password);
        System.out.println(user.getPassword());
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadRequestException("用户名或密码错误");
        }
        // 5.生成TOKEN
        String token = jwtTool.createToken(user.getId(), jwtProperties.getTokenTTL());
        // 6.封装VO返回
        UserLoginVO vo = new UserLoginVO();
        vo.setUserId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setToken(token);
        vo.setMessage("登陆成功！");

        System.out.println(vo.getUserId());
        System.out.println(vo.getUsername());
        System.out.println(vo.getToken());
        return vo;
    }

}