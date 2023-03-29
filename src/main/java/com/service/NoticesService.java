package com.service;

import com.entity.Notices;
import com.mapper.NoticesMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class NoticesService {

    @Resource
    private NoticesMapper noticesMapper;

    public Integer insertNotices(Notices notices){
        return noticesMapper.insertNotices(notices);
    }

    public Integer updateNoticesById(String id){
        return noticesMapper.updateNoticesById(id);
    }

    public List<Notices> queryNotices(String userid){
        return noticesMapper.queryNotices(userid);
    }

    public Integer CancelLatest(String userid){
        return noticesMapper.CancelLatest(userid);
    }

    public List<Notices> queryAllNotices(Integer page, Integer count, String userid){
        return noticesMapper.queryAllNotices(page,count,userid);
    }

    public Integer queryNoticesCount(String userid){
        return noticesMapper.queryNoticesCount(userid);
    }
}
