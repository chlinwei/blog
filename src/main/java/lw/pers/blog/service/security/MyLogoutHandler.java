package lw.pers.blog.service.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 主要目的是:退出登录后,跳转到指定界面
 * 退出登录分为两种情况:
 * 1.手动退出,也就是点击按钮访问/logout
 * 2.自动退出,也就是session过期,此时不会进入到MyLogoutHandler
 */
public class MyLogoutHandler implements LogoutHandler {
    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        String referer = httpServletRequest.getHeader("Referer");
        if(referer!=null){
            try {
                httpServletResponse.sendRedirect(referer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            //这种情况基本不会发生
            try {
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        try {
//            httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/login");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
