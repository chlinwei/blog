package lw.pers.blog.service.impl;

import lw.pers.blog.dao.CustomTypeDao;
import lw.pers.blog.exception.MyException;
import lw.pers.blog.exception.ResponseMessageUtil;
import lw.pers.blog.model.CustomType;
import lw.pers.blog.service.CustomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomTypeServiceImpl implements CustomTypeService{
    @Autowired
    private CustomTypeDao customTypeDao;

    @Override
    public void delCustomTypeById(int id,int userId) {
        customTypeDao.delCustomTypeById(id,userId);
    }

    @Override
    public List<CustomType> getAll(int userId) {
        return customTypeDao.getAll(userId);
    }

    @Override
    @Transactional
    public void saveCustomTypes(List<CustomType> customTypes) {
        if(customTypes.size()>=20){
            throw new MyException("文章分类不能超过20个");
        }
        for(CustomType customType:customTypes){
            if(customType.getId()==0){
                //1.这些是需要插入的文章分类
                customTypeDao.insertCustomType(customType);
            }else{
                //2.这些是需要修改的文章分类
                customTypeDao.updateCustomType(customType);
            }
        }
    }
}

