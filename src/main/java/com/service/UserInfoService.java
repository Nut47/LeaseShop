package com.service;

import com.entity.UserInfo;
import com.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    public UserInfo LookUserinfo(String userid) {
        return userInfoMapper.LookUserinfo(userid);
    }

    public List<UserInfo> queryAllUserInfo(Integer page,Integer count,Integer roleid,Integer userstatus){
        return userInfoMapper.queryAllUserInfo(page,count,roleid,userstatus);
    }

    public Integer queryAllUserCount(Integer roleid){
        return userInfoMapper.queryAllUserCount(roleid);
    }

    public Integer userReg(UserInfo userInfo){
        return userInfoMapper.userReg(userInfo);
    }

    public Integer UpdateUserInfo(UserInfo userInfo){
        return userInfoMapper.UpdateUserInfo(userInfo);
    }

    public UserInfo queryPartInfo(String userid){
        return userInfoMapper.queryPartInfo(userid);
    }
}
