package com.micropos.user.rest;

import com.micropos.user.model.dto.LoginFormDTO;
import com.micropos.user.model.vo.UserLoginVO;
import com.micropos.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    @GetMapping("hello")
    public String hello() {
        System.out.println("hello!!");
        return "hello";
    }

    @PostMapping("login")
    public ResponseEntity<UserLoginVO> login(@RequestBody @Validated LoginFormDTO loginFormDTO){
        try {
            return new ResponseEntity<>(userService.login(loginFormDTO), HttpStatus.OK);
        } catch (Exception e) {
            UserLoginVO vo = new UserLoginVO();
            vo.setMessage(e.getMessage());
            return new ResponseEntity<>(vo, HttpStatus.BAD_REQUEST);
        }
    }


}
