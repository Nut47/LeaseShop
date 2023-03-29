package com.controller;

import com.entity.Collect;
import com.service.CollectService;
import com.util.GetDate;
import com.util.KeyUtil;
import com.util.StatusCode;
import com.vo.LayuiPageVo;
import com.vo.ResultVo;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CollectController {

    @Resource
    private CollectService collectService;

    @ResponseBody
    @PostMapping("/collect/operate")
    public ResultVo insertcollect(@RequestBody Collect collect, HttpSession session){
        String couserid = (String) session.getAttribute("userid");
        Integer colloperate = collect.getColloperate();
        collect.setCouserid(couserid);

        if (StringUtils.isEmpty(couserid)){
            return new ResultVo(false, StatusCode.ACCESSERROR,"请先登录");
        }

        if (colloperate == 1){
            Collect collect1 = collectService.queryCollectStatus(collect);
            if(!StringUtils.isEmpty(collect1)){
                collect1.setCommname(collect.getCommname()).setCommdesc(collect.getCommdesc()).setSchool(collect.getSchool())
                        .setSoldtime(GetDate.strToDate());
                Integer i = collectService.updateCollect(collect);
                if (i == 1){
                    return new ResultVo(true, StatusCode.OK,"收藏成功");
                }
                return new ResultVo(false,StatusCode.ERROR,"收藏失败");
            }else{
                collect.setId(KeyUtil.genUniqueKey());
                Integer i = collectService.insertCollect(collect);
                if (i == 1){
                    return new ResultVo(true, StatusCode.OK,"收藏成功");
                }
                return new ResultVo(false,StatusCode.ERROR,"收藏失败");
            }

        }else {
            Collect collect1 = collectService.queryCollectStatus(collect);
            if (collect1.getCouserid().equals(couserid)){
                Integer i = collectService.updateCollect(collect);
                if (i == 1){
                    return new ResultVo(true, StatusCode.OK,"取消成功");
                }
                return new ResultVo(false,StatusCode.ERROR,"取消失败");
            }
            return new ResultVo(false,StatusCode.ACCESSERROR,"禁止操作");
        }
    }

    @ResponseBody
    @PutMapping("/collect/delete/{id}")
    public ResultVo deletecollect(@PathVariable("id") String id,HttpSession session){
        String couserid = (String) session.getAttribute("userid");
        Collect collect = new Collect().setId(id).setCouserid(couserid);
        Collect collect1 = collectService.queryCollectStatus(collect);
        if (collect1.getCouserid().equals(couserid)){
            collect.setColloperate(2);
            Integer i = collectService.updateCollect(collect);
            if (i == 1){
                return new ResultVo(true, StatusCode.OK,"取消成功");
            }
            return new ResultVo(false,StatusCode.ERROR,"取消失败");
        }
        return new ResultVo(false,StatusCode.ACCESSERROR,"禁止操作");
    }

    @ResponseBody
    @GetMapping("/user/collect/queryall")
    public LayuiPageVo usercollect(int limit, int page, HttpSession session) {
        String couserid = (String) session.getAttribute("userid");
        List<Collect> collectList = collectService.queryAllCollect((page - 1) * limit, limit, couserid);
        Integer dataNumber = collectService.queryCollectCount(couserid);
        return new LayuiPageVo("",0,dataNumber,collectList);
    }
}

