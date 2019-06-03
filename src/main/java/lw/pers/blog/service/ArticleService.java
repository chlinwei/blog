package lw.pers.blog.service;

import lw.pers.blog.model.Article;
import lw.pers.blog.model.SessionUserInfo;
import lw.pers.blog.vo.ArticleShow;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public interface ArticleService {
    public void saveArticle(Article article);

    public Map getArticleById(int id, Integer myId) throws InvocationTargetException, IllegalAccessException;

    public boolean articleIsExist(int id);

    //判断某个是否含有某篇文章
    public boolean userHasArticle(int articleId,int userId);

    /**
     * 删除一篇文章
     * @param id:文章id
     * @param userId:用户id
     */
    public void delArticle(int id,int userId);

    /**
     * 删除多个文章
     * @param articleIds:文章id列表
     * @param userId:用户id
     */
    public void delArticles(int[] articleIds,int userId);

    public Map<String,Object> getUpdatePageArticle(int id,Integer myId);

    public void updateArticle(Article article);

    /**
     * 返回文章管理界面的文章list
     */
    public Map getArticlesInManager(int userId,int pageNum,int pageSize,Integer customTypeId,String articleType);

    /**
     *
     * @param userId:用户id
     * @param articleIds :文章id数组
     * @param customTypeId:文章分类的id
     */
    public void updateArticlesCustomType(int userId,int[] articleIds,int customTypeId);


    /**
     * 返回/userBlog/1页面的文章列表
     */
    public Map<String,Object> getArticlesInUserBlog(Integer customTypeId,int userId,int pageNum,int pageSize,Integer myId);


    /**
     * 获取某个用户最新发布的文章
     */
    public List<Map<String,Object>> getLatestArticlesInSomeone(int userId,int num);

    /**
     * 获取最新发布的文章
     */
    public List<Map<String,Object>> getLatestArticles(int num);


    /**
     * 获取某个用户的文章总数
     */

    public int countArticle(int userId);

    /**
     * 获取所有的文章
     */
    public Map<String,Object> getAllArticle(Integer myId,int pageNum,int pageSize);

    /**
     * 获取所有文章数目
     */
    public int getAllArticleNum();
}
