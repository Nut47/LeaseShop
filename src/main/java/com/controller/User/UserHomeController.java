package com.controller.User;

import com.entity.Commodity;
import com.entity.UserInfo;
import com.service.CommodityService;
import com.service.UserInfoService;
import com.util.StatusCode;
import com.vo.LayuiPageVo;
import com.vo.ResultVo;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.util.List;

@Controller
public class UserHomeController {

    @Resource
    private UserInfoService userInfoService;
    @Resource
    private CommodityService commodityService;

    @ResponseBody
    @GetMapping("/user/userinfo/{userid}")
    public ResultVo userinfo(@PathVariable("userid") String userid) {
        UserInfo userInfo = userInfoService.LookUserinfo(userid);
        if (!StringUtils.isEmpty(userInfo)){
            return new ResultVo(true, StatusCode.OK, "查询成功",userInfo);
        }
        return new ResultVo(false, StatusCode.ERROR, "查询失败");
    }

    @ResponseBody
    @GetMapping("/user/usercommodity/{userid}")
    public LayuiPageVo userHomeCommodity(@PathVariable("userid") String userid,int limit, int page) {
        List<Commodity> commodityList = commodityService.queryAllCommodity((page - 1) * limit, limit, userid,1);
        Integer dataNumber = commodityService.queryCommodityCount(userid,1);
        return new LayuiPageVo("", 0,dataNumber,commodityList);
    }

}
