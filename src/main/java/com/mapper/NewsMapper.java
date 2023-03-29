package com.mapper;

import com.entity.News;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NewsMapper {

    Integer insertNews(News news);

    Integer delectNews(String id);

    Integer updateNews(News news);

    News queryNewsById(String id);

    void addNewsRednumber(String id);

    List<News> queryNews();

    List<News> queryAllNews(@Param("page") Integer page, @Param("count") Integer count);

    Integer LookNewsCount();
}
