package lw.pers.blog.dao;

import lw.pers.blog.model.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 草稿表和文章表公用一个model
 */
public interface DraftDao {
    /**
     * 获取草稿表列表
     */
    public List<Article> getDraftList(@Param("userId") int userId);

    /**
     * 删除一篇草稿
     */
    public void delOne(@Param("id") int id,@Param("userId") int userId);

    /**
     * 某个用户获取一篇草稿
     */
    public Article getOne(@Param("id")int id,@Param("userId")int userId);

    /**
     * 保存一篇草稿
     */
    public void saveDraft(@Param("article") Article article);

    /**
     * 修改一篇草稿
     */
    public void updateDraft(@Param("article") Article article);

}
