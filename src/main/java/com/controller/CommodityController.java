package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.entity.*;
import com.service.*;
import com.util.KeyUtil;
import com.util.StatusCode;
import com.vo.LayuiPageVo;
import com.vo.PageVo;
import com.vo.ResultVo;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Controller
public class CommodityController {

    @Resource
    private CommodityService commodityService;
    @Resource
    private CommimagesService commimagesService;
    @Resource
    private LoginService loginService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private SoldrecordService soldrecordService;
    @Resource
    private CollectService collectService;
    @Resource
    private NoticesService noticesService;
    @Resource
    private BillService billService;
    @Resource
    private BuyService buyService;

    @GetMapping("/user/relgoods")
    public String torelgoods(HttpSession session){
        return "/user/product/relgoods";
    }

    @GetMapping("/user/editgoods/{commid}")
    public String toeditgoods(@PathVariable("commid")String commid, HttpSession session, ModelMap modelMap){
        Commodity commodity=commodityService.LookCommodity(new Commodity().setCommid(commid));
        if(commodity.getCommstatus().equals(2) || commodity.getCommstatus().equals(4)){
            return "/error/404";//商品已被删除或已完成交易
        }
        String[] commons=commodity.getCommon().split("、");
        commodity.setCommon(commons[0]).setCommon2(commons[1]);
        modelMap.put("goods",commodity);
        modelMap.put("otherimg",commimagesService.LookGoodImages(commid));
        return "/user/product/changegoods";
    }

    @PostMapping("/changegoods/rel")
    @ResponseBody
    public String changegoods(@RequestBody Commodity commodity, HttpSession session){
        String userid = (String) session.getAttribute("userid");
        commodity.setUpdatetime(new Date()).setCommstatus(3);
        commodity.setCommon(commodity.getCommon()+"、"+commodity.getCommon2());//常用选项拼接
        commodityService.ChangeCommodity(commodity);
        commimagesService.DelGoodImages(commodity.getCommid());
        List<Commimages> commimagesList=new ArrayList<>();
        for (String list:commodity.getOtherimg()) {
            commimagesList.add(new Commimages().setId(KeyUtil.genUniqueKey()).setCommid(commodity.getCommid()).setImage(list));
        }
        commimagesService.InsertGoodImages(commimagesList);
        /**发出待审核系统通知*/
        Notices notices = new Notices().setId(KeyUtil.genUniqueKey()).setUserid(userid).setTpname("商品审核")
                .setWhys("您的商品 <a href=/product-detail/"+commodity.getCommid()+"/0 style=\"color:#08bf91\" target=\"_blank\" >"+commodity.getCommname()+"</a> 进入待审核队列，请您耐心等待。");
        noticesService.insertNotices(notices);
        return "0";
    }

    @PostMapping("/relgoods/rel")
    @ResponseBody
    public String relgoods(@RequestBody Commodity commodity, HttpSession session){
        String userid = (String) session.getAttribute("userid");
        UserInfo userInfo = userInfoService.LookUserinfo(userid);
        String commid = KeyUtil.genUniqueKey();
        commodity.setCommid(commid).setUserid(userid).setSchool(userInfo.getSchool());//商品id
        commodity.setCommon(commodity.getCommon()+"、"+commodity.getCommon2());//常用选项拼接
        commodityService.InsertCommodity(commodity);
        List<Commimages> commimagesList=new ArrayList<>();
        for (String list:commodity.getOtherimg()) {
            commimagesList.add(new Commimages().setId(KeyUtil.genUniqueKey()).setCommid(commid).setImage(list));
        }
        commimagesService.InsertGoodImages(commimagesList);
        /**发出待审核系统通知*/
        Notices notices = new Notices().setId(KeyUtil.genUniqueKey()).setUserid(userid).setTpname("商品审核")
                .setWhys("您的商品 <a href=/product-detail/"+commid+"/0 style=\"color:#08bf91\" target=\"_blank\" >"+commodity.getCommname()+"</a> 进入待审核队列，请您耐心等待。");
        noticesService.insertNotices(notices);
        return "0";
    }

