package lw.pers.blog.controller;

import lw.pers.blog.exception.ResponseMessage;
import lw.pers.blog.exception.ResponseMessageUtil;
import lw.pers.blog.model.SessionUserInfo;
import lw.pers.blog.service.LikeService;
import lw.pers.blog.util.LoginCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.security.Principal;

/**
 * 点赞
 */
@Controller
public class LikeController {
    @Autowired
    private LikeService likeService;
    /**
     * 进行点赞操作
     */
    @PostMapping("/doLike")
    @ResponseBody
    public ResponseMessage doLike(
            @RequestParam("typeId")int typeId,
            @RequestParam("ownerId")int ownerId,
            @RequestParam("type")int type,
            @AuthenticationPrincipal Principal principal,
            HttpSession session){
        LoginCheckUtil.check(principal);
        SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("userInfo");
        likeService.insertLike(typeId,ownerId,type,userInfo.getId());
        return ResponseMessageUtil.success();
    }

    /**
     * 取消点赞功能
     */
    @PostMapping("/undoLike")
    @ResponseBody
    public ResponseMessage undoLike(@RequestParam("typeId")int typeId,@RequestParam("type")int type,@AuthenticationPrincipal Principal principal,HttpSession session){
        LoginCheckUtil.check(principal);
        SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("userInfo");
        likeService.undoLike(typeId,type,userInfo.getId());
        return ResponseMessageUtil.success();
    }
}
