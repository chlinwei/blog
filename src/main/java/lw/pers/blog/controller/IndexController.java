package lw.pers.blog.controller;

import lw.pers.blog.exception.ResponseMessage;
import lw.pers.blog.exception.ResponseMessageUtil;
import lw.pers.blog.model.SessionUserInfo;
import lw.pers.blog.service.ArticleService;
import lw.pers.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;

    /**
     * 获取所有文章
     */
    @GetMapping("/getAllArticle")
    @ResponseBody
    public ResponseMessage getAllArticle(
            @RequestParam(value = "pageNum",required = false,defaultValue = "1")int pageNum,
            @RequestParam(value = "pageSize",required = false,defaultValue = "10")int pageSize,
            HttpSession session
    ){
        SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("userInfo");
        Integer myId = null;
        if(userInfo!=null){
            myId = userInfo.getId();
        }
        Map<String, Object> allArticle = articleService.getAllArticle(myId, pageNum, pageSize);
        return ResponseMessageUtil.success(allArticle);
    }

    /**
     * 获取最新发布的文章
     */
    @GetMapping("/getLatestArticles")
    @ResponseBody
    public ResponseMessage getLatestArticles(@RequestParam("num") int num){
        List<Map<String, Object>> list = articleService.getLatestArticles(num);
        return ResponseMessageUtil.success(list);
    }

    /**
     * 获取概括
     */
    @GetMapping("getIndexSummary")
    @ResponseBody
    public ResponseMessage getIndexSummary(){
        Map map = new HashMap<String,Object>();
        int articleNum = articleService.getAllArticleNum();
        int commentNum = commentService.getAllcommentNum();
        map.put("articleNum",articleNum);
        map.put("commentNum",commentNum);
        return ResponseMessageUtil.success(map);
    }
}

