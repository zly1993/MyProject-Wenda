package com.zly.dao;

import com.zly.model.User;
import org.apache.ibatis.annotations.*;


import static com.zly.dao.UserDAO.SELECT_FIELDS;
import static com.zly.dao.UserDAO.TABLE_NAME;

/**
 * Created by ly on 2016/7/23.
 * 加入Mapper注解指的是这是一个与mybatis访问关联的一个dao
 */
@Mapper
public interface UserDAO {
    String TABLE_NAME = "user";
    String INSERT_FIELDS = " name, password, salt, head_url ";
    String SELECT_FIELDS = " id, name, password, salt, head_url";

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{name},#{password},#{salt},#{headUrl})"})
        //上面的values后面的值，从下面的user中来
    int addUser(User user);   //往表中插入一条数据，添加一个用户

    @Select({"select ",SELECT_FIELDS,"from",TABLE_NAME , "where id=#{id}"})
    User selectById(int id);

    @Update({"update ",TABLE_NAME," set password=#{password}"})
    void updatePassword(User user);

    @Delete({"delete from ",TABLE_NAME," where id=#{id}"})
    void deleteById(int id);

    @Select({"select ",SELECT_FIELDS,"from",TABLE_NAME , "where name=#{name}"})
    User selectByName(String name);

}
