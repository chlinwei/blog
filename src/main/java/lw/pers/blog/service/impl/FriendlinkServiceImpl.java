package lw.pers.blog.service.impl;

import lw.pers.blog.dao.FriendlinkDao;
import lw.pers.blog.model.Friendlink;
import lw.pers.blog.service.FriendlinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendlinkServiceImpl implements FriendlinkService{
    @Autowired
    private FriendlinkDao friendlinkDao;
    @Override
    public List<Friendlink> getAll() {
        return friendlinkDao.getAll();
    }
}
