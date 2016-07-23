package com.zly.service;

import com.zly.dao.QuestionDAO;
import com.zly.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ly on 2016/7/23.
 */
@Service
public class QuestionService {
    @Autowired
    QuestionDAO questionDAO;

    public List<Question> getLatestQuestions(int userId,int offset,int limit) {
        return questionDAO.selectLatestQuestions(userId, offset, limit);
    }
}
