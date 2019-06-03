package lw.pers.blog.dao;

import lw.pers.blog.model.Like;
import org.apache.ibatis.annotations.Param;

public interface LikeDao {
    /**
     * 查看看谋篇文章或者评论的点赞次数
     */
    public int getLikes(@Param("typeId")int typeId,@Param("type")int type);

    /**
     * 获取某个用户的所有文章和评论的点赞次数之和
     */
    public int getAllLikesByOwnerId(@Param("ownerId")int ownerId);


    /**
     * 查看某个用户是否点赞过
     */
    public Like getLike(@Param("typeId")int typeId, @Param("type")int type,@Param("userId") int userId);


    /**
     * 进行点赞
     */
    public void insertLike(@Param("typeId")int typeId,@Param("ownerId")int ownerId, @Param("type")int type, @Param("userId") int userId);


    /**
     * 取消点赞
     */
    public void delLike(@Param("typeId")int typeId,@Param("type")int type,@Param("userId") int userId);


    /**
     * 删除关于某篇文章的点赞数
     */
    public void delLikesByArticleId(@Param("typeId")int typeId,@Param("type")int type);

}
