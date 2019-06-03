package lw.pers.blog.dao;

import lw.pers.blog.model.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentDao {
    /**
     *返回一篇文章的所有评论,不包含回复
     */
    List<Comment> getAllComments(@Param("articleId") int articleId);

    /**
     * 获取评论总的数目
     */
    public int getAllcommentNum();

    /**
     * 根据文章id,返回所有评论和回复数的和
     */

    int getSumByArticleId(@Param("articleId") int articleId);

    /**
     * 根据用户id,返回这个用户的所有文章包含的所有评论和回复数之和
     */
    int countCommentsByAuthorId(@Param("authorId")int authorId);


    /**
     *根据一个评论id,返回对应的所有回复
     */
    List<Comment> getReplies(@Param("pId")int pId);

    /**
     * 根据一个评论id,返回对应得所有回复id
     */
    List<Integer> getReplyIdsByPid(@Param("pId")int pId);

    /**
     删除一个回复或者评论,如果是删除评论,则不会删除相关的回复
     */
    void delOne(@Param("id") int id,@Param("fromUid") int fromUid);

    /**
     * 删除一个评论下的所有回复
     */
    void delRepliesBypId(@Param("pId")int pId);

    /**
     * 根据id和fromUid,返回一个Comment
     */
    Comment getComment(@Param("id") int id,@Param("fromUid") int fromUid);

    /**
     * 添加一条评论
     */
    void insertComment(@Param("comment")Comment comment);

    /**
     * 添加一条回复
     */
    void insertReply(@Param("comment")Comment comment);

    /**
     * 根据文章id来删除相关得comment
     */
    void delCommentsByArticleId(@Param("articleId")int articleId);

}

