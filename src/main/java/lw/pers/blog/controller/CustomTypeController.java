package lw.pers.blog.controller;


import lw.pers.blog.exception.ResponseMessage;
import lw.pers.blog.exception.ResponseMessageUtil;
import lw.pers.blog.model.CustomType;
import lw.pers.blog.model.SessionUserInfo;
import lw.pers.blog.service.CustomTypeService;
import lw.pers.blog.util.LoginCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
public class CustomTypeController {
    @Autowired
    private CustomTypeService customTypeService;


    /**
     * 文章分类列表
     */
    @GetMapping("/getAllCustomTypes")
    @ResponseBody
    public ResponseMessage getAllCustomTypes(HttpSession session,@AuthenticationPrincipal Principal principal){
        //这里不做登录检查,是因为用户不会点击按钮来获取信息
        SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("userInfo");
        Integer id = userInfo.getId();
        List<CustomType> list = customTypeService.getAll(id);
        return  ResponseMessageUtil.success(list);
    }

    /**
     * 删除一个文章分类标签
     */
    @PostMapping("/delCustomType")
    @ResponseBody
    public ResponseMessage delCustomType(@RequestParam("id")int id,@AuthenticationPrincipal Principal principal,HttpSession session){
        LoginCheckUtil.check(principal);
        SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("userInfo");
        customTypeService.delCustomTypeById(id,userInfo.getId());
        return ResponseMessageUtil.success();
    }

    /**
     * 更新一个用户的所有文章分类
     */
    @PostMapping("/updateCustomTypes")
    @ResponseBody
    public ResponseMessage updateCustomTypes(
            @RequestBody List<CustomType> customTypes,
            @AuthenticationPrincipal Principal principal,HttpSession session){
        LoginCheckUtil.check(principal);
        SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("userInfo");
        int userId = userInfo.getId();
        for(CustomType customType: customTypes){
            customType.setUserId(userId);
        }
        customTypeService.saveCustomTypes(customTypes);
        return ResponseMessageUtil.success();
    }

    /**
     * /userBlog/1页面的文章分类列表
     */
    @GetMapping("/getCustomTypesInUserBlog")
    @ResponseBody
    public ResponseMessage getCustomTypesInUserBlog(
            @RequestParam("userId") int userId
    ){
        List<CustomType> all = customTypeService.getAll(userId);
        return ResponseMessageUtil.success(all);
    }
}

