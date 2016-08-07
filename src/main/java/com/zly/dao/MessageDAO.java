package com.zly.dao;

import com.zly.model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by ly on 2016/8/4.
 */
@Mapper
public interface MessageDAO {
    String TABLE_NAME = "message";
    String INSERT_FIELDS = " from_id,to_id, content, created_date, conversation_id, has_read ";
    String SELECT_FIELDS = " id,"+INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME , "(", INSERT_FIELDS,
            ") values (#{fromId},#{toId},#{content},#{createdDate},#{conversationId},#{hasRead})"})
        //上面的values后面的值,从下面的参数中来
    int addMessage(Message message);   //往表中插入一条数据

    @Select({"select ",SELECT_FIELDS," from ",TABLE_NAME,
            " where conversation_id=#{conversationId} order by created_date desc limit #{offset},#{limit}"})
    List<Message> getConversationDetail(@Param("conversationId") String conversationId,
                                       @Param("offset") int offset,
                                       @Param("limit") int limit);

    @Select({"select ", INSERT_FIELDS, " ,count(id) as id from ( select * from ", TABLE_NAME,
            " where from_id=#{userId} or to_id=#{userId} order by id desc) tt ",
            " group by conversation_id  order by created_date desc limit #{offset}, #{limit}"})
    List<Message> getConversationList(@Param("userId") int userId,
                                       @Param("offset") int offset,
                                       @Param("limit") int limit);

    @Select({"select count(id) from ", TABLE_NAME,
            " where has_read=0 and to_id=#{userId} and conversation_id=#{conversationId}"})
    int getConversationUnreadCount(@Param("userId") int userId, @Param("conversationId") String conversationId);
}
