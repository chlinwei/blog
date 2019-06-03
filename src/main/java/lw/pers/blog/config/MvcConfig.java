package lw.pers.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("html/index");
        registry.addViewController("/index").setViewName("html/index");
        registry.addViewController("/index.html").setViewName("html/index");
        registry.addViewController("/user/userInfo").setViewName("html/user/userInfo");
        registry.addViewController("/user/securitySetting").setViewName("html/user/securitySetting");
        registry.addViewController("/user/articleManager").setViewName("html/user/articleManager");
        registry.addViewController("/user/draftManager").setViewName("html/user/draftManager");
        registry.addViewController("/user/customTypeManager").setViewName("html/user/customTypeManager");
        registry.addViewController("/user/commentManager").setViewName("html/user/commentManager");
        registry.addViewController("/editor").setViewName("html/editor");
        registry.addViewController("/register").setViewName("html/register");
        registry.addViewController("/collect").setViewName("html/collect");
        registry.addViewController("/friendlylink").setViewName("html/friendlylink");

    }
}
