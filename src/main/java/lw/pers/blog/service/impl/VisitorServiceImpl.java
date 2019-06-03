package lw.pers.blog.service.impl;

import lw.pers.blog.dao.UserDao;
import lw.pers.blog.dao.VisitorDao;
import lw.pers.blog.model.User;
import lw.pers.blog.model.Visitor;
import lw.pers.blog.service.VisitorService;
import lw.pers.blog.util.AvatarlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VisitorServiceImpl implements VisitorService{
    @Value("${ftp.host}")
    private String ftpHost;

    @Autowired
    private UserDao userDao;

    @Autowired
    private VisitorDao visitorDao;

    @Override
    @Transactional
    public List<Map<String, Object>> getVisitors(int userId) {
        List<Visitor> visitors = visitorDao.getVisitors(userId);
        List<Map<String,Object>> list = new ArrayList<>();
        for(Visitor visitor:visitors){
            Map map = new HashMap<String,Object>();
            map.put("visitorId",visitor.getVisitorId());

            User user = userDao.getUserById(visitor.getVisitorId());
            map.put("visitorName",user.getUserName());

            map.put("visitorAvatarUrl", AvatarlUtil.getUrl(ftpHost,user.getAvatarImgUri()));
            list.add(map);
        }
        return list;
    }

    @Override
    @Transactional
    public void updateVisitor(int visitorId,int userId) {
        //硬编码:最近访问人数最多为6人
        int count = visitorDao.countVisitor(userId);
        //先判断访问者是否已经存在
        Visitor visitor = visitorDao.getVisitor(visitorId, userId);
        if(visitor!=null){
            //存在
            //1.删除存在的visitor
            visitorDao.delVisitor(visitorId,userId);
            //插入
            visitorDao.insertVisitor(visitorId,userId);

        }else{
            //不存在
            if(count<6){
                visitorDao.insertVisitor(visitorId,userId);
            }else{
                //先删除最旧的visitor
                visitorDao.delOldestVisitor(userId);
                //在插入visitor
                visitorDao.insertVisitor(visitorId,userId);
            }
        }
    }
}
