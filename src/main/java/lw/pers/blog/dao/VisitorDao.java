package lw.pers.blog.dao;

import lw.pers.blog.model.Visitor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VisitorDao {
    /**
     *获取某个用户的最近访问人员列表
     */
    public List<Visitor> getVisitors(@Param("userId")int userId);

    /**
     * 删除一条最近访问记录
     */
    public void delVisitor(@Param("visitorId")int visitorId,@Param("userId")int userId);

    /**
     * 插入一条最近访问记录
     */
    public void insertVisitor(@Param("visitorId")int visitorId,@Param("userId")int userId);

    /**
     * 查询出当前用户所包含的访问者数量
     */
    public int countVisitor(@Param("userId")int userId);

    /**
     * 删除最旧的访问者
     */
    public int delOldestVisitor(@Param("userId")int userId);

    public Visitor getVisitor(@Param("visitorId")int visitorId,@Param("userId")int userId);
}
