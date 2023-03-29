package com.mapper;

import com.entity.Reply;

import java.util.List;

public interface ReplyMapper {

    Integer insetReply(Reply reply);

    List<Reply> queryReplys(String cid);

    Reply queryById(String rid);

    Integer deleteReply(Reply reply);
}
