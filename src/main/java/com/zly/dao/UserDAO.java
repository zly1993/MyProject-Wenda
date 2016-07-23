package com.zly.dao;

import com.zly.model.User;
import org.apache.ibatis.annotations.*;

/**
 * Created by ly on 2016/7/23.
 * 加入Mapper注解指的是这是一个与mybatis访问关联的一个dao
 */
@Mapper
public interface UserDAO {
    String TABLE_NAME = "user";
    String INSET_FIELDS = " name, password, salt, head_url ";
    String SELECT_FIELDS = " id, name, password, salt, head_url";

    @Insert({"insert into ", TABLE_NAME, "(", INSET_FIELDS,
            ") values (#{name},#{password},#{salt},#{headUrl})"})
        //上面的values后面的值，从下面的user中来
    int addUser(User user);
}
