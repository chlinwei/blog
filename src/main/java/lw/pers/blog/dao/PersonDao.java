package lw.pers.blog.dao;

import lw.pers.blog.model.Person;
import org.apache.ibatis.annotations.Param;

public interface PersonDao {
    public Person getOne(@Param("id")int id);
}
