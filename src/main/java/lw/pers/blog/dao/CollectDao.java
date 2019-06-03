package lw.pers.blog.dao;

import lw.pers.blog.model.Collect;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectDao {
    /**
     * 创建一个收藏夹
     */
    public void insertCollect(@Param("collect") Collect collect);

    /**
     * 修改一个收藏夹
     */
    public void updateCollect(@Param("collect") Collect collect);

    /**
     * 获取一个收藏夹
     */
    public Collect getCollectById(@Param("id")int id,@Param("userId")int userId);

    /**
     * 获取默认收藏夹
     */
    public Collect getDefaultCollect(@Param("userId")int userId);

    /**
     * 删除一个收藏夹
     */
    public void delCollect(@Param("id")int id,@Param("userId")int userId);


    /**
     * 获取一个用户的所有收藏夹
     */
    public List<Collect> getCollects(@Param("userId")int userId);


    /**
     * 收藏一篇文章
     */
    public void collectArticle(@Param("articleId")int articleId,@Param("authorId")int authorId,@Param("collectId")int collectId,@Param("userId")int userId);

    /**
     * 删除某一个收藏夹里所收藏的文章
     */
    public void undoCollectArticle(@Param("articleId")int articleId,@Param("collectId")int collectId,@Param("userId")int userId);

    /**
     * 删除一篇删除的文章,无论任何收藏夹,注意这个是文章作者删除的,注意权限
     */
    public void delArticleByArticleId(@Param("articleId")int articleId);


    /**
     * 删除一个收藏夹里所包含的文章
     */
    public void delArticlesByCollect(@Param("collectId")int collectId,@Param("userId")int userId);


    /**
     * 根据收藏夹id来获取这个收藏夹所包含的文章数目
     */
    public int countArticlesByCollectId(@Param("collectId")int collectId);


    /**
     * 判断这个收藏夹是否包含这篇文章,返回值要么是0要么是1
     */
    public int isContainArticle(@Param("articleId")int articleId,@Param("collectId")int collectId,@Param("userId") int userId);

    /**
     * 根据收藏夹id来获取这个收藏夹所包含的文章id
     */
    public Integer[] getArticleIdsByCollect(@Param("collectId")int collectId,@Param("userId")int userId);


    /**
     * 返回一篇文章被收藏的次数
     */
    public int getSumByArticleId(@Param("articleId")int articleId);


    /**
     * 返回一个用户包含的所有文章被收藏的次数之和
     */
    public int getSumByAuthorId(@Param("authorId")int authorId);


}
