package lw.pers.blog.controller;

import lw.pers.blog.exception.ResponseMessage;
import lw.pers.blog.exception.ResponseMessageUtil;
import lw.pers.blog.model.Friendlink;
import lw.pers.blog.service.FriendlinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class FriendlinkController {

    @Autowired
    private FriendlinkService friendlinkService;

    @GetMapping("/getAllFriendlinks")
    @ResponseBody
    public ResponseMessage getAllFriendlinks(){
        List<Friendlink> links = friendlinkService.getAll();
        return ResponseMessageUtil.success(links);
    }
}
