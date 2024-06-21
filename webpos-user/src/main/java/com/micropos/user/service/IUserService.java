package com.micropos.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.micropos.user.model.User;
import com.micropos.user.model.dto.LoginFormDTO;
import com.micropos.user.model.vo.UserLoginVO;

public interface IUserService extends IService<User> {
    UserLoginVO login(LoginFormDTO loginFormDTO);
}
