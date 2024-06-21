package com.micropos.user.model.dto;

import lombok.Data;

@Data
public class LoginFormDTO {
    private String username;
    private String password;
    private Boolean rememberMe = false;
}