    @PostMapping("/relgoods/video")
    @ResponseBody
    public JSONObject relGoodsVideo(@RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        JSONObject res = new JSONObject();
        JSONObject resUrl = new JSONObject();
        String filename = UUID.randomUUID().toString().replaceAll("-", "");
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        String filenames = filename + "." + ext;
        String pathname = "C:\\Users\\Time\\Desktop\\LeaseShop\\src\\main\\resources\\static\\file\\" + filenames;
        file.transferTo(new File(pathname));
        resUrl.put("src", "/pic/"+filenames);
        res.put("msg", "");
        res.put("code", 0);
        res.put("data", resUrl);
        return res;
    }

    @PostMapping(value="/relgoods/images")
    @ResponseBody
    public JSONObject relGoodsImages(@RequestParam(value = "file", required = false) MultipartFile[] file) throws IOException {
        JSONObject res = new JSONObject();
        JSONObject resUrl = new JSONObject();
        List<String> imageurls=new ArrayList<>();
        for (MultipartFile files:file){
            String filename = UUID.randomUUID().toString().replaceAll("-", "");
            String ext = FilenameUtils.getExtension(files.getOriginalFilename());
            String filenames = filename + "." + ext;
            String pathname = "C:\\Users\\Time\\Desktop\\LeaseShop\\src\\main\\resources\\static\\file\\" + filenames;
            files.transferTo(new File(pathname));
            imageurls.add("/pic/"+filenames);
            res.put("msg", "");
            res.put("code", 0);
        }
        resUrl.put("src", imageurls);
        res.put("data", resUrl);
        return res;
    }

    @GetMapping("/product-detail/{commid}/{num}")
    public String product_detail(@PathVariable("num") int num, @PathVariable("commid") String commid, ModelMap modelMap, HttpSession session){
        String couserid = (String) session.getAttribute("userid");
        Commodity commodity = commodityService.LookCommodity(new Commodity().setCommid(commid).setCommstatus(1));
        List<Bill> bill = billService.getList(couserid);
        modelMap.put("commodity_state",0);
        modelMap.put("commodity_num",0);
        int i = 0;
        if (commodity.getCommstatus().equals(1)){//如果商品正常
            i=1;
        }else if (!StringUtils.isEmpty(couserid)){//如果用户已登录
            Login login = loginService.userLogin(new Login().setUserid(couserid));
            if (commodity.getCommstatus().equals(0) && (commodity.getUserid().equals(couserid) || (login.getRoleid().equals(2) || login.getRoleid().equals(3)))){
                i=1;
            }else if (commodity.getCommstatus().equals(3) && (commodity.getUserid().equals(couserid) || (login.getRoleid().equals(2) || login.getRoleid().equals(3)))){
                i=1;
            }else if (commodity.getCommstatus().equals(4) && (commodity.getUserid().equals(couserid) || (login.getRoleid().equals(2) || login.getRoleid().equals(3)))){
                i=1;
            }
        }
        if(i==1){
            commodity.setOtherimg(commimagesService.LookGoodImages(commid));
            commodityService.ChangeCommodity(new Commodity().setCommid(commid).setRednumber(1));
            modelMap.put("userinfo",userInfoService.queryPartInfo(commodity.getUserid()));
            modelMap.put("gddetail",commodity);
            if (StringUtils.isEmpty(couserid)){
                modelMap.put("collectstatus",2);
            }else {
                Collect collect = collectService.queryCollectStatus(new Collect().setCommid(commid).setCouserid(couserid));
                if(collect!=null){
                    if (collect.getCollstatus() == 2){
                        modelMap.put("collectstatus",2);
                    }else {
                        modelMap.put("collectstatus",1);
                    }
                }else {
                    modelMap.put("collectstatus",2);
                }
            }
            for (Bill b:bill) {
                if(commodity.getCommname().equals(b.getCommodityname())){
                    modelMap.put("commodity_state",1);
                    modelMap.put("commodity_num",b.getNumber());
                }
            }
            String target = "/common/product-detail"+num;
            return target;
        }else{
            return "/error/404";
        }
    }

