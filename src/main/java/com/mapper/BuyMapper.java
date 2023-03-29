package com.mapper;

import com.entity.Buy;
import java.util.List;

public interface BuyMapper {

     int add(Buy buy);

     List<Buy> getList(String userid);
}
