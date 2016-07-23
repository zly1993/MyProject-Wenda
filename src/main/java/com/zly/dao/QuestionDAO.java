package com.zly.dao;

import com.zly.model.Question;
import com.zly.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by ly on 2016/7/23.
 * 加入Mapper注解指的是这是一个与mybatis访问关联的一个dao
 */
@Mapper
public interface QuestionDAO {
    String TABLE_NAME = "question";
    String INSERT_FIELDS = " title, content, created_date, user_id, comment_count ";
    String SELECT_FIELDS = " id,"+INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{title},#{content},#{createdDate},#{userId},#{commentCount})"})
        //上面的values后面的值，从下面的user中来
    int addQuestion(Question question);   //往表中插入一条数据

    List<Question> selectLatestQuestions(@Param("userId") int userId, @Param("offset") int offset,
                                         @Param("limit") int limit);
}
