package lw.pers.blog.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lw.pers.blog.dao.ArticleDao;
import lw.pers.blog.dao.CollectDao;
import lw.pers.blog.dao.CustomTypeDao;
import lw.pers.blog.dao.UserDao;
import lw.pers.blog.model.Article;
import lw.pers.blog.model.Collect;
import lw.pers.blog.model.CustomType;
import lw.pers.blog.model.User;
import lw.pers.blog.service.CollectService;
import lw.pers.blog.util.SummaryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CollectServiceImpl implements CollectService {
    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private CollectDao collectDao;

    @Autowired
    private CustomTypeDao customTypeDao;

    @Autowired
    private UserDao userDao;

    @Override
    public void insertCollect(Collect collect) {
        collectDao.insertCollect(collect);
    }

    @Override
    @Transactional
    public void delCollect(int collectId,int userId) {
        //1.先删除收藏夹
        collectDao.delCollect(collectId,userId);
        //2.再删除收藏夹里包含的文章
        collectDao.delArticlesByCollect(collectId,userId);
    }

    @Override
    @Transactional
    public List<Map<String,Object>> getCollects(int userId,int articleId) {
        //1.获取该用户的所有收藏夹
        List<Map<String,Object>> list = new ArrayList<>();
        List<Collect> collects = collectDao.getCollects(userId);
        for(Collect collect : collects){
            Map<String, Object> map = new HashMap<>();
            map.put("id",collect.getId());
            map.put("name",collect.getName());
            //2.获取每个收藏夹目前所包含的文章数目
            map.put("num",collectDao.countArticlesByCollectId(collect.getId()));
            //3.判断每个收藏夹是否包含该文章id
            int num = collectDao.isContainArticle(articleId,collect.getId(),userId);
            if(num==1){
                map.put("isCollected",1);
            }else{
                map.put("isCollected",0);
            }
            list.add(map);
        }
        return list;
    }

    @Override
    @Transactional
    public void collectArticle(int articleId,int authorId, int[] collectIds,int userId) {
        for(int i=0;i<collectIds.length;i++) {
            //1.先查找当前收藏夹是否已经收藏过
            int flag = collectDao.isContainArticle(articleId,collectIds[i],userId);
            System.out.println("flag:"+flag);
            if (flag == 0) {
                //表示没有收藏,则收藏
                collectDao.collectArticle(articleId,authorId, collectIds[i],userId);
            }else{
                //如果已经收藏了,则取消收藏
                collectDao.undoCollectArticle(articleId,collectIds[i],userId);
            }
        }
    }

    @Override
    public void undoCollectArticle(int articleId, int collectId, int userId) {
        collectDao.undoCollectArticle(articleId,collectId,userId);
    }

    @Override
    @Transactional
    public boolean articleIsCollected(int articleId, int userId) {
        //1.先返回这个用户的所有收藏夹
        List<Collect> collects = collectDao.getCollects(userId);
        if(collects.size()==0){
            return false;
        }
        //2.到article_collect中查找
        for(Collect collect:collects){
            int flag = collectDao.isContainArticle(articleId,collect.getId(),userId);
            if(flag!=0){
                //只要从用户的任何一个收藏夹中找到这篇文章,就表示用户已收藏了这篇文章
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Map<String,Object>> findCollects(int userId) {
        List<Collect> collects = collectDao.getCollects(userId);
        ArrayList<Map<String,Object>> list = new ArrayList<>();
        for(Collect collect : collects){
            Map<String,Object> map  =new HashMap<>();
            map.put("id",collect.getId());
            map.put("name",collect.getName());
            map.put("isDefault",collect.getIsDefault());
            //2.获取每个收藏夹目前所包含的文章数目
            map.put("num",collectDao.countArticlesByCollectId(collect.getId()));
            list.add(map);
        }
        return list;
    }

    @Override
    @Transactional
    public Map<String,Object> getArticlesByCollect(int collectId, int userId,int pageNum,int pageSize) {
        //1.查询这个收藏夹所包含的文章id
        Map<String,Object> returnMap = new HashMap<>();
        Page page = PageHelper.startPage(pageNum,pageSize);
        collectDao.getArticleIdsByCollect(collectId, userId);
        PageInfo<Integer> pageInfo = new PageInfo<Integer>(page);
        //获取分页信息
        returnMap.put("pageNum",pageInfo.getPageNum());
        returnMap.put("pageSize",pageInfo.getPageSize());
        returnMap.put("pages",pageInfo.getPages());
        returnMap.put("total",pageInfo.getTotal());
        List<Integer> articleIds = pageInfo.getList();
        List<Map<String,Object>> list = new ArrayList<>();
        for(int articleId : articleIds){
            Map<String, Object> map = new HashMap<>();
            Article article = articleDao.getArticleById(articleId);
            //文章信息
            map.put("id",article.getId());
            map.put("createTime",article.getCreateTime());
            //判断是否有摘要,有的话使用摘要,没有自动生成摘要
            if(article.getSummary()==null){
                map.put("brief", SummaryUtil.toSummary(article.getArticleContent()));
            }else{
                map.put("brief",article.getSummary());
            }
            map.put("articleTitle",article.getArticleTitle());

            //用户信息
            int uId = article.getUserId();
            User user = userDao.getUserById(uId);
            map.put("authorId",uId);
            map.put("author",user.getUserName());

            //文章分类
            Integer customTypeId = article.getCustomTypeId();
            CustomType customType = customTypeDao.getCutomTypeById(customTypeId);
            map.put("customTypeId",customTypeId);
            map.put("customTypeName",customType.getName());

            list.add(map);
        }
        returnMap.put("list",list);
        return returnMap;
    }

    @Override
    public Collect getCollectById(int id, int userId) {
        return collectDao.getCollectById(id,userId);
    }

    @Override
    public Collect getDefaultCollect(int userId) {
        return collectDao.getDefaultCollect(userId);
    }

    @Override
    public Map<String, Object> getArticlesByDefaultCollect(int userId,int pageNum,int pageSize) {
        //1.获取默认收藏夹
        Collect collect = collectDao.getDefaultCollect(userId);
        if(collect!=null){
            return getArticlesByCollect(collect.getId(),userId,pageNum,pageSize);
        }else{
            return null;
        }
    }

    @Override
    public void updateCollect(Collect collect) {
        collectDao.updateCollect(collect);
    }
}
