package com.controller;

import com.entity.Bill;
import com.service.BillService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class IndexController {

    @Resource
    private BillService billService;

    @GetMapping("/")
    public String index(){
        return "/index";
    }

    @GetMapping("/admin/index")
    public String adminIndex(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String admin = (String) session.getAttribute("admin");
        if (StringUtils.isEmpty(admin)){
            response.sendRedirect(request.getContextPath() + "/");//重定向
        }
        return "/admin/index";
    }

    @GetMapping("/login")
    public String login(){
        return "/user/logreg";
    }

    @GetMapping("/forget")
    public String forget(){
        return "user/forget";
    }

    @GetMapping("/user/center")
    public String userCenter(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userid = (String) session.getAttribute("userid");
        if (StringUtils.isEmpty(userid)){
            response.sendRedirect(request.getContextPath() + "/");//重定向
        }
        return "/user/user-center";
    }

    @RequiresPermissions("user:userinfo")
    @GetMapping("/user/pass")
    public String userinfo(){
        return "/user/updatepass";
    }

    @RequiresPermissions("user:userinfo")
    @GetMapping("/user/phone")
    public String userPhone(){
        return "/user/updatephone";
    }

    @GetMapping("/user/product")
    public String userProduct(){
        return "/user/product/productlist";
    }

    @GetMapping("/user/message")
    public String commonMessage(){
        return "/user/message/message";
    }

    @GetMapping("/user/alertmessage")
    public String alertMessage(){
        return "/user/message/alertmessage";
    }

    @GetMapping("/goods-listing/{num}")
    public String toGoodsListing(@PathVariable("num") int num, HttpSession session) {
        session.setAttribute("num", num);
        return "/common/product-listing";
    }

    @GetMapping("/product-elisting")
    public String toProductListing() {
        return "/common/product-listing";
    }

    @GetMapping("/product-search")
    public String toProductSearch(String keys, ModelMap modelMap) {
        if(keys==null){
            return "/error/404";
        }
        modelMap.put("keys",keys);
        return "/common/product-search";
    }

    @GetMapping("/home/console")
    public String homeConsole(){
        return "/admin/home/console";
    }

    @GetMapping("/echars/console")
    public String echarts(){
        return "/admin/echars/console";
    }

    @GetMapping("/app/message/index")
    public String appMessageIndex(){
        return "/admin/app/message/index";
    }

    @GetMapping("/user/collect")
    public String userCollect(){
        return "/user/collect/collectlist";
    }

    @GetMapping("/user/sold")
    public String sold(){
        return "/user/sold/soldrecord";
    }

    @GetMapping("/admin/sold")
    public String adminSold(){
        return "/admin/sold/soldrecord";
    }

    @GetMapping("/admin/newslist")
    public String adminNews(){
        return "/admin/news/newslist";
    }

    @GetMapping("/bill")
    public String bill(HttpSession session, Model model){
        String userid = (String) session.getAttribute("userid");
        List<Bill> list=billService.getList(userid);
        model.addAttribute("newList", list);
        return "/user/bill/billRecord";
    }

    @GetMapping("/finishPay")
    public String pay(){
        return "/user/bill/finishPay";
    }
}
