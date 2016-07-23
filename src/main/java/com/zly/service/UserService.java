package com.zly.service;

import com.zly.dao.UserDAO;
import com.zly.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ly on 2016/7/23.
 */
@Service
public class UserService {
    @Autowired
    UserDAO userDAO;
    public User getUser(int id) {
        return userDAO.selectById(id);
    }
}
