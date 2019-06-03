package lw.pers.blog.dao;

import lw.pers.blog.model.Friendlink;

import java.util.List;

public interface FriendlinkDao{
    /**
     * 查询所有链接
     */
    public List<Friendlink> getAll();

}