    @GetMapping("/product/search/number/{commname}")
    @ResponseBody
    public PageVo searchCommodityNumber(@PathVariable("commname") String commname){
        Integer dataNumber = commodityService.queryCommodityByNameCount(commname);
        return new PageVo(StatusCode.OK,"查询成功",dataNumber);
    }

    @GetMapping("/product/search/{nowPaging}/{commname}")
    @ResponseBody
    public ResultVo searchCommodity(@PathVariable("nowPaging") Integer page, @PathVariable("commname") String commname){
        List<Commodity> commodityList = commodityService.queryCommodityByName((page - 1) * 20, 20, commname);

        if(!StringUtils.isEmpty(commodityList)){//如果有对应商品
            for (Commodity commodity : commodityList) {
                List<String> imagesList = commimagesService.LookGoodImages(commodity.getCommid());
                commodity.setOtherimg(imagesList);
            }
            return new ResultVo(true,StatusCode.OK,"查询成功",commodityList);
        }else{
            return new ResultVo(true,StatusCode.ERROR,"没有相关商品");
        }
    }

    @ResponseBody
    @GetMapping("/index/product/{category}")
    public ResultVo indexCommodity(@PathVariable("category") String category) {
        List<Commodity> commodityList = commodityService.queryCommodityByCategory(category);
        for (Commodity commodity : commodityList) {
            /**查询商品对应的其它图片*/
            List<String> imagesList = commimagesService.LookGoodImages(commodity.getCommid());
            commodity.setOtherimg(imagesList);
        }
            return new ResultVo(true,StatusCode.OK,"查询成功",commodityList);
    }

    @ResponseBody
    @GetMapping("/product/latest")
    public ResultVo latestCommodity() {
        String category = "全部";
        List<Commodity> commodityList = commodityService.queryCommodityByCategory(category);
        for (Commodity commodity : commodityList) {
            List<String> imagesList = commimagesService.LookGoodImages(commodity.getCommid());
            commodity.setOtherimg(imagesList);
        }
        return new ResultVo(true,StatusCode.OK,"查询成功",commodityList);
    }

    @GetMapping("/list-number/{category}/{area}/{minmoney}/{maxmoney}")
    @ResponseBody
    public PageVo productListNumber(@PathVariable("category") String category, @PathVariable("area") String area,
                                    @PathVariable("minmoney") BigDecimal minmoney, @PathVariable("maxmoney") BigDecimal maxmoney,
                                    HttpSession session) {
        String school=null;
        if(!area.equals("全部")){
            String userid = (String) session.getAttribute("userid");
            UserInfo userInfo = userInfoService.LookUserinfo(userid);
            school = userInfo.getSchool();
        }
        Integer dataNumber = commodityService.queryAllCommodityByCategoryCount(area, school, category, minmoney, maxmoney);
        return new PageVo(StatusCode.OK,"查询成功",dataNumber);
    }

