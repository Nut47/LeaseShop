package com.service;

import com.entity.Bill;
import com.mapper.BillMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class BillService {

    @Resource
    private BillMapper billMapper;

    public int add(Bill bill) {
        return billMapper.add(bill);
    }

    public List<Bill> getList(String userid) {
        return billMapper.getList(userid);
    }

    public int delete(Bill bill) { return billMapper.delete(bill);}

    public int updateState(int state,int billid) {return billMapper.updateState(state,billid);}

    public int renewal(Bill bill){return billMapper.renewal(bill);}

    public int addAddress(String userid,String address){return billMapper.addAddress(userid,address);}

    public List<String> getAddress(String userid){return billMapper.getAddress(userid);}

    public int updateAddress(String address,int billid){return billMapper.updateAddress(address,billid);}
}
