package lw.pers.blog.service;

import lw.pers.blog.model.Article;

import java.util.List;
import java.util.Map;

public interface DraftService {

    /**
     * 获取草稿表列表
     */
    public Map<String,Object> getDraftList(int userId, int pageNum, int pageSize);

    /**
     * 删除一条草稿
     */
    public void delOne(int id, int userId);

    /**
     * 删除几篇草稿
     */
    public void delMany(int[] ids,int userId);

    /**
     * 获取一篇草稿
     */
    public  Map<String,Object> getOne(int id,int userId);

    /**
     * 判断某个用户是否有这篇草稿
     */
    public boolean userHasDraft(int draftId,int userId);

    /**
     * 保存一篇草稿
     */
    public void saveDraft(Article article);

    /**
     * 修改一篇草稿
     */
    public void updateDraft(Article article);

    /**
     * 将草稿发布成文章
     */
    public void pubDraft(Article article);
}
