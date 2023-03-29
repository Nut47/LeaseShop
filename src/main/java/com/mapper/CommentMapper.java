package com.mapper;

import com.entity.Comment;

import java.util.List;

public interface CommentMapper {

    Integer insertComment(Comment comment);

    List<Comment> queryComments(String commid);

    Comment queryById(String cid);

    Integer deleteComment(String cid);
}
