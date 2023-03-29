package com.controller;

import com.entity.Bill;
import com.service.BillService;
import com.util.AlipayUtilTest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AlipayController {

    private AlipayUtilTest alipayUtilTest;

    @Resource
    private BillService billService;

    @Resource
    public void setAlipayUtilTest(AlipayUtilTest alipayUtilTest) {
        this.alipayUtilTest = alipayUtilTest;
    }

    @PostMapping("zhiFuBao")
    @ResponseBody
    public String zhiFuBao(String id, String price, String title, HttpSession session){
        String pay = alipayUtilTest.aliPay(id, price, title);
        session.setAttribute("form", pay);
        session.setAttribute("billId", id);
        return "OK";
    }

    @GetMapping("/return")
    public String returnNotice(HttpSession session, Model model) {
        String userid = (String) session.getAttribute("userid");
        Integer billId = Integer.parseInt((String) session.getAttribute("billId"));
        billService.updateState(1, billId);
        List<Bill> list = billService.getList(userid);
        model.addAttribute("newList", list);
        return "/user/user-center";
    }

    @PostMapping("/notify")
    public void notifyUrl(String trade_no, String total_amount, String trade_status){
        System.out.println("支付宝订单编号："+trade_no+"，订单金额："+total_amount+"，订单状态："+trade_status);
    }
}
