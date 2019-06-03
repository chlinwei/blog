package lw.pers.blog.service.security;

import lw.pers.blog.dao.UserDao;
import lw.pers.blog.model.Role;
import lw.pers.blog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.getUserNameAndRolesByUserName(s);
        if(user == null){
            throw new UsernameNotFoundException("用户不存在,请登录");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(Role role:user.getRoles()){
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.getName());
            authorities.add(simpleGrantedAuthority);
        }
        org.springframework.security.core.userdetails.User user1 = new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPasswd(), authorities);
        return user1;
    }
}
