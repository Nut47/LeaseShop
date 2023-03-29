package com.mapper;

import com.entity.Commodity;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface CommodityMapper {

    Integer InsertCommodity(Commodity commodity);

    Commodity LookCommodity(Commodity commodity);

    Integer ChangeCommodity(Commodity commodity);

    Integer ChangeCommstatus(@Param("commid") String commid, @Param("commstatus") Integer commstatus);

    List<Commodity> queryCommodityByName(@Param("page") Integer page, @Param("count") Integer count, @Param("commname") String commname);

    Integer queryCommodityByNameCount(@Param("commname") String commname);

    List<Commodity> queryAllCommodity(@Param("page") Integer page, @Param("count") Integer count, @Param("userid") String userid, @Param("commstatus") Integer commstatus);

    Integer queryCommodityCount(@Param("userid") String userid, @Param("commstatus") Integer commstatus);

    List<Commodity> queryCommodityByCategory(@Param("category") String category);

    List<Commodity> queryAllCommodityByCategory(@Param("page") Integer page, @Param("count") Integer count, @Param("area") String area, @Param("school") String school,
                                                @Param("category") String category, @Param("minmoney") BigDecimal minmoney, @Param("maxmoney") BigDecimal maxmoney);

    Integer queryAllCommodityByCategoryCount(@Param("area") String area, @Param("school") String school, @Param("category") String category,
                                             @Param("minmoney") BigDecimal minmoney, @Param("maxmoney") BigDecimal maxmoney);
}