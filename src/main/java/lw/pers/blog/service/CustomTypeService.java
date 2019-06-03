package lw.pers.blog.service;

import lw.pers.blog.model.CustomType;

import java.util.List;

public interface CustomTypeService {

    /**
     * 删一个
     */
    public void delCustomTypeById(int id,int userId);


    /**
     * 查
     */
    public List<CustomType> getAll(int userId);


    /**
     * 保存用户的文章分类
     */
    public void saveCustomTypes(List<CustomType> customTypes);
}
