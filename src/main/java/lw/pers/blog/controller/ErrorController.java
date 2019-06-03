package lw.pers.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
    @GetMapping("/error/404")
    public String get404Page(){
        return "error/404";
    }

    @GetMapping("/error/405")
    public String get405Page(){
        return "error/405";
    }

    @GetMapping("/error/500")
    public String get500Page(){
        return "error/500";
    }
}
