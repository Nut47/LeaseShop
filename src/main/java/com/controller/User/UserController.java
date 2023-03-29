package com.controller.User;

import com.entity.Login;
import com.entity.UserInfo;
import com.service.LoginService;
import com.service.UserInfoService;
import com.util.*;
import com.vo.ResultVo;
import net.sf.json.JSONObject;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Controller
public class UserController {

    @Resource
    private LoginService loginService;
    @Resource
    private UserInfoService userInfoService;

    private static Map<String, String> phonecodemap = new HashMap<>();

    @ResponseBody
    @PutMapping("/user/updatepwd")
    public ResultVo updatepwd(HttpSession session, HttpServletRequest request) throws IOException {
        JSONObject json = JsonReader.receivePost(request);
        String oldpwd = json.getString("oldpwd");
        String newpwd = json.getString("newpwd");
        String userid = (String) session.getAttribute("userid");
        Login login = new Login();
        UserInfo userInfo = new UserInfo();
        login.setUserid(userid);
        Login login1 = loginService.userLogin(login);
        String oldpwds = new Md5Hash(oldpwd, "Lease-Shop").toString();
        if (oldpwds.equals(login1.getPassword())){
            String passwords = new Md5Hash(newpwd, "Lease-Shop").toString();
            login.setPassword(passwords);
            userInfo.setPassword(passwords).setUserid(login1.getUserid());
            Integer integer = loginService.updateLogin(login);
            Integer integer1 = userInfoService.UpdateUserInfo(userInfo);
            if (integer == 1 && integer1 == 1) {
                return new ResultVo(true, StatusCode.OK, "修改密码成功");
            }
            return new ResultVo(false, StatusCode.ERROR, "修改密码失败");
        }
        return new ResultVo(false, StatusCode.LOGINERROR, "当前密码错误");
    }

    @ResponseBody
    @PostMapping("/user/avatar")
    public ResultVo userAvatar( HttpSession session) {
        String userid = (String) session.getAttribute("userid");
        UserInfo userInfo = userInfoService.queryPartInfo(userid);
        return new ResultVo(true, StatusCode.OK, "查询头像成功",userInfo);
    }

    @PostMapping(value = "/user/updateuimg")
    @ResponseBody
    public JSONObject updateuimg(@RequestParam(value = "file", required = false) MultipartFile file, HttpSession session) throws IOException {
        JSONObject res = new JSONObject();
        JSONObject resUrl = new JSONObject();
        String filename = UUID.randomUUID().toString().replaceAll("-", "");
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());//获得文件扩展名
        String filenames = filename + "." + ext;//文件全名
        String pathname = "C:\\Users\\Time\\Desktop\\LeaseShop\\src\\main\\resources\\static\\file\\" + filenames;
        file.transferTo(new File(pathname));
        resUrl.put("src", "/pic/"+filenames);
        res.put("msg", "");
        res.put("code", 0);
        res.put("data", resUrl);
        String uimgUrl = "/pic/" + filenames;

        String userid=(String) session.getAttribute("userid");
        UserInfo userInfo = new UserInfo().setUserid(userid).setUimage(uimgUrl);
        userInfoService.UpdateUserInfo(userInfo);
        return res;
    }

    @RequiresPermissions("user:userinfo")
    @GetMapping("/user/lookinfo")
    public String lookinfo(HttpSession session, ModelMap modelMap) {
        String userid = (String) session.getAttribute("userid");
        UserInfo userInfo = userInfoService.LookUserinfo(userid);
        modelMap.put("userInfo",userInfo);
        return "/user/userinfo";
    }

    @GetMapping("/user/perfectinfo")
    public String perfectInfo(HttpSession session, ModelMap modelMap) {
        String userid = (String) session.getAttribute("userid");
        UserInfo userInfo = userInfoService.LookUserinfo(userid);
        modelMap.put("perfectInfo",userInfo);
        return "/user/perfectInfo";
    }

    @ResponseBody
    @PostMapping("/user/updateinfo")
    public ResultVo updateInfo(@RequestBody UserInfo userInfo, HttpSession session) {
        String username = userInfo.getUsername();
        String userid = (String) session.getAttribute("userid");
        Login login = new Login();
        if (!StringUtils.isEmpty(username)){
            login.setUsername(username);
            Login login1 = loginService.userLogin(login);
            if (!StringUtils.isEmpty(login1)){
                return new ResultVo(false, StatusCode.ERROR, "该用户名已存在");
            }
            login.setUserid(userid);
            loginService.updateLogin(login);
        }
        userInfo.setUserid(userid);
        Integer integer1 = userInfoService.UpdateUserInfo(userInfo);
        if (integer1 == 1) {
            return new ResultVo(true, StatusCode.OK, "修改成功");
        }
        return new ResultVo(false, StatusCode.ERROR, "修改失败");
    }

    @ResponseBody
    @PostMapping("/user/sendupdatephone")
    public ResultVo sendupdatephone(HttpServletRequest request) throws IOException {
        JSONObject json = JsonReader.receivePost(request);
        final String mobilephone = json.getString("mobilephone");
        Integer type = json.getInt("type");
        Login login = new Login();
        if(type!=2){
            return new ResultVo(false,StatusCode.ACCESSERROR,"违规操作");
        }
        if (!JustPhone.justPhone(mobilephone)) {
            return new ResultVo(false,StatusCode.ERROR,"请输入正确格式的手机号");
        }
        login.setMobilephone(mobilephone);
        Login userIsExist = loginService.userLogin(login);
        if (!StringUtils.isEmpty(userIsExist)){
            return new ResultVo(false, StatusCode.REPERROR,"手机号已存在");
        }
        String code = GetCode.phonecode();
        Integer result = new SmsUtil().SendMsg(mobilephone, code, type);
        if(result == 1) {
            phonecodemap.put(mobilephone, code);
            ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
                    new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());
            executorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    phonecodemap.remove(mobilephone);
                    ((ScheduledThreadPoolExecutor) executorService).remove(this::run);
                }
            },5 * 60 * 1000,5 * 60 * 1000, TimeUnit.HOURS);
            return new ResultVo(true,StatusCode.SMS,"验证码发送成功");
        }else if(result == 2){
            return new ResultVo(false,StatusCode.ERROR,"请输入正确格式的手机号");
        }
        return new ResultVo(false,StatusCode.REMOTEERROR,"验证码发送失败");
    }

    @ResponseBody
    @PutMapping("/user/updatephone/{mobilephone}/{vercode}")
    public ResultVo updatephone(@PathVariable("mobilephone")String mobilephone,@PathVariable("vercode")String vercode,HttpSession session) {
        String userid = (String) session.getAttribute("userid");
        String rel = phonecodemap.get(mobilephone);
        if (StringUtils.isEmpty(rel)) {
            return new ResultVo(false,StatusCode.ERROR,"请重新获取验证码");
        }
        if (rel.equalsIgnoreCase(vercode)) {
            Login login = new Login().setUserid(userid).setMobilephone(mobilephone);
            UserInfo userInfo = new UserInfo().setUserid(userid).setMobilephone(mobilephone);
            Integer integer = loginService.updateLogin(login);
            Integer integer1 = userInfoService.UpdateUserInfo(userInfo);
            if (integer == 1 && integer1 == 1) {
                return new ResultVo(true, StatusCode.OK, "更换手机号成功");
            }
            return new ResultVo(false, StatusCode.SERVERERROR, "系统错误，更换失败");
        }
        return new ResultVo(false,StatusCode.ERROR,"验证码错误");
    }


}

