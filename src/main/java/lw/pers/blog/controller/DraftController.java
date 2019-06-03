package lw.pers.blog.controller;

import lw.pers.blog.exception.ResponseMessage;
import lw.pers.blog.exception.ResponseMessageUtil;
import lw.pers.blog.model.Article;
import lw.pers.blog.model.SessionUserInfo;
import lw.pers.blog.service.DraftService;
import lw.pers.blog.util.LoginCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;

@Controller
public class DraftController {
    @Autowired
    private DraftService draftService;

    @GetMapping("/getDraftList")
    @ResponseBody
    public ResponseMessage getDraftList(
            @RequestParam(value = "pageNum",required = false,defaultValue = "1")int pageNum,
            @RequestParam(value = "pageSize",required = false,defaultValue = "5")int pageSize,
            @AuthenticationPrincipal Principal principal,
            HttpSession session){
        LoginCheckUtil.check(principal);
        SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("userInfo");
        Map<String, Object> result = draftService.getDraftList(userInfo.getId(), pageNum, pageSize);
        return ResponseMessageUtil.success(result);
    }

    @GetMapping("/draft/{draftId}")
    public String getDraftPage(@PathVariable("draftId")int draftId, @AuthenticationPrincipal Principal principal, HttpSession session, HttpServletRequest request){
        SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("userInfo");
        //判断此用户是否有这篇草稿
        boolean b = draftService.userHasDraft(draftId, userInfo.getId());
        request.setAttribute("flag",b);
        return "html/draft";
    }

    /**
     * 保存草稿
     */
    @PostMapping("/saveDraft")
    @ResponseBody
    public ResponseMessage saveDraft(Article article,@AuthenticationPrincipal Principal principal,HttpSession session){
        LoginCheckUtil.check(principal);
        SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("userInfo");
        article.setUserId(userInfo.getId());
        draftService.saveDraft(article);
        return ResponseMessageUtil.success(article.getId());
    }

    /**
     * 获取草稿
     */
    @GetMapping("/getDraft")
    @ResponseBody
    public ResponseMessage getDraft(@RequestParam("draftId")int draftId,@AuthenticationPrincipal Principal principal, HttpSession session ){
        LoginCheckUtil.check(principal);
        SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("userInfo");
        Map<String, Object> result = draftService.getOne(draftId, userInfo.getId());
        return ResponseMessageUtil.success(result);
    }

    /**
     * 修改草稿
     */
    @PostMapping("/updateDraft")
    @ResponseBody
    public ResponseMessage updateDraft(Article article,@AuthenticationPrincipal Principal principal,HttpSession session){
        LoginCheckUtil.check(principal);
        SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("userInfo");
        article.setUserId(userInfo.getId());
        draftService.updateDraft(article);
        return ResponseMessageUtil.success();
    }


    /**
     * 将草稿发布成文章
     */
    @PostMapping("/pubDraft")
    @ResponseBody
    public ResponseMessage pubDraft(Article article,@AuthenticationPrincipal Principal principal,HttpSession session){
        LoginCheckUtil.check(principal);
        SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("userInfo");
        article.setUserId(userInfo.getId());
        draftService.pubDraft(article);
        return ResponseMessageUtil.success(article.getId());
    }

    /**
     * 删除一篇草稿
     */
    @PostMapping("/delDraft")
    @ResponseBody
    public ResponseMessage delDraft(@RequestParam(name = "draftId",required = true)int draftId,@AuthenticationPrincipal Principal principal,HttpSession session){
        LoginCheckUtil.check(principal);
        SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("userInfo");
        draftService.delOne(draftId,userInfo.getId());
        return ResponseMessageUtil.success();
    }

    /**
     * 删除几篇草稿
     */
    @PostMapping("/delDrafts")
    @ResponseBody
    public ResponseMessage delDraft(@RequestParam(name = "draftIds[]",required = true)int[] draftIds,@AuthenticationPrincipal Principal principal,HttpSession session){
        LoginCheckUtil.check(principal);
        SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("userInfo");
        draftService.delMany(draftIds,userInfo.getId());
        return ResponseMessageUtil.success();
    }






}
