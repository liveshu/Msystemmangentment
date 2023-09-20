package com.example.service;

import com.example.pojo.dto.LoginDTO;
import com.example.pojo.entity.Login;
import com.example.pojo.entity.SignIn;

public interface LoginService {
    Login login(LoginDTO loginDTO);

    /*注册测试*/
    void signIn(SignIn signIn);
}
