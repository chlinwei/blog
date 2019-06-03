package lw.pers.blog.service;

import lw.pers.blog.model.User;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public interface UserService {
    /**
     * 根据id,返回一个用户
     */
    User findUserById(int id);

    /**
     * 根据用户名,返回一个用户
     */
    User findUserByUserName(String userName);

    /**
     * 获取个人信息
     */
    Map<String,String> getUserInfo(String userName);

    /**
     * 创建一个用户
     */
    public void createUser(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException;


    /**
     * 修改用户头像
     */
    public void updateAvatarImgUri(String userName,String avatarImgUri);

    /**
     * 获取用户头像
     */
    public String getAvatarImgUri(String userName);

    /**
     * 修改用户信息
     */
    public void updateUserInfo(User user,String oldName);

    /**
     * 根据用户名判断某个用户是否存在
     */
    public boolean userNameIsExist(String userName);

    /**
     * 根据用户名判断某个用户是否存在
     */
    public boolean userIsExist(int userId );

    /**
     * 修改密码
     */
    public void updatePasswd(String newPasswd,String userName) throws UnsupportedEncodingException, NoSuchAlgorithmException;


    /**
     * 在/userBlog页面获取用户基本信息
     */
    public Map<String,Object> getUserInfoInUserBlog(int userId);

    /**
     * 修改用户最近登录时间
     */
    public void updateLastLoginTime(int userId);
}
