package com.example.controller;

import com.example.constant.JwtClaimsConstant;
import com.example.pojo.dto.LoginDTO;
import com.example.pojo.entity.Login;
import com.example.pojo.entity.SignIn;
import com.example.pojo.vo.LoginVO;
import com.example.properties.JwtProperties;
import com.example.result.Result;
import com.example.service.LoginService;
import com.example.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@Slf4j
@Api(tags = "登录测试")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private JwtProperties jwtProperties;

    @ApiOperation("登陆测试")
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginDTO loginDTO) {
        log.info("管理员登录：{}", loginDTO);
        Login login = loginService.login(loginDTO);

        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, login.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        LoginVO loginVO = LoginVO.builder()
                .id(login.getId())
                .userName(login.getUsername())
                .name(login.getName())
                .token(token)
                .build();

        return Result.success(loginVO);
    }

    @PostMapping("/signIn")
    @ApiOperation("注册测试")
    public Result signIn(@RequestBody SignIn signIn){
        log.info("注册测试:{}",signIn);
        loginService.signIn(signIn);
        return Result.success();
    }
}
