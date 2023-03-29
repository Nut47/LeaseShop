package com.service;

import com.entity.Soldrecord;
import com.mapper.SoldrecordMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class SoldrecordService {

    @Resource
    private SoldrecordMapper soldrecordMapper;


    public Integer insertSold(Soldrecord soldrecord){
        return soldrecordMapper.insertSold(soldrecord);
    }

    public Integer deleteSold(String id){
        return soldrecordMapper.deleteSold(id);
    }

    public List<Soldrecord> queryAllSoldrecord(Integer page, Integer count, String userid){
        return soldrecordMapper.queryAllSoldrecord(page,count,userid);
    }

    public Integer querySoldCount(String userid){
        return soldrecordMapper.querySoldCount(userid);
    }
}
