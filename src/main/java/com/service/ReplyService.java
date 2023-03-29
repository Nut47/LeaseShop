package com.service;

import com.entity.Reply;
import com.mapper.ReplyMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class ReplyService {

    @Resource
    private ReplyMapper replyMapper;

    public Integer insetReply(Reply reply){
        return replyMapper.insetReply(reply);
    }

    public List<Reply> queryReply(String cid){
        return replyMapper.queryReplys(cid);
    }

    public Reply queryById(String rid){
        return replyMapper.queryById(rid);
    }

    public Integer deleteReply(Reply reply){
        return replyMapper.deleteReply(reply);
    }
}
