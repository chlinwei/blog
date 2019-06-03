package lw.pers.blog.controller;

import lw.pers.blog.exception.ResponseMessage;
import lw.pers.blog.exception.ResponseMessageUtil;
import lw.pers.blog.model.User;
import lw.pers.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Validated //开启校验,不然写了@Pattern,@Email等不会生效的
@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/doRegister")
    @ResponseBody
    public ResponseMessage register(@Valid User user, HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        userService.createUser(user);
        String contextPath = request.getContextPath();
        Map<String,String> map = new HashMap<>();
        map.put("url",contextPath+"/index");
        return ResponseMessageUtil.success(map);
    }
}
