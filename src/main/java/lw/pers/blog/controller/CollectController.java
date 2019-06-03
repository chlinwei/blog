package lw.pers.blog.controller;

import lw.pers.blog.exception.ResponseMessage;
import lw.pers.blog.exception.ResponseMessageUtil;
import lw.pers.blog.model.Collect;
import lw.pers.blog.model.SessionUserInfo;
import lw.pers.blog.service.CollectService;
import lw.pers.blog.util.LoginCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Controller
public class CollectController {
    @Autowired
    private CollectService collectService;

    /**
     * url:/article/1这个页面的收藏列表
     */
    @GetMapping("/getCollects")
    @ResponseBody
    public ResponseMessage getCollects(@RequestParam("articleId")int articleId,@AuthenticationPrincipal Principal principal, HttpSession session){
        LoginCheckUtil.check(principal);
        SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("userInfo");
        List<Map<String,Object>> collects = collectService.getCollects(userInfo.getId(),articleId);
        //排序,将map中isCollected为1的排在前面
        collects.sort(new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                int v1 = (int) o1.get("isCollected");
                int v2 = (int) o2.get("isCollected");
                //逆序
                if (v1 > v2) {
                    return -1;
                } else if (v1 < v2) {
                    return 1;
                }
                return 0;
            }
        });
        return ResponseMessageUtil.success(collects);
    }

    /**
     * /article/1这个页面的点击收藏确定按钮:多个收藏夹收藏或者取消一篇文章
     */
    @PostMapping("/collectArticle")
    @ResponseBody
    public ResponseMessage collectArticle(@RequestParam("articleId")int articleId,
                                          @RequestParam("authorId")int authorId,
                                          @RequestParam("collectIds[]")int[] collectIds,
                                          @AuthenticationPrincipal Principal principal, HttpSession session){

        LoginCheckUtil.check(principal);
        SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("userInfo");
        collectService.collectArticle(articleId,authorId,collectIds,userInfo.getId());
        return ResponseMessageUtil.success();
    }

    /**
     * 创建或者修改一个收藏夹
     */
    @PostMapping("/createOrUpdateCollect")
    @ResponseBody
    public ResponseMessage createCollectOrUpdate( Collect collect, @AuthenticationPrincipal Principal principal, HttpSession session ){
        LoginCheckUtil.check(principal);
        SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("userInfo");
        collect.setUserId(userInfo.getId());
        if(collect.getId()==null){
            collectService.insertCollect(collect);
        }else{
            collectService.updateCollect(collect);
        }
        return ResponseMessageUtil.success(collect);
    }

    /**
     * 删除一个收藏夹
     */
    @PostMapping("/delCollect")
    @ResponseBody
    public ResponseMessage delCollect(@RequestParam("collectId")int collectId,@AuthenticationPrincipal Principal principal,HttpSession session){
        LoginCheckUtil.check(principal);
        SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("userInfo");
        //1.判断这个收藏夹id是不是默认收藏夹
        int userId = userInfo.getId();
        Collect defaultCollect = collectService.getDefaultCollect(userId);
        if(defaultCollect.getId()==collectId){
            return ResponseMessageUtil.error("默认收藏夹不能被删除");
        }else{
            //2.开始删除
            collectService.delCollect(collectId,userId);
            return ResponseMessageUtil.success();
        }
    }

    // 页面:/collect
    @GetMapping("/findCollects")
    @ResponseBody
    public ResponseMessage findCollects(@AuthenticationPrincipal Principal principal,HttpSession session){
        LoginCheckUtil.check(principal);
        SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("userInfo");
        List<Map<String, Object>> collects = collectService.findCollects(userInfo.getId());
        return ResponseMessageUtil.success(collects);
    }

    /**
     * 获取普通收藏夹
     */
    @GetMapping("/getCollectById")
    @ResponseBody
    public ResponseMessage getCollectById(@RequestParam("collectId")int collectId,@AuthenticationPrincipal Principal principal,HttpSession session){
        LoginCheckUtil.check(principal);
        SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("userInfo");
        Collect collect = collectService.getCollectById(collectId, userInfo.getId());
        return ResponseMessageUtil.success(collect);
    }

    /**
     * 获取默认收藏夹
     */
    @GetMapping("/getDefaultCollect")
    @ResponseBody
    public ResponseMessage getDefaultCollect(
            @AuthenticationPrincipal Principal principal,HttpSession session){
        LoginCheckUtil.check(principal);
        SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("userInfo");
        Collect collect = collectService.getDefaultCollect(userInfo.getId());
        return ResponseMessageUtil.success(collect);
    }


    /**
     * 获取收藏夹里的文章
     */
    @GetMapping("/getArticlesByCollect")
    @ResponseBody
    public ResponseMessage getArticlesByCollect(
            @RequestParam(value = "pageNum",required = false,defaultValue = "1")int pageNum,
            @RequestParam(value = "pageSize",required = false,defaultValue = "5")int pageSize,
            @RequestParam("collectId")int collectId,@AuthenticationPrincipal Principal principal,HttpSession session){
        LoginCheckUtil.check(principal);
        SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("userInfo");
        Map<String, Object> data = collectService.getArticlesByCollect(collectId, userInfo.getId(),pageNum,pageSize);
        return ResponseMessageUtil.success(data);
    }


    /**
     * 获取默认收藏夹里的文章
     */
    @GetMapping("/getArticlesByDefaultCollect")
    @ResponseBody
    public ResponseMessage getArticlesByDefaultCollect(
            @RequestParam(value = "pageNum",required = false,defaultValue = "1")int pageNum,
            @RequestParam(value = "pageSize",required = false,defaultValue = "5")int pageSize,
            @AuthenticationPrincipal Principal principal,HttpSession session){
        LoginCheckUtil.check(principal);
        SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("userInfo");
        Map<String, Object> data= collectService.getArticlesByDefaultCollect(userInfo.getId(),pageNum,pageSize);
        return ResponseMessageUtil.success(data);
    }


    /**
     * 一个收藏夹取消收藏一篇文章
     */

    @PostMapping("/cancelArticle")
    @ResponseBody
    public ResponseMessage cancelArticle(
            @RequestParam("articleId")int articleId,
            @RequestParam("collectId")int collectId,
            @AuthenticationPrincipal Principal principal,
            HttpSession session
    ){
        LoginCheckUtil.check(principal);
        SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("userInfo");
        collectService.undoCollectArticle(articleId,collectId,userInfo.getId());
        return ResponseMessageUtil.success();
    }

}


