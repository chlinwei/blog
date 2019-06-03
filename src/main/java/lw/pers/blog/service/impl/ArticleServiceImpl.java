package lw.pers.blog.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lw.pers.blog.constant.LikeType;
import lw.pers.blog.dao.*;
import lw.pers.blog.exception.MyException;
import lw.pers.blog.model.Article;
import lw.pers.blog.model.CustomType;
import lw.pers.blog.model.User;
import lw.pers.blog.service.ArticleService;
import lw.pers.blog.service.CollectService;
import lw.pers.blog.service.CommentService;
import lw.pers.blog.service.LikeService;
import lw.pers.blog.util.AvatarlUtil;
import lw.pers.blog.util.SummaryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private CustomTypeDao customTypeDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CollectDao collectDao;

    @Autowired
    private CollectService collectService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private CommentService commentService;

    @Value("${ftp.host}")
    private String ftpHost;

    @Override
    public void saveArticle(Article article) {
        articleDao.insertArticle(article);
    }

    @Override
    @Transactional
    public Map getArticleById(int id,Integer myId) throws InvocationTargetException, IllegalAccessException {
        Map<String,Object> map = new HashMap<>();
        //本篇
        Article article = articleDao.getArticleById(id);
        map.put("articleTitle",article.getArticleTitle());
        map.put("id",article.getId());
        map.put("articleContent",article.getArticleContent());
        map.put("articleTags",article.getArticleTags());
        map.put("createTime",article.getCreateTime());
        map.put("articleType",article.getArticleType());
        map.put("summary",article.getSummary());
        map.put("updateTime",article.getUpdateTime());

        //文章分类
        CustomType customType = customTypeDao.getCutomTypeById(article.getCustomTypeId());
        if(customType!=null) {
            map.put("customTypeId", customType.getId());
            map.put("customTypeName", customType.getName());
        }
        int userId = article.getUserId();
        //上一篇文章
        Article lastArticle = articleDao.getLastArticleId(userId, id);
        if(lastArticle!=null) {
            map.put("lastArticleId", lastArticle.getId());
            map.put("lastArticleTitle", lastArticle.getArticleTitle());
        }
        //下一篇文章
        Article nextArticle = articleDao.getNextArticleId(userId, id);
        if(nextArticle!=null) {
            map.put("nextArticleId", nextArticle.getId());
            map.put("nextArticleTitle", nextArticle.getArticleTitle());
        }
        //用户
        User user = userDao.getUserById(userId);
        map.put("userId",userId);
        map.put("userName",user.getUserName());
        String imgUri = user.getAvatarImgUri();
        String avatarUrl = AvatarlUtil.getUrl(ftpHost,imgUri);
        map.put("avatarUrl",avatarUrl);

        //点赞次数
        int likes = likeService.getLikes(id, LikeType.article);
        map.put("likes",likes);

        //是否点赞,判断是否登录
        if(myId!=null) {
            boolean liked = likeService.isLiked(id, LikeType.article, myId);
            if (liked) {
                //表示点赞了:
                map.put("isLiked", 1);
            } else {
                map.put("isLiked", 0);
            }
        }

        //是否收藏,有用户登录才会判断是否已经收藏
        if(myId!=null){
            boolean b =  collectService.articleIsCollected(id,myId);
            if(b){
                //表示收藏了
                map.put("isCollected",1);
            }else{
                map.put("isCollected",0);
            }
        }
        return map;

    }

    @Override
    public boolean articleIsExist(int id) {
        Article article = articleDao.getArticleById(id);
        return article != null;
    }

    @Override
    @Transactional
    public void delArticle(int id, int userId) {
        //首先判断这个用户是否有这篇文章
        Article article = articleDao.getArticleById(id);
        if(article.getUserId()==userId){
            //1.先删除article表的数据
            articleDao.delArticleById(id,userId);
            //2.再删除article_collect表的数据
            collectDao.delArticleByArticleId(id);
            //3.在删除like表里的相关点赞
            likeService.delLikesByArticleId(id,LikeType.article);
            //4.在删除评论相关得数据
            commentService.delCommentsByArticleId(id);
        }
    }

    @Override
    @Transactional
    public void delArticles(int[] articleIds, int userId) {
        for(int i=0;i<articleIds.length;i++){
            Article article = articleDao.getArticleById(articleIds[i]);
            //判断用户是否包含这篇文章
            if(article.getUserId()==userId){
                delArticle(articleIds[i],userId);
            }
        }
    }

    /**
     * 获取文件修改界面的文章数据
     */
    @Override
    @Transactional
    public Map<String,Object> getUpdatePageArticle(int id, Integer myId) {
        Article article = articleDao.getArticleById(id);
        CustomType customType = customTypeDao.getCutomTypeById(article.getCustomTypeId());

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

    @Override
    public void updateArticle(Article article) {
        articleDao.updateArticle(article);
    }

    @Override

    public Map getArticlesInManager(int userId,int pageNum,int pageSize,Integer customTypeId,String articleType){
        Map<String,Object> returnValue = new HashMap<>();
        Page page = PageHelper.startPage(pageNum,pageSize);
        articleDao.getArticlesByUserId(userId,customTypeId,articleType);
        PageInfo<Article> pageInfo = new PageInfo<Article>(page);
        List<Article> articles = pageInfo.getList();
        //分页数据
        returnValue.put("pageNum",pageInfo.getPageNum());
        returnValue.put("pageSize",pageInfo.getPageSize());
        returnValue.put("pages",pageInfo.getPages());
        returnValue.put("total",pageInfo.getTotal());
        List<Map> list = new ArrayList<>();
        for(Article article : articles){
            HashMap<String, Object> map = new HashMap<>();

            //文章数据
            map.put("id",article.getId());
            map.put("articleTitle",article.getArticleTitle());
            map.put("articleType",article.getArticleType());
            map.put("customTypeId",article.getCustomTypeId());
            map.put("createTime",article.getCreateTime());
            list.add(map);
        }
        returnValue.put("list",list);
        return returnValue;
    }

    @Override
    @Transactional
    public void updateArticlesCustomType(int userId, int[] articleIds, int customTypeId) {
        //1.先判断该文章分类是否存在
        CustomType customType = customTypeDao.getCutomTypeById(customTypeId);
        for(int i=0;i<articleIds.length;i++){
            if(customType==null){
                throw new MyException("此文章分类不存在");
            }else{
                //2.修改
                articleDao.updateCustomType(userId,articleIds[i],customTypeId);
            }
        }
    }

    @Override
    @Transactional
    public Map<String,Object> getArticlesInUserBlog(Integer customTypeId, int userId,int pageNum,int pageSize,Integer myId) {
        Map<String,Object> returnValue = new HashMap<>();
        List list = new ArrayList<Map<String,Object>>();
        Page page = PageHelper.startPage(pageNum,pageSize);
        articleDao.getArticlesByUserId(userId, customTypeId, null);
        PageInfo<Article> pageInfo = new PageInfo<Article>(page);
        List<Article> articles = pageInfo.getList();
        returnValue.put("pageNum",pageInfo.getPageNum());
        returnValue.put("pageSize",pageInfo.getPageSize());
        returnValue.put("pages",pageInfo.getPages());
        returnValue.put("total",pageInfo.getTotal());
        //用户信息
        User user = userDao.getUserById(userId);
        returnValue.put("authorId",user.getId());
        returnValue.put("author",user.getUserName());
        returnValue.put("avatarUrl",AvatarlUtil.getUrl(ftpHost,user.getAvatarImgUri()));

        for(Article article : articles){
            HashMap<String, Object> map = new HashMap<>();
            //文章
            map.put("articleId",article.getId());
            map.put("articleTitle",article.getArticleTitle());
            map.put("createTime",article.getCreateTime());
            map.put("articleType",article.getArticleType());


            //文章分类
            Integer customTypeId1 = article.getCustomTypeId();
            CustomType customType = customTypeDao.getCutomTypeById(customTypeId1);
            map.put("customTypeId",customTypeId1);
            map.put("customTypeName",customType.getName());

            //文章摘要
            if(article.getSummary()==null){
                map.put("articleBrief", SummaryUtil.toSummary(article.getArticleContent()));
            }else{
                map.put("articleBrief", article.getSummary());
            }

            //评论次数
            int commentNum = commentDao.getSumByArticleId(article.getId());
            map.put("commentNum",commentNum);

            //收藏次数
            int collectNum  = collectDao.getSumByArticleId(article.getId());
            map.put("collectNum",collectNum);

            //点赞次数
            int likes = likeService.getLikes(article.getId(), LikeType.article);
            map.put("likes",likes);
            list.add(map);

            //是否点赞
            if(myId!=null){
                //登录了
                if(likeService.isLiked(article.getId(),LikeType.article,myId)) {
                    map.put("isLiked", 1);
                }else{
                    map.put("isLiked", 0);
                }
            }else{
                map.put("isLiked", 0);
            }
        }
        returnValue.put("list",list);
        return returnValue;
    }


    @Override
    public List<Map<String,Object>> getLatestArticlesInSomeone(int userId, int num) {
        List<Article> articles = articleDao.getlatestArticlesInSomeone(userId, num);
        List<Map<String,Object>> list = new ArrayList<>();
        for(Article article:articles){
            Map map = new HashMap<String,Object>();
            map.put("articleId",article.getId());
            map.put("articleTitle",article.getArticleTitle());
            list.add(map);
        }
        return list;
    }


    @Override
    public List<Map<String, Object>> getLatestArticles(int num) {
        List<Article> articles = articleDao.getlatestArticles(num);
        List<Map<String,Object>> list = new ArrayList<>();
        for(Article article:articles){
            Map map = new HashMap<String,Object>();
            map.put("articleId",article.getId());
            map.put("articleTitle",article.getArticleTitle());
            list.add(map);
        }
        return list;
    }

    @Override
    public int countArticle(int userId) {
        return articleDao.countArticles(userId);
    }

    @Override
    @Transactional
    public Map<String, Object> getAllArticle(Integer myId,int pageNum,int pageSize) {
        Map returnValue = new HashMap<String,Object>();
        List<Map<String,Object>> list = new ArrayList<>();
        Page page = PageHelper.startPage(pageNum,pageSize);
        articleDao.getAllArticle();
        PageInfo<Article> pageInfo = new PageInfo<Article>(page);
        List<Article> articles = pageInfo.getList();
        returnValue.put("pageNum",pageInfo.getPageNum());
        returnValue.put("pageSize",pageInfo.getPageSize());
        returnValue.put("pages",pageInfo.getPages());
        returnValue.put("total",pageInfo.getTotal());

        for(Article article : articles){
            HashMap<String, Object> map = new HashMap<>();
            //文章
            map.put("articleId",article.getId());
            map.put("articleTitle",article.getArticleTitle());
            map.put("createTime",article.getCreateTime());
            map.put("articleType",article.getArticleType());

            //用户信息
            User user = userDao.getUserById(article.getUserId());
            map.put("authorId",user.getId());
            map.put("author",user.getUserName());
            map.put("avatarUrl",AvatarlUtil.getUrl(ftpHost,user.getAvatarImgUri()));

            //文章分类
            Integer customTypeId1 = article.getCustomTypeId();
            CustomType customType = customTypeDao.getCutomTypeById(customTypeId1);
            map.put("customTypeId",customTypeId1);
            map.put("customTypeName",customType.getName());

            //文章摘要
            if(article.getSummary()==null){
                map.put("articleBrief", SummaryUtil.toSummary(article.getArticleContent()));
            }else{
                map.put("articleBrief", article.getSummary());
            }

            //评论次数
            int commentNum = commentDao.getSumByArticleId(article.getId());
            map.put("commentNum",commentNum);

            //收藏次数
            int collectNum  = collectDao.getSumByArticleId(article.getId());
            map.put("collectNum",collectNum);

            //点赞次数
            int likes = likeService.getLikes(article.getId(), LikeType.article);
            map.put("likes",likes);
            list.add(map);

            //是否点赞
            if(myId!=null){
                //登录了
                if(likeService.isLiked(article.getId(),LikeType.article,myId)) {
                    map.put("isLiked", 1);
                }else{
                    map.put("isLiked", 0);
                }
            }else{
                map.put("isLiked", 0);
            }
        }
        returnValue.put("list",list);
        return returnValue;
    }

    @Override
    public int getAllArticleNum() {
        return articleDao.getAllArticleNum();
    }

    @Override
    public boolean userHasArticle(int articleId, int userId) {
        Article article = articleDao.getArticleByUserIdAndId(articleId,userId);
        return article!=null;
    }
}

