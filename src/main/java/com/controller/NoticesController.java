package com.controller;


import com.entity.Notices;
import com.service.NoticesService;
import com.util.StatusCode;
import com.vo.LayuiPageVo;
import com.vo.ResultVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class NoticesController {

    @Resource
    private NoticesService noticesService;

    @ResponseBody
    @PutMapping("/notices/look/{id}")
    public ResultVo LookNoticesById (@PathVariable("id") String id) {
        Integer i = noticesService.updateNoticesById(id);
        if (i == 1){
            return new ResultVo(true, StatusCode.OK,"设置成功");
        }
        return new ResultVo(true, StatusCode.ERROR,"设置失败");
    }

    @ResponseBody
    @GetMapping("/notices/queryNotices")
    public ResultVo queryNotices (HttpSession session){
        String userid = (String) session.getAttribute("userid");
        List<Notices> noticesList = noticesService.queryNotices(userid);
        return new ResultVo(true,StatusCode.OK,"查询成功",noticesList);
    }

    @ResponseBody
    @GetMapping("/notices/cancelLatest")
    public ResultVo CancelLatest (HttpSession session){
        String userid = (String) session.getAttribute("userid");
        Integer i = noticesService.CancelLatest(userid);
        if (i == 1){
            return new ResultVo(true,StatusCode.OK,"设置成功");
        }
        return new ResultVo(true,StatusCode.ERROR,"设置失败");
    }

    @ResponseBody
    @GetMapping("/notices/queryall")
    public LayuiPageVo queryallSold(int limit, int page, HttpSession session) {
        String userid = (String) session.getAttribute("userid");
        List<Notices> noticesList = noticesService.queryAllNotices((page - 1) * limit, limit, userid);
        Integer dataNumber = noticesService.queryNoticesCount(userid);
        return new LayuiPageVo("", 0,dataNumber,noticesList);
    }

}

