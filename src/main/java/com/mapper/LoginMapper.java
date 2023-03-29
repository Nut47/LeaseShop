package com.mapper;

import com.entity.Login;

public interface LoginMapper {

    Integer loginAdd(Login login);

    Login userLogin(Login login);

    Integer updateLogin(Login login);

    Integer findRoleId(String userid);
}