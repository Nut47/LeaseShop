package com.service;

import com.entity.News;
import com.mapper.NewsMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class NewsService {

    @Resource
    private NewsMapper newsMapper;

    public Integer insertNews(News news){
        return newsMapper.insertNews(news);
    }

    public Integer delectNews(String id){
        return newsMapper.delectNews(id);
    }

    public Integer updateNews(News news){
        return newsMapper.updateNews(news);
    }

    public News queryNewsById(String id){
        return newsMapper.queryNewsById(id);
    }

    public void addNewsRednumber(String id){
        newsMapper.addNewsRednumber(id);
    }

    public List<News> queryNews(){
        return newsMapper.queryNews();
    }

    public List<News> queryAllNews(@Param("page") Integer page, @Param("count") Integer count){
        return newsMapper.queryAllNews(page,count);
    }

    public Integer LookNewsCount(){
        return newsMapper.LookNewsCount();
    }

}