    @GetMapping("/product-listing/{category}/{nowPaging}/{area}/{minmoney}/{maxmoney}/{price}")
    @ResponseBody
    public ResultVo productlisting(@PathVariable("category") String category, @PathVariable("nowPaging") Integer page,
                                 @PathVariable("area") String area, @PathVariable("minmoney") BigDecimal minmoney, @PathVariable("maxmoney") BigDecimal maxmoney,
                                 @PathVariable("price") Integer price, HttpSession session) {
        String school=null;
        if(!area.equals("全部")) {
            String userid = (String) session.getAttribute("userid");
            UserInfo userInfo = userInfoService.LookUserinfo(userid);
            school = userInfo.getSchool();
        }
        List<Commodity> commodityList = commodityService.queryAllCommodityByCategory((page - 1) * 16, 16, area, school, category, minmoney, maxmoney);
        for (Commodity commodity : commodityList) {
            List<String> imagesList = commimagesService.LookGoodImages(commodity.getCommid());
            commodity.setOtherimg(imagesList);
        }

        if (price != 0){
            if (price == 1){
                //此处创建了一个匿名内部类
                Collections.sort(commodityList, new Comparator<Commodity>() {
                    int i;
                    @Override
                    public int compare(Commodity o1, Commodity o2) {
                        if (o1.getThinkmoney().compareTo(o2.getThinkmoney()) > -1) {
                            System.out.println("===o1大于等于o2===");
                            i = 1;
                        } else if (o1.getThinkmoney().compareTo(o2.getThinkmoney()) < 1) {
                            i = -1;
                            System.out.println("===o1小于等于o2===");
                        }
                        return i;
                    }
                });
            }else if (price == 2){
                Collections.sort(commodityList, new Comparator<Commodity>() {
                    int i;
                    @Override
                    public int compare(Commodity o1, Commodity o2) {
                        if (o1.getThinkmoney().compareTo(o2.getThinkmoney()) > -1) {
                            System.out.println("===o1大于等于o2===");
                            i = -1;
                        } else if (o1.getThinkmoney().compareTo(o2.getThinkmoney()) < 1) {
                            System.out.println("===o1小于等于o2===");
                            i = 1;
                        }
                        return i;
                    }
                });
            }
        }
        return new ResultVo(true,StatusCode.OK,"查询成功",commodityList);
    }

    @GetMapping("/user/commodity/{commstatus}")
    @ResponseBody
    public LayuiPageVo userCommodity(@PathVariable("commstatus") Integer commstatus, int limit, int page, HttpSession session) {
        String userid = (String) session.getAttribute("userid");
        if(StringUtils.isEmpty(userid)){
            userid = "123456";
        }
        List<Commodity> commodityList=null;
        Integer dataNumber;
        if(commstatus==100){
            commodityList = commodityService.queryAllCommodity((page - 1) * limit, limit, userid,null);
            dataNumber = commodityService.queryCommodityCount(userid,null);
        }else{
            commodityList = commodityService.queryAllCommodity((page - 1) * limit, limit, userid,commstatus);
            dataNumber = commodityService.queryCommodityCount(userid,commstatus);
        }
        return new LayuiPageVo("",0,dataNumber,commodityList);
    }

    @ResponseBody
    @GetMapping("/user/changecommstatus/{commid}/{commstatus}")
    public ResultVo ChangeCommstatus(@PathVariable("commid") String commid, @PathVariable("commstatus") Integer commstatus, HttpSession session) {
        Integer i = commodityService.ChangeCommstatus(commid, commstatus);
        if (i == 1){
            if (commstatus == 4){
                String userid = (String) session.getAttribute("userid");
                Commodity commodity = commodityService.LookCommodity(new Commodity().setCommid(commid));
                Soldrecord soldrecord = new Soldrecord();
                soldrecord.setId(KeyUtil.genUniqueKey()).setCommid(commid).setCommname(commodity.getCommname()).setCommdesc(commodity.getCommdesc())
                .setThinkmoney(commodity.getThinkmoney()).setUserid(userid);
                soldrecordService.insertSold(soldrecord);
            }
            return new ResultVo(true,StatusCode.OK,"操作成功");
        }
        return new ResultVo(false,StatusCode.ERROR,"操作失败");
    }

    @PostMapping(value = "/submit")
    @ResponseBody
    public String add(Double num,String who,String goodsname,String img,String goodsdes) {
        Bill bill = new Bill();
        bill.setUserid(who);
        bill.setNumber(num);
        bill.setCommodityname(goodsname);
        bill.setCommodityphoto(img);
        bill.setCommoditydes(goodsdes);
        int rows = billService.add(bill);
        if (rows > 0) {
            return "OK";
        } else {
            return "FAIL";
        }
    }

