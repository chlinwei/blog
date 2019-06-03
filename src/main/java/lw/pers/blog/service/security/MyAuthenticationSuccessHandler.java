package lw.pers.blog.service.security;

import lw.pers.blog.model.SessionUserInfo;
import lw.pers.blog.model.User;
import lw.pers.blog.service.UserService;
import lw.pers.blog.util.AvatarlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 完成登录后,初始化工作
 */
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler{
    @Autowired
    private UserService userService;

    @Value("${ftp.host}")
    private String ftpHost;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        System.out.println("登录成功");
        HttpSession session = httpServletRequest.getSession();
        //把头像的url保存在session中
        String userName = authentication.getName();
        User user = userService.findUserByUserName(userName);
        SessionUserInfo userInfo = new SessionUserInfo();
        String imgUri = user.getAvatarImgUri();
        String url = AvatarlUtil.getUrl(ftpHost,imgUri);
        String lastUrl = (String) session.getAttribute("lastUrl");
        userInfo.setAvatarUrl(url);
        userInfo.setId(user.getId());
        userInfo.setUserName(user.getUserName());
        session.setAttribute("userInfo",userInfo);


        //修改用户最近登录时间
        userService.updateLastLoginTime(user.getId());

        //要重定向的页面,默认是index
        String redirectUrl = httpServletRequest.getContextPath()+"/index";
        SavedRequest savedRequest= (SavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
        if(lastUrl!=null){
            redirectUrl = lastUrl;
            session.removeAttribute("lastUrl");
        }else if(savedRequest != null){
            redirectUrl = savedRequest.getRedirectUrl();
            session.removeAttribute("SPRING_SECURITY_SAVED_REQUEST");
        }
        httpServletResponse.sendRedirect(redirectUrl);
    }
}

