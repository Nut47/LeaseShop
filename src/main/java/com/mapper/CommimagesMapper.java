package com.mapper;

import com.entity.Commimages;

import java.util.List;

public interface CommimagesMapper {

    void InsertGoodImages(List<Commimages> list);

    List<String> LookGoodImages(String commid);

    void DelGoodImages(String commid);
}
