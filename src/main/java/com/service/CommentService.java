package com.service;

import com.entity.Comment;
import com.mapper.CommentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class CommentService {

    @Resource
    private CommentMapper commentMapper;

    public Integer insertComment(Comment comment){
        return commentMapper.insertComment(comment);
    }

    public List<Comment> queryComments(String commid){
        return commentMapper.queryComments(commid);
    }

    public Comment queryById(String cid){
        return commentMapper.queryById(cid);
    }

    public Integer deleteComment(String cid){
        return commentMapper.deleteComment(cid);
    }
}
