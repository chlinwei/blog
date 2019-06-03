package lw.pers.blog.dao;

import lw.pers.blog.model.CustomType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomTypeDao {
    /**
     * 插入一个文章分类
     */
    public void insertCustomType(@Param("customType") CustomType customType);

    /**
     * 根据id删除一个文章分类
     */
    public void delCustomTypeById(@Param("id") int id,@Param("userId") int userId);

    /**
     * 修改一个分类
     */
    public void updateCustomType(@Param("customType") CustomType customType);


    /**
     * 根据用户id返回所有该用户所有文章分类
     */
    public List<CustomType> getAll(int userId);

    /**
     * 根据id返回实体类
     */
    public CustomType getCutomTypeById(int id);



}
