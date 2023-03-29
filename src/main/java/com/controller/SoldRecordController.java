package com.controller;

import com.entity.Soldrecord;
import com.service.SoldrecordService;
import com.util.StatusCode;
import com.vo.LayuiPageVo;
import com.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class SoldRecordController {

    @Resource
    private SoldrecordService soldrecordService;

    @ResponseBody
    @PutMapping("/soldrecord/delect/{id}")
    public ResultVo delectSold (@PathVariable("id") String id) {
        Integer i = soldrecordService.deleteSold(id);
        if (i == 1){
            return new ResultVo(true, StatusCode.OK,"删除记录成功");
        }
        return new ResultVo(false, StatusCode.ERROR,"删除记录失败");
    }

    @ResponseBody
    @GetMapping("/soldrecord/lookuser")
    public LayuiPageVo LookUserSold(int limit, int page, HttpSession session) {
        String userid = (String) session.getAttribute("userid");
        List<Soldrecord> soldrecordList = soldrecordService.queryAllSoldrecord((page - 1) * limit, limit, userid);
        Integer dataNumber = soldrecordService.querySoldCount(userid);
        return new LayuiPageVo("",0,dataNumber,soldrecordList);
    }

    @ResponseBody
    @GetMapping("/soldrecord/queryall")
    public LayuiPageVo queryAllSold(int limit, int page) {
        List<Soldrecord> soldrecordList = soldrecordService.queryAllSoldrecord((page - 1) * limit, limit, null);
        Integer dataNumber = soldrecordService.querySoldCount(null);
        return new LayuiPageVo("",0,dataNumber,soldrecordList);
    }

}

