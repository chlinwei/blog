package lw.pers.blog.service;

import lw.pers.blog.model.Like;

public interface LikeService {
    /**
     * 查看看谋篇文章或者评论的点赞次数
     */
    public int getLikes(int typeId,int type);

    /**
     * 返回一个实体类
     */
    public boolean isLiked(int typeId,int type, int userId);


    /**
     * 进行点赞
     */
    public void insertLike(int typeId,int ownerId,int type, int userId);


    /**
     * 取消点赞
     */
    public void undoLike(int typeId,int type,int userId);

    public void delLikesByArticleId(int typeId,int type);

    /**
     * 返回一个用户的所有点赞之和
     */
    public int getAllLikesByOwnerId(int ownerId);

}



