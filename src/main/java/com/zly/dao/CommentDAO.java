package com.zly.dao;

import com.zly.model.Comment;
import com.zly.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by ly on 2016/7/23.
 * 加入Mapper注解指的是这是一个与mybatis访问关联的一个dao
 */
@Mapper
public interface CommentDAO {
    String TABLE_NAME = "comment";
    String INSERT_FIELDS = " user_id, content, created_date, entity_id, entity_type,status ";
    String SELECT_FIELDS = " id,"+INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{userId},#{content},#{createdDate},#{entityId},#{entityType},#{status})"})
        //上面的values后面的值，从下面的user中来
    int addComment(Comment comment);   //往表中插入一条数据

    @Select({"select ",SELECT_FIELDS," from ",TABLE_NAME,
            " where entity_id=#{entityId} and entity_type=#{entityType} order by created_date desc"})
    List<Comment> selectCommentByEntity(@Param("entityId") int entityId, @Param("entityType") int entityType);

    @Select({"select count(id) from ",TABLE_NAME,
            " where entity_id=#{entityId} and entity_type=#{entityType} order by created_date desc "})
    int getCommentCount(@Param("entityId") int entityId, @Param("entityType") int entityType);

    @Update({"update ", TABLE_NAME,
            " set status=#{status} where entity_id=#{entityId} and entity_type=#{entityType}"})
    int updateStatus(@Param("id") int id,@Param("status") int status);
}
