package com.service;

import com.entity.Commodity;
import com.mapper.CommodityMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class CommodityService {

    @Resource
    private CommodityMapper commodityMapper;

    @Async
    public Integer InsertCommodity(Commodity commodity){
        return commodityMapper.InsertCommodity(commodity);
    }

    public Commodity LookCommodity(Commodity commodity){
        return commodityMapper.LookCommodity(commodity);
    }

    public Integer ChangeCommodity(Commodity commodity){
        return commodityMapper.ChangeCommodity(commodity);
    }

    public Integer ChangeCommstatus(String commid,Integer commstatus){
        return commodityMapper.ChangeCommstatus(commid,commstatus);
    }

    public List<Commodity> queryCommodityByName(Integer page,Integer count,String commname){
        return commodityMapper.queryCommodityByName(page,count,"%"+commname+"%");
    }

    public Integer queryCommodityByNameCount(String commname){
        return commodityMapper.queryCommodityByNameCount("%"+commname+"%");
    }

    public List<Commodity> queryAllCommodity(Integer page,Integer count,String userid,Integer commstatus){
        return commodityMapper.queryAllCommodity(page,count,userid,commstatus);
    }

    public Integer queryCommodityCount(String userid,Integer commstatus){
        return commodityMapper.queryCommodityCount(userid,commstatus);
    }

    public List<Commodity> queryCommodityByCategory(String category){
        return commodityMapper.queryCommodityByCategory(category);
    }

    public List<Commodity> queryAllCommodityByCategory(Integer page,Integer count,String area,String school,String category,BigDecimal minmoney,BigDecimal maxmoney){
        return commodityMapper.queryAllCommodityByCategory(page,count,area,school,category,minmoney,maxmoney);
    }

    public Integer queryAllCommodityByCategoryCount(String area, String school, String category, BigDecimal minmoney, BigDecimal maxmoney){
        return commodityMapper.queryAllCommodityByCategoryCount(area,school,category,minmoney,maxmoney);
    }
}
