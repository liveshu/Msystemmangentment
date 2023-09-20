package com.example.service.Impl;

import com.example.constant.MessageConstant;
import com.example.exception.AccountExistException;
import com.example.exception.PasswordErrorException;
import com.example.exception.AccountNotFoundException;
import com.example.exception.AccountExistException;
import com.example.mapper.LoginMapper;
import com.example.pojo.dto.LoginDTO;
import com.example.pojo.entity.Login;
import com.example.pojo.entity.SignIn;
import com.example.service.LoginService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    @Override
    public Login login(LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Login login= loginMapper.getByUsername(username);

        //2、处理各种异常情况
        if (login == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        if (!password.equals(login.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        //3、返回实体对象
        return login;
    }

    /*注册测试*/
    @Override
    public void signIn(SignIn signIn) {

        /*判断用户名是否已经存在*/
        String username = signIn.getUsername();
        Login login=loginMapper.contains(username);
        if(login!=null){
            throw new  AccountExistException(MessageConstant.USERNAME_EXIST);
        }

        /*判断两次输入密码是否一致*/
        String password = signIn.getPassword();
        String passswordRepeat = signIn.getPassswordRepeat();
        if(!passswordRepeat.equals(password)){
            throw new PasswordErrorException(MessageConstant.PASSWORD_DIFFERENT);
        }

        Login login1=new Login();
        BeanUtils.copyProperties(signIn,login1);
        loginMapper.create(login1);
    }
}
