package lw.pers.blog.dao;

import lw.pers.blog.model.User;
import org.apache.ibatis.annotations.Param;
import java.util.Map;

public interface UserDao {
    User getUserById(Integer id);

    //根据用户名返回user
    User getUserByUserName(String userName);

    //获取用户个人信息
    User getUserInfo(String userName);

    //获取用户个人信息通过用户id
    User getUserInfoById(int id);

    //获取用户名和角色
    User getUserNameAndRolesByUserName(String userName);

    //插入一个用户
    void insertUser(User user);

    //给用户插入一条角色数据,多个参数的话要写@Param,否则mybatis报错
    void insertRole(@Param("userId") int userId, @Param("roleId") int roleId);

    //修改用户头像
    void updateAvatarImgUriByUserName(@Param("userName") String userName,@Param("avatarImgUri") String avatarImgUri);


    //获取用户头像的
    String getAvatarImgUriByUserName(@Param("userName") String userName);


    //保存用户信息,根据用户名来保存
    void updateUserInfo(@Param("user") User user,@Param("oldName")String oldName);

    //修改密码
    void updatePasswd(@Param("newPasswd") String passwd,@Param("userName") String userName);

    //修改最近登录时间
    void updateLastLoginTime(@Param("userId")int userId);

}
