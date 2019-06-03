package lw.pers.blog.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lw.pers.blog.constant.LikeType;
import lw.pers.blog.dao.CommentDao;
import lw.pers.blog.dao.LikeDao;
import lw.pers.blog.exception.MyException;
import lw.pers.blog.model.Comment;
import lw.pers.blog.model.SessionUserInfo;
import lw.pers.blog.model.User;
import lw.pers.blog.service.CommentService;
import lw.pers.blog.service.LikeService;
import lw.pers.blog.service.UserService;
import lw.pers.blog.util.AvatarlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private LikeService likeService;
    @Autowired
    private CommentDao commentDao;

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @Value("${ftp.host}")
    private String ftpHost;

    @Override
    @Transactional
    public Map getAllComments(int articleId,int pageNum,int pageSize) {
        SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("userInfo");
        boolean flag = false;

        Page page = PageHelper.startPage(pageNum,pageSize);
        //这里不用接受返回值,返回值会自动跑到pageInfo里
        commentDao.getAllComments(articleId);
        PageInfo<Comment> pageInfo = new PageInfo<Comment>(page);
        List<Comment> comments = pageInfo.getList();
        HashMap<String, Object> returnValue = new HashMap<>();
        returnValue.put("pageNum",pageInfo.getPageNum());
        returnValue.put("pageSize",pageInfo.getPageSize());
        returnValue.put("pages",pageInfo.getPages());
        returnValue.put("total",pageInfo.getTotal());

        List<Map> list = new ArrayList<>();
        //评论总数
        int commentNum = commentDao.getSumByArticleId(articleId);
        returnValue.put("commentNum",commentNum);

        for(Comment comment : comments){
            Map<String,Object> map = new HashMap<>();
            map.put("id",comment.getId());
            map.put("createTime",comment.getCreateTime());
            map.put("content",comment.getContent());
            map.put("floor",comment.getFloor());
            //点赞数目
            int likes = likeService.getLikes(comment.getId(), LikeType.comment);
            map.put("likes",likes);

            if(userInfo!=null){
                //判断是否已经点赞
                flag = likeService.isLiked(comment.getId(),LikeType.comment,userInfo.getId());
                if(flag){
                    map.put("isLiked",1);
                }else{
                    map.put("isLiked",0);
                }
            }
            //fromUser
            Map<String,Object> fromUserVo = new HashMap<>();
            int fromUid = comment.getFromUid();
            User fromUser = userService.findUserById(fromUid);
            fromUserVo.put("id",fromUser.getId());
            fromUserVo.put("userName",fromUser.getUserName());
            fromUserVo.put("gender",fromUser.getGender());
            fromUserVo.put("url", AvatarlUtil.getUrl(ftpHost,fromUser.getAvatarImgUri()));
            map.put("fromUserVo",fromUserVo);

            //回复
            List<Map> replyMaps = new ArrayList<>();
            List<Comment> replies = commentDao.getReplies(comment.getId());
            for(Comment comment1: replies){
                Map<String, Object> replyMap = new HashMap<>();
                replyMap.put("id",comment1.getId());
                replyMap.put("content",comment1.getContent());
                replyMap.put("createTime",comment1.getCreateTime());

                //点赞数目
                int likes1 = likeService.getLikes(comment1.getId(), LikeType.comment);
                replyMap.put("likes",likes1);

                //是否点赞
                if(userInfo!=null){
                    flag = likeService.isLiked(comment1.getId(),LikeType.comment,userInfo.getId());
                    if(flag){
                        replyMap.put("isLiked",1);
                    }else{
                        replyMap.put("isLiked",0);
                    }
                }

                //回复的fromUser
                HashMap<String, Object> replyFromUserVo = new HashMap<>();
                int fromUid1 = comment1.getFromUid();
                User replyFromUser = userService.findUserById(fromUid1);
                replyFromUserVo.put("id",replyFromUser.getId());
                replyFromUserVo.put("userName",replyFromUser.getUserName());
                replyFromUserVo.put("gender",replyFromUser.getGender());
                replyFromUserVo.put("url",AvatarlUtil.getUrl(ftpHost,replyFromUser.getAvatarImgUri()));
                replyMap.put("replyFromUserVo",replyFromUserVo);


                //回复的toUser
                HashMap<String, Object> replyToUserVo = new HashMap<>();
                int replyToUserUid = comment1.getToUid();
                User replyToUser = userService.findUserById(replyToUserUid);
                if(replyToUser!=null){
                    replyToUserVo.put("id",replyToUser.getId());
                    replyToUserVo.put("userName",replyToUser.getUserName());
                    replyMap.put("replyToUserVo",replyToUserVo);
                    replyMap.put("replyToUserVo",replyToUserVo);
                }
                //将封装好的评论放进list
                replyMaps.add(replyMap);
            }
            map.put("replyMaps",replyMaps);
            list.add(map);
        }
        returnValue.put("list",list);
        return returnValue;
    }

    @Override
    @Transactional
    public void delReply(int id, int fromUid) {
        //由于不仅仅是删除回复那么简单,所以需要先判断此回复是否属于该用户
        if(userHasComment(id,fromUid)){
            //1.删除回复本身
            commentDao.delOne(id,fromUid);
            //2.删除此回复相关的点赞
            likeService.delLikesByArticleId(id,LikeType.comment);
        }
    }

    @Override
    @Transactional
    public void delComment(int id, int fromUid) {
        //先查找是否存在此评论
        if(userHasComment(id,fromUid)){
            //存在,确认此评论是属于此用户的
            //1.先删除所有相关的回复
            //1.1删除每条回复相关得点赞数据
            List<Integer> replyIds = commentDao.getReplyIdsByPid(id);
            if(replyIds.size()!=0){
                for(Integer replyId : replyIds){
                    //删除点赞
                    likeService.delLikesByArticleId(replyId,LikeType.comment);
                }
            }
            //1.2删除所有得回复
            commentDao.delRepliesBypId(id);
            //2.删除评论
            commentDao.delOne(id,fromUid);
        }
    }

    @Override
    public void insertComment(Comment comment) {
        commentDao.insertComment(comment);
    }

    @Override
    public void insertReply(Comment comment) {
        commentDao.insertReply(comment);
    }

    @Override
    public boolean userHasComment(int commentId, int fromUid) {
        Comment comment = commentDao.getComment(commentId, fromUid);
        return comment!=null;
    }

    @Override
    public void delCommentsByArticleId(int articleId) {
        commentDao.delCommentsByArticleId(articleId);
    }

    @Override
    public int countCommentsByAuthorId(int authorId) {
        return commentDao.countCommentsByAuthorId(authorId);
    }

    @Override
    public int getAllcommentNum() {
        return commentDao.getAllcommentNum();
    }
}


