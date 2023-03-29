package com.mapper;

import com.entity.Soldrecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SoldrecordMapper {

    Integer insertSold(Soldrecord soldrecord);

    Integer deleteSold(String id);

    List<Soldrecord> queryAllSoldrecord(@Param("page") Integer page, @Param("count") Integer count, @Param("userid") String userid);

    Integer querySoldCount(@Param("userid") String userid);
}
