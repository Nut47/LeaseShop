package com.mapper;

import com.entity.Bill;
import java.util.List;

public interface BillMapper {

     int add(Bill bill);

     List<Bill> getList(String userid);

     int delete(Bill bill);

     int updateState(int state,int billid);

     int renewal(Bill bill);

     int addAddress(String userid,String address);

     List<String> getAddress(String userid);

     int updateAddress(String address,int billid);
}
