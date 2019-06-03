package lw.pers.blog.controller;

import lw.pers.blog.model.SessionUserInfo;
import lw.pers.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class SuccessController {

    @Autowired
    private ArticleService articleService;

    /**
     * 此页面为发布文章成功后跳转到的页面
     */
    @GetMapping("/pubSuccess/{articleId}")
    public String getPubSuccessPage(@PathVariable("articleId") int articleId, @AuthenticationPrincipal Principal principal, HttpSession session){
        //1.判断用户是否含有该文章
        SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("userInfo");
        boolean b = articleService.userHasArticle(articleId, userInfo.getId());
        if(!b){
            return "redirect:/error/404";
        }
        return "html/pubArticle_success";
    }
}
