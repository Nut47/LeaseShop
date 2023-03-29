package com.service;

import com.entity.Buy;
import com.mapper.BuyMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class BuyService {

    @Resource
    private BuyMapper buyMapper;

    public int add(Buy buy) {
        return buyMapper.add(buy);
    }

    public List<Buy> getList(String userid) {
        return buyMapper.getList(userid);
    }
}