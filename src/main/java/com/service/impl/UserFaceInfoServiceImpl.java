package com.service.impl;

import com.entity.UserFaceInfo;
import com.mapper.MybatisUserFaceInfoMapper;
import com.service.UserFaceInfoService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class UserFaceInfoServiceImpl implements UserFaceInfoService {

    @Resource
    private MybatisUserFaceInfoMapper userFaceInfoMapper;

    @Override
    public void insertSelective(UserFaceInfo userFaceInfo) {
        userFaceInfoMapper.insertUserFaceInfo(userFaceInfo);
    }
}
