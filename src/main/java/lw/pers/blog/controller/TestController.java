package lw.pers.blog.controller;


import lw.pers.blog.dao.PersonDao;
import lw.pers.blog.dao.UserDao;
import lw.pers.blog.model.Person;
import lw.pers.blog.model.Role;
import lw.pers.blog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class TestController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private PersonDao personDao;

    @ResponseBody
    @RequestMapping("/hello")
    public User hello(Integer id, HttpServletRequest request){
        return userDao.getUserById(id);
    }


    @RequestMapping("/test")
    public String all(){
        return "html/test";
    }


    @RequestMapping("/createUser")
    public void insertUser(String userName,String passwd,String gender){
        User user = new User();
        user.setUserName(userName);
        user.setPasswd(passwd);
        user.setGender(gender);
        userDao.insertUser(user);
    }

}
