package lw.pers.blog.controller;

import lw.pers.blog.exception.ResponseMessage;
import lw.pers.blog.exception.ResponseMessageUtil;
import lw.pers.blog.model.Visitor;
import lw.pers.blog.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class VisitorController {
    @Autowired
    private VisitorService visitorService;

    /**
     * 获取最近访问用户列表
     */
    @GetMapping("/getVisitors")
    @ResponseBody
    public ResponseMessage getVisitors(@RequestParam("userId")int userId){
        List<Map<String, Object>> visitors = visitorService.getVisitors(userId);
        return ResponseMessageUtil.success(visitors);
    }

}
