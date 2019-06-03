package lw.pers.blog.dao;

import lw.pers.blog.model.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章doa
 */
public interface ArticleDao {
    /**
     * 插入一篇文章
     */
    public void insertArticle(Article article);

    /**
     * 获取所有文章数目
     */
    public int getAllArticleNum();

    /**
     * 根据文章id,获取一篇文章
     */
    public Article getArticleById(int id);

    /**
     * 根据用户id和文章id来获取一篇文章
     */
    public Article getArticleByUserIdAndId(@Param("id") int id,@Param("userId") int userId);

    /**
     * 根据用户id和文章id,返回这个用户的下一篇文章id
     */
    public Article getNextArticleId(@Param("userId") int userId,@Param("id")int id);

    /**
     * 根据用户id和文章id,返回这个用户的上一篇文章id
     */
    public Article getLastArticleId(@Param("userId") int userId,@Param("id")int id);


    /**
     * 删除一篇文章
     */
    public void delArticleById(@Param("id") int id,@Param("userId") int userId);

    /**
     * 修改一篇文章
     */
    public void updateArticle(Article article);

    /**
     * 根据文章分类和类型返回文章列表
     */
    public List<Article> getArticlesByUserId(@Param("userId") int userId,@Param("customTypeId") Integer customTypeId,@Param("articleType") String articleType);

    /**
     * @param userId:用户id
     * @param id:文章id
     * @param customTypeId:文章分类id
     */
    public void updateCustomType(@Param("userId")int userId,@Param("id")int id,@Param("customTypeId")int customTypeId);


    /**
     * 获取某个用户最新发布的文章
     */
    public List<Article>  getlatestArticlesInSomeone(@Param("userId")int userId,@Param("num")int num);

    /**
     * 获取最新发布的文章
     */
    public List<Article>  getlatestArticles(@Param("num")int num);

    /**
     * 获取某个用户的文章总数
     */
    public int countArticles(@Param("userId")int userId);

    /**
     * 获取所有文章列表
     */
    public List<Article> getAllArticle();
}

