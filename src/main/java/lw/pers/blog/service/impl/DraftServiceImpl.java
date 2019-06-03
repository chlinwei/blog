package lw.pers.blog.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lw.pers.blog.dao.CustomTypeDao;
import lw.pers.blog.dao.DraftDao;
import lw.pers.blog.model.Article;
import lw.pers.blog.model.CustomType;
import lw.pers.blog.service.ArticleService;
import lw.pers.blog.service.CustomTypeService;
import lw.pers.blog.service.DraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DraftServiceImpl implements DraftService {

    @Autowired
    private DraftDao draftDao;

    @Autowired
    private CustomTypeDao customTypeService;

    @Autowired
    private ArticleService articleService;
    @Override
    public Map<String,Object> getDraftList(int userId, int pageNum, int pageSize) {
        Map returnValue = new HashMap<String,Object>();
        List list = new ArrayList<Map<String,Object>>();
        Page page = PageHelper.startPage(pageNum,pageSize);
        draftDao.getDraftList(userId);
        PageInfo<Article> pageInfo = new PageInfo<Article>(page);
        List<Article> drafts = pageInfo.getList();
        returnValue.put("pageNum",pageInfo.getPageNum());
        returnValue.put("pageSize",pageInfo.getPageSize());
        returnValue.put("pages",pageInfo.getPages());
        returnValue.put("total",pageInfo.getTotal());
        for(Article draft:drafts){
            Map map = new HashMap<String,Object>();
            map.put("id",draft.getId());
            map.put("articleTitle",draft.getArticleTitle());
            map.put("updateTime",draft.getUpdateTime());
            list.add(map);
        }
        returnValue.put("list",list);
        return returnValue;
    }

    @Override
    public void delOne(int id, int userId) {
        draftDao.delOne(id,userId);
    }


    @Override
    @Transactional
    public void delMany(int[] ids, int userId) {
        for(int id:ids){
            delOne(id,userId);
        }
    }

    @Override
    public boolean userHasDraft(int draftId, int userId) {
        Article draft = draftDao.getOne(draftId, userId);
        return draft!=null;
    }

    @Override
    public void saveDraft(Article article) {
        draftDao.saveDraft(article);
    }

    @Override
    public void updateDraft(Article article) {
        draftDao.updateDraft(article);
    }

    @Override
    @Transactional
    public void pubDraft(Article article) {
        //1.先删除草稿
        if(article.getId()!=null) {
            draftDao.delOne(article.getId(), article.getUserId());
        }
        //2.在创建文章
        articleService.saveArticle(article);
    }

    @Override
    public Map<String, Object> getOne(int id, int userId) {
        Article article = draftDao.getOne(id, userId);
        CustomType customType = null;
        if(article.getCustomTypeId()!=null) {
            customType = customTypeService.getCutomTypeById(article.getCustomTypeId());
        }
        Map<String,Object> map = new HashMap<>();
        map.put("id",article.getId());
        map.put("articleType",article.getArticleType());
        map.put("articleTitle",article.getArticleTitle());
        map.put("articleContent",article.getArticleContent());

        if(customType!=null) {
            map.put("customTypeId", article.getCustomTypeId());
            map.put("customTypeName", customType.getName());
        }else{
            map.put("customTypeId", "");
            map.put("customTypeName", "请选择");
        }
        map.put("summary",article.getSummary());
        map.put("articleTags",article.getArticleTags());
        return map;
    }
}


