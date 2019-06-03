package lw.pers.blog.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lw.pers.blog.exception.ResponseMessage;
import lw.pers.blog.exception.ResponseMessageUtil;
import lw.pers.blog.model.Comment;
import lw.pers.blog.model.SessionUserInfo;
import lw.pers.blog.service.CommentService;
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
import java.util.List;
import java.util.Map;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     *获取一篇文的所有评论和回复
     */
    @GetMapping("/getComments")
    @ResponseBody
    public ResponseMessage getComments(@RequestParam("articleId")int articleId,
                                         @RequestParam("pageNum")int pageNum,
                                         @RequestParam("pageSize")int pageSize){
        //注意这里的写法
        Map comments = commentService.getAllComments(articleId,pageNum,pageSize);
        return ResponseMessageUtil.success(comments);
    }

    /**
     * 删除一个回复
     */
    @PostMapping("/delReply")
    @ResponseBody
    public ResponseMessage delReply(@RequestParam("id")int id, @AuthenticationPrincipal Principal principal, HttpSession session){
        LoginCheckUtil.check(principal);
        SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("userInfo");
        commentService.delReply(id,userInfo.getId());
        return ResponseMessageUtil.success();
    }

    /**
     * 删除一条评论,及其相关的回复
     */
    @PostMapping("/delComment")
    @ResponseBody
    public ResponseMessage delComment(@RequestParam("id")int id,@AuthenticationPrincipal Principal principal,HttpSession session){
        LoginCheckUtil.check(principal);
        SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("userInfo");
        commentService.delComment(id,userInfo.getId());
        return ResponseMessageUtil.success();
    }

    /**
     * 插入条评论
     */
    @PostMapping("/insertComment")
    @ResponseBody
    public ResponseMessage insertComment(@RequestParam("articleId")int articleId,
                                         @RequestParam("authorId")int authorId,
                                         @RequestParam("content")String content,
                                         @AuthenticationPrincipal Principal principal,
                                         HttpSession session){
        LoginCheckUtil.check(principal);
        SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("userInfo");
        Comment comment = new Comment();
        comment.setFromUid(userInfo.getId());
        comment.setContent(content);
        comment.setArticleId(articleId);
        comment.setAuthorId(authorId);
        commentService.insertComment(comment);
        return ResponseMessageUtil.success(comment.getId());
    }


    /**
     * 插入一条回复
     */
    @PostMapping("/insertReply")
    @ResponseBody
    public ResponseMessage insertReply(@RequestParam("pId")int pId,
                                       @RequestParam("articleId")int articleId,
                                       @RequestParam("authorId")int authorId,
                                       @RequestParam("content")String content,
                                       @RequestParam("toUid")int toUid,
                                       @AuthenticationPrincipal Principal principal,
                                       HttpSession session){
        LoginCheckUtil.check(principal);
        SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("userInfo");
        int id = userInfo.getId();
        Comment comment = new Comment();
        comment.setFromUid(id);
        comment.setpId(pId);
        comment.setAuthorId(authorId);
        comment.setArticleId(articleId);
        comment.setContent(content);
        comment.setToUid(toUid);
        commentService.insertReply(comment);
        return ResponseMessageUtil.success(comment.getId());
    }
}


