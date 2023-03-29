package com.mapper;

import com.vo.FaceUserInfo;
import com.entity.UserFaceInfo;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface MybatisUserFaceInfoMapper {

    List<UserFaceInfo> findUserFaceInfoList();

    void insertUserFaceInfo(UserFaceInfo userFaceInfo);

    List<FaceUserInfo> getUserFaceInfoByGroupId(Integer groupId);
}
