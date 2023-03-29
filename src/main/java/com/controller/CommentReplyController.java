package com.controller;

import com.entity.*;
import com.service.*;
import com.util.KeyUtil;
import com.util.StatusCode;
import com.vo.ResultVo;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CommentReplyController {

    @Resource
    private CommodityService commodityService;
    @Resource
    private CommentService commentService;
    @Resource
    private ReplyService replyService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private NoticesService noticesService;

    @ResponseBody
    @GetMapping("/comment/query/{commid}")
    public ResultVo queryCommentReply(@PathVariable("commid") String commid){
        List<Comment> commentsList = commentService.queryComments(commid);
        for (Comment comment : commentsList) {
            List<Reply> repliesList = replyService.queryReply(comment.getCid());
            for (Reply reply : repliesList) {
                UserInfo ruser = userInfoService.queryPartInfo(reply.getRuserid());
                UserInfo cuser = userInfoService.queryPartInfo(reply.getCuserid());
                reply.setRusername(ruser.getUsername()).setRuimage(ruser.getUimage()).setCusername(cuser.getUsername());
            }
            System.out.println("评论者ID：" + comment.getCuserid());
            UserInfo userInfo = userInfoService.queryPartInfo(comment.getCuserid());
            comment.setReplyLsit(repliesList).setCusername(userInfo.getUsername()).setCuimage(userInfo.getUimage());
        }
        return new ResultVo(true, StatusCode.OK,"查询评论回复成功",commentsList);
    }

    @ResponseBody
    @PostMapping("/comment/insert")
    public ResultVo insertcomment(@RequestBody Comment comment,HttpSession session) {
        String cuserid = (String) session.getAttribute("userid");
        String content = comment.getContent();

        if (StringUtils.isEmpty(cuserid)) {
            return new ResultVo(false,StatusCode.ACCESSERROR,"请登录后再评论");
        }
        content = content.replace("<", "&lt;");
        content = content.replace(">", "&gt;");
        content = content.replace("'", "\"");

        comment.setCid(KeyUtil.genUniqueKey()).setCuserid(cuserid).setContent(content);
        Integer i = commentService.insertComment(comment);
        if (i == 1){
            Commodity commodity = commodityService.LookCommodity(new Commodity().setCommid(comment.getCommid()));
            Notices notices = new Notices().setId(KeyUtil.genUniqueKey()).setUserid(comment.getSpuserid()).setTpname("评论")
                    .setWhys("您的商品 <a href=/product-detail/"+comment.getCommid()+"/0 style=\"color:#08bf91\" target=\"_blank\" >"+commodity.getCommname()+"</a> 被评论了，快去看看吧。");
            noticesService.insertNotices(notices);
            return new ResultVo(true, StatusCode.OK,"评论成功");
        }
        return new ResultVo(false,StatusCode.ERROR,"评论失败");
    }

    @ResponseBody
    @PostMapping("/reply/insert")
    public ResultVo insertreply(@RequestBody Reply reply,HttpSession session) {
        String ruserid = (String) session.getAttribute("userid");
        String recontent = reply.getRecontent();

        if (StringUtils.isEmpty(ruserid)) {
            return new ResultVo(false,StatusCode.ACCESSERROR,"请登录后再评论");
        }

        recontent = recontent.replace("<", "&lt;");
        recontent = recontent.replace(">", "&gt;");
        recontent = recontent.replace("'", "\"");

        reply.setRid(KeyUtil.genUniqueKey()).setRuserid(ruserid).setRecontent(recontent);
        Integer i = replyService.insetReply(reply);
        if (i == 1){
            Commodity commodity = commodityService.LookCommodity(new Commodity().setCommid(reply.getCommid()));
            Notices notices = new Notices().setId(KeyUtil.genUniqueKey()).setUserid(reply.getCuserid()).setTpname("评论回复")
                    .setWhys("有小伙伴在 <a href=/product-detail/"+reply.getCommid()+" style=\"color:#08bf91\" target=\"_blank\" >"+commodity.getCommname()+"</a> 下回复了您的评论，快去看看吧。");
            noticesService.insertNotices(notices);
            return new ResultVo(true, StatusCode.OK,"回复成功");
        }
        return new ResultVo(false,StatusCode.ERROR,"回复失败");
    }

    @ResponseBody
    @PutMapping("/comment/delete/{cid}")
    public ResultVo deletecomment(@PathVariable("cid") String cid,HttpSession session){
        String cuserid = (String) session.getAttribute("userid");
        Comment comment = commentService.queryById(cid);
        if (comment.getCuserid().equals(cuserid) || comment.getSpuserid().equals(cuserid)){
            Integer i = commentService.deleteComment(cid);
            Integer j = replyService.deleteReply(new Reply().setCid(cid));
            if (i == 1 && j == 1){
                return new ResultVo(true, StatusCode.OK,"删除成功");
            }
            return new ResultVo(false, StatusCode.ERROR,"删除失败");
        }
        return new ResultVo(false,StatusCode.ACCESSERROR,"禁止操作");
    }

    @ResponseBody
    @PutMapping("/reply/delete/{rid}")
    public ResultVo deletereply(@PathVariable("rid") String rid,HttpSession session){
        String ruserid = (String) session.getAttribute("userid");
        Reply reply = replyService.queryById(rid);
        if (reply.getRuserid().equals(ruserid) || reply.getSpuserid().equals(ruserid)){
            Integer i = replyService.deleteReply(new Reply().setRid(rid));
            if (i == 1){
                return new ResultVo(true, StatusCode.OK,"删除成功");
            }
            return new ResultVo(false, StatusCode.ERROR,"删除失败");
        }
        return new ResultVo(false,StatusCode.ACCESSERROR,"禁止操作");
    }

}

