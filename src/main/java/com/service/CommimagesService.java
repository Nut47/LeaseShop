package com.service;

import com.entity.Commimages;
import com.mapper.CommimagesMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class CommimagesService {

    @Resource
    private CommimagesMapper commimagesMapper;

    public void InsertGoodImages(List<Commimages> list){
        commimagesMapper.InsertGoodImages(list);
    }

    public List<String> LookGoodImages(String commid){
        return commimagesMapper.LookGoodImages(commid);
    }

    public void DelGoodImages(String commid){
        commimagesMapper.DelGoodImages(commid);
    }
}
