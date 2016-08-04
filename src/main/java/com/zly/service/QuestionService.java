package com.zly.service;

import com.zly.dao.QuestionDAO;
import com.zly.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * Created by ly on 2016/7/23.
 */
@Service
public class QuestionService {
    @Autowired
    QuestionDAO questionDAO;
    @Autowired
    SensitiveService sensitiveService;

    public Question selectById(int id){
        return questionDAO.selectById(id);
    }

    public int addQuestion(Question question){
        //html标签过滤
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));

        //敏感词过滤
        question.setTitle(sensitiveService.filter(question.getTitle()));
        question.setContent(sensitiveService.filter(question.getContent()));

        return questionDAO.addQuestion(question)>0 ? question.getId():0 ;
    }

    public List<Question> getLatestQuestions(int userId,int offset,int limit) {
        return questionDAO.selectLatestQuestions(userId, offset, limit);
    }

    public int updateCommentCount(int entityId,int count){
        return questionDAO.updateCommentCount(entityId,count);
    }
}