    @RequestMapping(value = "/submit2",method = RequestMethod.POST)
    @ResponseBody
    public String add2(Double num,String who,String goodsname,String img,String goodsdes,String leaseterm,String deposit,String totalprice){
        Bill bill=new Bill();
        bill.setUserid(who);
        bill.setNumber(num);
        bill.setCommodityname(goodsname);
        bill.setCommodityphoto(img);
        bill.setCommoditydes(goodsdes);
        bill.setState("以租代售");
        bill.setLeaseterm(leaseterm);
        bill.setDeposit(deposit);
        bill.setTotalprice(totalprice);
        int rows = billService.add(bill);
        if(rows > 0){
            System.out.println("以租代售-存库成功");
            return "OK";
        }else{
            return "FAIL";
        }
    }

    @RequestMapping(value = "/submit3",method = RequestMethod.POST)
    @ResponseBody
    public String add3(Double num,String who,String goodsname,String img,String goodsdes,String leaseterm,String deposit,String totalprice){
        Bill bill=new Bill();
        bill.setUserid(who);
        bill.setNumber(num);
        bill.setCommodityname(goodsname);
        bill.setCommodityphoto(img);
        bill.setCommoditydes(goodsdes);
        bill.setState("先租后买");
        bill.setLeaseterm(leaseterm);
        bill.setDeposit(deposit);
        bill.setTotalprice(totalprice);
        int rows = billService.add(bill);
        if(rows > 0){
            System.out.println("以租代售-存库成功");
            return "OK";
        }else{
            return "FAIL";
        }
    }

    @PostMapping(value = "/submit_buy")
    @ResponseBody
    public String buy(double num,String who,String goodsname,String img,String goodsdes,HttpSession session) {
        String couserid = (String) session.getAttribute("userid");
        Buy buy = new Buy();
        buy.setUserid(who);
        buy.setNumber(num);
        buy.setCommodityname(goodsname);
        buy.setCommodityphoto(img);
        buy.setCommoditydes(goodsdes);
        int rows = buyService.add(buy);
        System.out.println(rows);
        List<Bill> bill = billService.getList(couserid);
        int billid=0;
        for (Bill b:bill) {
            billid=b.getBillid();
        }
        if (rows > 0) {
            return "OK";
        } else {
            return "FAIL";
        }
    }

    @PostMapping(value = "/submit4")
    @ResponseBody
    public String add(Double num,String who,String goodsname,String img,String goodsdes,String one,String two) {
        System.out.println("收到");
        Bill bill = new Bill();
        bill.setLeaseterm(one);
        bill.setTotalprice(two);
        bill.setUserid(who);
        bill.setNumber(num);
        bill.setCommodityname(goodsname);
        bill.setCommodityphoto(img);
        bill.setCommoditydes(goodsdes);
        bill.setState("预约租赁");
        System.out.println(bill);
        int rows = billService.add(bill);
        if (rows > 0) {
            return "OK";
        } else {
            return "FAIL";
        }
    }

    @RequestMapping(value = "/renewal",method = RequestMethod.POST)
    @ResponseBody
    public String renewal(Double number,String leaseterm,int billid){
        Bill bill=new Bill();
        bill.setBillid(billid);
        bill.setNumber(number);
        bill.setLeaseterm(leaseterm);
        int rows = billService.renewal(bill);
        if(rows > 0){
            System.out.println("续租成功");
            return "OK";
        }else{
            return "FAIL";
        }
    }

    @RequestMapping(value = "/updateAddress",method = RequestMethod.POST)
    @ResponseBody
    public String uodateAddress(int billid,String province,String city,String area,String detail) {
        String Address = province+" "+city+" "+area+" "+detail;
        System.out.println(Address);
        int rows = billService.updateAddress(Address,billid);
        if (rows > 0) {
            return "OK";
        } else {
            return "FAIL";
        }
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public String delete(int billid) {
        Bill bill = new Bill();
        bill.setBillid(billid);
        System.out.println(billid);
        int rows = billService.delete(bill);
        if (rows > 0) {
            System.out.println("删除成功");
            return "OK";
        } else {
            return "FAIL";
        }
    }
}

