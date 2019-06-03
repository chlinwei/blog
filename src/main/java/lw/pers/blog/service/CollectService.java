package lw.pers.blog.service;

import lw.pers.blog.model.Collect;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CollectService {
    /**
     * 新建一个收藏夹
     */
    public void insertCollect(Collect collect);

    /**
     *修改一个收藏夹
     */
    public void updateCollect(Collect collect);

    /**
     * 根据收藏夹id返回指定收藏夹
     */
    public Collect getCollectById(int id,int userId);

    /**
     * 获取默认收藏夹
     */
    public Collect getDefaultCollect(int userId);

    /**
     * 删除一个收藏夹
     */
    public void delCollect(int collectId,int userId);

    /**
     * 返回收藏夹列表,articleId是用来判断每个收藏夹是否包含此文章
     */
    public List<Map<String,Object>> getCollects(int userId,int articleId);

    /**
     * 返回收藏夹列表
     */
    public List<Map<String,Object>> findCollects(int userId);

    /**
     * 多个收藏夹收藏一篇文章,userId用来判断输入的收藏夹是否属于该用户
     */
    public void collectArticle(int articleId,int authorId,int[] collectIds,int userId);

    /**
     * 取消收藏一篇文章
     */
    public void undoCollectArticle(int articleId,int collectId,int userId);

    /**
     * 判断某人是否收藏过这篇文章
     */
    boolean articleIsCollected(int articleId, int userId);

    /**
     * 返回一个收藏夹里包含的文章
     */
    Map<String,Object> getArticlesByCollect(int collectId,int userId,int pageNum,int pageSize);

    /**
     * 返回默认收藏夹里的文章
     */
    Map<String,Object> getArticlesByDefaultCollect(int userId,int pageNum,int pageSize);


}
