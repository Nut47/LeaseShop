package com.service;

import com.entity.Login;
import com.mapper.LoginMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

@Service
@Transactional
public class LoginService {

    @Resource
    private LoginMapper loginMapper;

    public Integer loginAdd(Login login){
        return loginMapper.loginAdd(login);
    }

    public Login userLogin(Login login){
        return loginMapper.userLogin(login);
    }

    public Integer updateLogin(Login login){
        return loginMapper.updateLogin(login);
    }

    public Integer findRoleId(String userid) {
        return loginMapper.findRoleId(userid);
    }
}
