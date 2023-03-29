package com.mapper;

import com.entity.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserInfoMapper {

    UserInfo LookUserinfo(String userid);

    List<UserInfo> queryAllUserInfo(@Param("page") Integer page, @Param("count") Integer count, @Param("roleid") Integer roleid, @Param("userstatus") Integer userstatus);

    Integer queryAllUserCount(Integer roleid);

    Integer userReg(UserInfo userInfo);

    Integer UpdateUserInfo(UserInfo userInfo);

    UserInfo queryPartInfo(String userid);
}
