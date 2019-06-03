package lw.pers.blog.service.impl;

import lw.pers.blog.constant.RoleConstant;
import lw.pers.blog.dao.CustomTypeDao;
import lw.pers.blog.dao.UserDao;
import lw.pers.blog.exception.MyException;
import lw.pers.blog.exception.ResultEnum;
import lw.pers.blog.model.Collect;
import lw.pers.blog.model.CustomType;
import lw.pers.blog.model.User;
import lw.pers.blog.service.CollectService;
import lw.pers.blog.service.CustomTypeService;
import lw.pers.blog.service.UserService;
import lw.pers.blog.util.AvatarlUtil;
import lw.pers.blog.util.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private CustomTypeDao customTypeDao;
    @Autowired
    private CollectService collectService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private HttpServletRequest request;

    @Value("${ftp.host}")
    private String ftpHost;

    @Override
    public User findUserById(int id) {
        return userDao.getUserById(id);
    }

    @Override
    public User findUserByUserName(String userName) {
        User user = userDao.getUserByUserName(userName);
        if(user==null){
            throw new MyException("用户不存在");
        }
        return user;
    }

    @Override
    public Map<String, String> getUserInfo(String userName) {
        User userInfo = userDao.getUserInfo(userName);
        Map map = new HashMap<String,String>();
        map.put("id",userInfo.getId());
        map.put("userName",userInfo.getUserName());
        map.put("email",userInfo.getEmail());
        map.put("gender",userInfo.getGender());
        map.put("birthday",userInfo.getBirthday());
        map.put("personalBrief",userInfo.getPersonalBrief());
        map.put("sign",userInfo.getSign());
        map.put("phone",userInfo.getPhone());
        return map;
    }

    @Transactional
    @Override
    public void createUser(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        //1.首先判断这个用户是否存在
        User u = userDao.getUserByUserName(user.getUserName());
        if(u!=null){
            throw new MyException(ResultEnum.USER_EXIST);
        }
        //2.插入用户
        user.setPasswd(Md5.encodeByMd5(user.getPasswd()));
        userDao.insertUser(user);
        //3.给用户设置角色
        userDao.insertRole(user.getId(), RoleConstant.ROLE_USER);


        //4.给用户创建默认收藏夹
        Collect collect = new Collect();
        collect.setName("默认收藏夹");
        collect.setUserId(user.getId());
        collect.setIsDefault(1);
        collectService.insertCollect(collect);


        //5.创建文章分类
        CustomType customType = new CustomType();
        customType.setName("默认分类");
        customType.setUserId(user.getId());
        customTypeDao.insertCustomType(customType);
    }


    @Override
    public void updateAvatarImgUri(String userName, String avatarImgUri) {
        userDao.updateAvatarImgUriByUserName(userName,avatarImgUri);
    }

    @Override
    public String getAvatarImgUri(String userName) {
        return userDao.getAvatarImgUriByUserName(userName);
    }

    @Override
    public boolean userNameIsExist(String userName) {
        User user = userDao.getUserByUserName(userName);
        return user != null;
    }

    @Override
    public boolean userIsExist(int userId) {
        User user = userDao.getUserInfoById(userId);
        return user != null;
    }

    @Override
    public void updateUserInfo(User user,String oldName) {
        userDao.updateUserInfo(user,oldName);
    }

    @Override
    public void updatePasswd(String newPasswd, String userName) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String pwd = Md5.encodeByMd5(newPasswd);
        userDao.updatePasswd(pwd,userName);
    }

    @Override
    public Map<String, Object> getUserInfoInUserBlog(int userId) {
        User user = userDao.getUserInfoById(userId);
        Map<String,Object> map = new HashMap<>();
        map.put("id",user.getId());
        map.put("userName",user.getUserName());
        map.put("gender",user.getGender());
        map.put("sign",user.getSign());
        map.put("personalBrief",user.getPersonalBrief());
        map.put("avaUrl", AvatarlUtil.getUrl(ftpHost,user.getAvatarImgUri()));
        map.put("lastLoginTime",user.getLastLoginTime());
        map.put("createTime",user.getCreateTime());
        map.put("birthday",user.getBirthday());
        return map;
    }

    @Override
    public void updateLastLoginTime(int userId) {
        userDao.updateLastLoginTime(userId);
    }
}
