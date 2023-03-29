package com.service;

import com.entity.UserRole;
import com.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class UserRoleService {

    @Resource
    private UserRoleMapper userRoleMapper;

    public Integer InsertUserRole(UserRole userRole){
        return userRoleMapper.InsertUserRole(userRole);
    }

    public Integer LookUserRoleId(String userid){
        return userRoleMapper.LookUserRoleId(userid);
    }

    public void UpdateUserRole(UserRole userRole){
        userRoleMapper.UpdateUserRole(userRole);
    }
}
