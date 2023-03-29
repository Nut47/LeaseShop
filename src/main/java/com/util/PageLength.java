package com.util;

public class PageLength {
    public static Integer getPages(Integer dataNumber,Integer pageSize){
        return (dataNumber + pageSize - 1) / pageSize;
    }
}
