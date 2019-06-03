package lw.pers.blog.service;

import lw.pers.blog.model.Comment;

import java.util.List;
import java.util.Map;

public interface CommentService {
    /**
     * 获取所有评论,不包含回复
     */
    public Map getAllComments(int articleId,int pageNum,int pageSize);



    /**
     * 删除一个回复
     */
    public void delReply(int id,int fromUid);

    /**
     * 删除一个评论,及其相关回复
     */
    public void delComment(int id,int fromUid);

    /**
     * 插入一条评论
     */
    public void insertComment(Comment comment);

    /**
     * 插入一条回复
     */
    public void insertReply(Comment comment);


    /**
     * 判断一条comment是否属于该用户
     */
    public boolean userHasComment(int commentId,int fromUid);


    /**
     * 根据文章id来删除相关的comment
     */
    public void delCommentsByArticleId(int articleId);


    /**
     * 根据用户id返回这个用户所有文章包含的所有评论和回复数之和
     */
    public int countCommentsByAuthorId(int authorId);

    /**
     * 获取总的评论数目
     */
    public int getAllcommentNum();
}
