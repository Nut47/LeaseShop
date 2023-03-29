package com.mapper;

import com.entity.Notices;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoticesMapper {

    Integer insertNotices(Notices notices);

    Integer updateNoticesById(String id);

    List<Notices> queryNotices(String userid);

    Integer CancelLatest(String userid);

    List<Notices> queryAllNotices(@Param("page") Integer page, @Param("count") Integer count, @Param("userid") String userid);

    Integer queryNoticesCount(@Param("userid") String userid);
}
