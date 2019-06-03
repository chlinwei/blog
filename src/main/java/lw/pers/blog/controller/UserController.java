package lw.pers.blog.controller;

import lw.pers.blog.exception.*;
import lw.pers.blog.model.SessionUserInfo;
import lw.pers.blog.model.User;
import lw.pers.blog.service.UserService;
import lw.pers.blog.service.VisitorService;
import lw.pers.blog.service.impl.FtpService;
import lw.pers.blog.util.LoginCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Validated
@Controller
public class UserController {
    @Autowired
    private FtpService ftpService;
    @Autowired
    private UserService userService;

    @Autowired
    private VisitorService visitorService;
    /**
     * 获取个人信息
     */
    @GetMapping("/getUserInfo")
    @ResponseBody
    public ResponseMessage getUserInfo(@AuthenticationPrincipal Principal principal){
        String userName = principal.getName();
        Map<String, String> userInfo = userService.getUserInfo(userName);
        System.out.println(userInfo);
        return ResponseMessageUtil.success(userInfo);
    }

    //保存个人信息
    @PostMapping("/saveUserInfo")
    @ResponseBody
    public ResponseMessage saveUserInfo(@Valid User user, @AuthenticationPrincipal Principal principal){
        LoginCheckUtil.check(principal);
        String oldName = principal.getName();
        ResponseMessage responseMessage = new ResponseMessage();
        //如果用户名不相等
        if(!user.getUserName().equals(oldName)){
            //判断新的用户名是否已经存在
            if(userService.userNameIsExist(user.getUserName())){
                //新用户名已存在
                throw new MyException(ResultEnum.USER_EXIST);
            }else{
                //更新
                userService.updateUserInfo(user,oldName);
                //清除session信息
                SecurityContextHolder.getContext().setAuthentication(null);
                responseMessage = new ResponseMessage(ResultEnum.SUCCESS_RELOGIN);
            }
        }else {
            userService.updateUserInfo(user, oldName);
            responseMessage.setResult(ResultEnum.SUCCESS);
        }
        return responseMessage;
    }


    //保存头像
    @PostMapping("/uploadAvatar")
    @ResponseBody
    public ResponseMessage uploadAvatar(HttpSession session,@RequestParam("file") MultipartFile file, @AuthenticationPrincipal Principal principal) throws IOException {
        LoginCheckUtil.check(principal);
        if(file.isEmpty()){
            return ResponseMessageUtil.error("文件不存在");
        }
        String userName = principal.getName();
        //获取文件名称
        String fileName = file.getOriginalFilename();
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //生成新的文件
        fileName = UUID.randomUUID()+suffixName;
        //上传文件
        InputStream inputStream = file.getInputStream();
        String uri = ftpService.uploadFile(fileName, inputStream, "/useravatar");
        //情况1:此时如果数据库保存uri失败,此时刚上传的文件需要被清理掉
        //情况2:如果是以前的头像文件还在,也需要清理掉
        //后面再解决
        userService.updateAvatarImgUri(userName, uri);
        String url ="http://"+ftpService.getHost()+uri;
        SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("userInfo");
        userInfo.setAvatarUrl(url);
        return ResponseMessageUtil.success(url);
    }

    /**
     * 获取头像的url
     */
    @GetMapping("/getAvatar")
    @ResponseBody
    public ResponseMessage getAvatar(@AuthenticationPrincipal Principal principal, HttpServletRequest request){
        String userName = principal.getName();
        String url = userService.getAvatarImgUri(userName);
        if("".equals(url)){
          return ResponseMessageUtil.error("该用户没有头像");
        }
        String host = ftpService.getHost();
        return ResponseMessageUtil.success("http://"+host+url);
    }
    /**
     * 修改密码
     */

    @ResponseBody
    @PostMapping("/updatePasswd")
    public ResponseMessage updatePasswd(@RequestParam("newPasswd") String newPasswd,@AuthenticationPrincipal Principal principal) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        LoginCheckUtil.check(principal);
        String userName = principal.getName();
        userService.updatePasswd(newPasswd,userName);
        SecurityContextHolder.getContext().setAuthentication(null);
        return ResponseMessageUtil.success("密码修改成功");
    }


}

