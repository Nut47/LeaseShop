package com.controller.admin;

import com.entity.*;
import com.service.*;
import com.util.JustPhone;
import com.util.KeyUtil;
import com.util.StatusCode;
import com.util.ValidateCode;
import com.vo.LayuiPageVo;
import com.vo.ResultVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AdminController {

    @Resource
    private UserRoleService userRoleService;
    @Resource
    private LoginService loginService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private CommodityService commodityService;
    @Resource
    private NoticesService noticesService;

    @GetMapping("/admin")
    public String adminToLogin() {
        return "admin/login/login";
    }

    @GetMapping("/admin/login")
    public String adminLogin(HttpSession session){
        Login login = new Login("admin","123456");
        String account=login.getUsername();
        String password=login.getPassword();
        UsernamePasswordToken token;
        String username = account;
        token=new UsernamePasswordToken(username, new Md5Hash(password,"Lease-Shop").toString());
        Subject subject= SecurityUtils.getSubject();
        try {
            subject.login(token);
            //盐加密
            String passwords = new Md5Hash(password, "Lease-Shop").toString();
            login.setPassword(passwords);
            Login login1 = loginService.userLogin(login);
            //查询登录者的权限
            Integer roleId = userRoleService.LookUserRoleId(login1.getUserid());
            if (roleId == 2 || roleId == 3){
                session.setAttribute("admin",login1.getUsername());
                session.setAttribute("username",login1.getUsername());
                return "/admin/index";
            }
        }catch (UnknownAccountException e){
            return "/admin/login/login";
        }catch (IncorrectCredentialsException e){
            return "/admin/login/login";
        }
        return "/admin/login/login";
    }

    @GetMapping("/admin/userlist")
    public String userlist(){
        return "/admin/user/userlist";
    }

    @RequiresPermissions("admin:set")
    @GetMapping("/admin/adminlist")
    public String adminlist(){
        return "/admin/user/adminlist";
    }

    @GetMapping("/admin/userlist/{roleid}/{userstatus}")
    @ResponseBody
    public LayuiPageVo userlist(int limit, int page,@PathVariable("roleid") Integer roleid,@PathVariable("userstatus") Integer userstatus) {
        List<UserInfo> userInfoList = userInfoService.queryAllUserInfo((page - 1) * limit, limit,roleid,userstatus);
        Integer dataNumber = userInfoService.queryAllUserCount(roleid);
        return new LayuiPageVo("",0,dataNumber,userInfoList);
    }

    @PutMapping("/admin/set/administrator/{userid}/{roleid}")
    @ResponseBody
    public ResultVo setadmin(@PathVariable("userid") String userid,@PathVariable("roleid") Integer roleid) {
        if (roleid == 2){
            Integer i = loginService.updateLogin(new Login().setUserid(userid).setRoleid(roleid));
            if (i == 1){
                userRoleService.UpdateUserRole(new UserRole().setUserid(userid).setRoleid(2).setIdentity("网站管理员"));
                Notices notices = new Notices().setId(KeyUtil.genUniqueKey()).setUserid(userid).setTpname("系统通知")
                        .setWhys("恭喜您已被设置为网站管理员，努力维护网站的良好氛围。");
                noticesService.insertNotices(notices);
                return new ResultVo(true, StatusCode.OK, "设置管理员成功");
            }
            return new ResultVo(true, StatusCode.ERROR, "设置管理员失败");
        }else if (roleid == 1){
            Integer i = loginService.updateLogin(new Login().setUserid(userid).setRoleid(roleid));
            if (i == 1){
                userRoleService.UpdateUserRole(new UserRole().setUserid(userid).setRoleid(1).setIdentity("网站用户"));
                Notices notices = new Notices().setId(KeyUtil.genUniqueKey()).setUserid(userid).setTpname("系统通知")
                        .setWhys("您已被设置为网站用户，希望您再接再厉。");
                noticesService.insertNotices(notices);
                return new ResultVo(true, StatusCode.OK, "设置成员成功");
            }
            return new ResultVo(true, StatusCode.ERROR, "设置成员失败");
        }
        return new ResultVo(false,StatusCode.ACCESSERROR,"违规操作");
    }

    @PutMapping("/admin/user/forbid/{userid}/{userstatus}")
    @ResponseBody
    public ResultVo adminuserlist(@PathVariable("userid") String userid,@PathVariable("userstatus") Integer userstatus) {
        if (userstatus == 0){
            Integer i = loginService.updateLogin(new Login().setUserid(userid).setUserstatus(userstatus));
            Integer j = userInfoService.UpdateUserInfo(new UserInfo().setUserid(userid).setUserstatus(userstatus));
            if (i ==1 && j == 1){
                Notices notices = new Notices().setId(KeyUtil.genUniqueKey()).setUserid(userid).setTpname("系统通知")
                        .setWhys("因为您的不良行为，您在该网站的账号已被封号。");
                noticesService.insertNotices(notices);
                return new ResultVo(true, StatusCode.OK, "封号成功");
            }
            return new ResultVo(true, StatusCode.ERROR, "封号失败");
        }else if (userstatus == 1){
            Integer i = loginService.updateLogin(new Login().setUserid(userid).setUserstatus(userstatus));
            Integer j = userInfoService.UpdateUserInfo(new UserInfo().setUserid(userid).setUserstatus(userstatus));
            if (i ==1 && j == 1){
                Notices notices = new Notices().setId(KeyUtil.genUniqueKey()).setUserid(userid).setTpname("系统通知")
                        .setWhys("您在该网站的账号已被解封，希望您保持良好的行为。");
                noticesService.insertNotices(notices);
                return new ResultVo(true, StatusCode.OK, "解封成功");
            }
            return new ResultVo(true, StatusCode.ERROR, "解封失败");
        }
        return new ResultVo(false,StatusCode.ACCESSERROR,"违规操作");
    }

    @GetMapping("/admin/product")
    public String adminproduct(){
        return "/admin/product/productlist";
    }

    @GetMapping("/admin/commodity/{commstatus}")
    @ResponseBody
    public LayuiPageVo userCommodity(@PathVariable("commstatus") Integer commstatus, int limit, int page) {
        if(commstatus==100){
            List<Commodity> commodityList = commodityService.queryAllCommodity((page - 1) * limit, limit, null, null);
            Integer dataNumber = commodityService.queryCommodityCount(null, null);
            return new LayuiPageVo("",0,dataNumber,commodityList);
        }else{
            List<Commodity> commodityList = commodityService.queryAllCommodity((page - 1) * limit, limit, null, commstatus);
            Integer dataNumber = commodityService.queryCommodityCount(null, commstatus);
            return new LayuiPageVo("",0,dataNumber,commodityList);
        }
    }

    @ResponseBody
    @PutMapping("/admin/changecommstatus/{commid}/{commstatus}")
    public ResultVo ChangeCommstatus(@PathVariable("commid") String commid, @PathVariable("commstatus") Integer commstatus) {
        Integer i = commodityService.ChangeCommstatus(commid, commstatus);
        if (i == 1){
            Commodity commodity = commodityService.LookCommodity(new Commodity().setCommid(commid));
            if (commstatus == 0){
                Notices notices = new Notices().setId(KeyUtil.genUniqueKey()).setUserid(commodity.getUserid()).setTpname("商品审核")
                        .setWhys("您的商品 <a href=/product-detail/"+commodity.getCommid()+"/0 style=\"color:#08bf91\" target=\"_blank\" >"+commodity.getCommname()+"</a> 未通过审核，目前不支持公开发布。");
                noticesService.insertNotices(notices);
            }else if (commstatus == 1){
                Notices notices = new Notices().setId(KeyUtil.genUniqueKey()).setUserid(commodity.getUserid()).setTpname("商品审核")
                        .setWhys("您的商品 <a href=/product-detail/"+commodity.getCommid()+"/0 style=\"color:#08bf91\" target=\"_blank\" >"+commodity.getCommname()+"</a> 已通过审核，快去看看吧。");
                noticesService.insertNotices(notices);
            }
            return new ResultVo(true,StatusCode.OK,"操作成功");
        }
        return new ResultVo(false,StatusCode.ERROR,"操作失败");
    }

}
