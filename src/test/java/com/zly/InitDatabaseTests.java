package com.zly;

import com.zly.dao.QuestionDAO;
import com.zly.dao.UserDAO;
import com.zly.model.Question;
import com.zly.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WendaApplication.class)
@Sql("/init-schema.sql")
public class InitDatabaseTests {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    UserDAO userDAO;
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    QuestionDAO questionDAO;

    @Test
	public void initDatabase() {
        Random random = new Random();
        for (int i = 0;i < 11;++i) {
            User user = new User();
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",random.nextInt(1000)));
            user.setName(String.format("USER%d",i));
            user.setPassword("");
            user.setSalt("");
            userDAO.addUser(user);//往数据库的表中加入一条数据

            user.setPassword("xx");
            userDAO.updatePassword(user);

            Question question = new Question();
            question.setCommentCount(i);
            Date date = new Date();
            date.setTime(date.getTime()+1000*3600*i);
            question.setCreatedDate(date);
            question.setUserId(i+1);
            question.setTitle(String.format("TITLE{%d}",i));
            question.setContent(String.format("Balalalalalalalalala Content %d",i));

            questionDAO.addQuestion(question);
        }

        Assert.assertEquals("xx",userDAO.selectById(1).getPassword());
        userDAO.deleteById(1);
        Assert.assertNull(userDAO.selectById(1));
        questionDAO.selectLatestQuestions(0,0,5);
	}

}
