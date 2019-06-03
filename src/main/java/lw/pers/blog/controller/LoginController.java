package lw.pers.blog.controller;
import lw.pers.blog.service.UserService;
import lw.pers.blog.service.impl.FtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private FtpService ftpService;
    /**
     * 从doLogin完成后,会转发到这个路径
     */
    @RequestMapping("/defaultSuccessUrl")
    public String defaultSuccessUrl(HttpSession session,@AuthenticationPrincipal Principal principal){
        String userName = principal.getName();
        String url = userService.getAvatarImgUri(userName);
        session.setAttribute("avatarImgUrl","http://"+ftpService.getHost()+url);
        //注意转发要写绝对路径,即不会跑到springmvc中
        //而重定向会跑到springmvc
        return "redirect:/user";
    }
    /**
     返回登录页面
     */
    @RequestMapping("/login")
    public String loginPage(HttpSession session,HttpServletRequest request){
        //是否含有登录之前的页面
        SavedRequest savedRequest = (SavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
        if(savedRequest==null) {
            String referer = request.getHeader("Referer");
            if (referer != null) {
                if (!referer.contains("/login")&&!referer.contains("/register")) {
                    session.setAttribute("lastUrl", referer);
                }
            }
        }
        return "html/login";
    }

    @RequestMapping(value = "/login/error",method = RequestMethod.GET)
    public String loginError(HttpServletRequest request,HttpServletResponse response) throws ServletException {
        HttpSession session = request.getSession();
        org.springframework.security.core.AuthenticationException exception = (org.springframework.security.core.AuthenticationException)session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        if(exception instanceof BadCredentialsException){
            //目的:将异常保存在request,因为如果保存在session中,则前端刷新一次页面,还是会显示异常信息
            session.setAttribute("SPRING_SECURITY_LAST_EXCEPTION","用户名或密码错误");
        }
        return "html/login";
    }


}
