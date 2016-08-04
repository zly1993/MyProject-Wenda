package com.zly.service;

import com.zly.controller.IndexController;
import com.zly.dao.LoginTicketDAO;
import com.zly.dao.UserDAO;
import com.zly.model.LoginTicket;
import com.zly.model.User;
import com.zly.util.WendaUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

/**
 * Created by ly on 2016/7/23.
 */
@Service
public class UserService {
    private static final Logger logger  = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private LoginTicketDAO loginTicketDAO;

    public User selectByName(String name) {
        return userDAO.selectByName(name);
    }

    public Map<String,String> register(String username,String password) {
        Map<String,String> map = new HashMap<String,String>();
        if (StringUtils.isBlank(username)) {
            map.put("msg","用户名不能为空");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("msg","密码不能为空");
            return map;
        }

        User user = userDAO.selectByName(username);
        if (user!=null) {
            map.put("msg","用户名已经被注册");
            return map;
        }
        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000)));
        user.setPassword(WendaUtil.MD5(password+user.getSalt()));
        userDAO.addUser(user);

        //登录下发ticket
        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }

    public Map<String,String> login(String username,String password) {
        Map<String,String> map = new HashMap<String,String>();
        if (StringUtils.isBlank(username)) {
            map.put("msg","用户名不能为空");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("msg","密码不能为空");
            return map;
        }

        User user = userDAO.selectByName(username);
        if (user==null) {
            map.put("msg","用户名不存在");
            return map;
        }
        if (!WendaUtil.MD5(password+user.getSalt()).equals(user.getPassword())) {
            map.put("msg","密码错误");
            return map;
        }
        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }


    public String addLoginTicket(int userId) {
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000*3600*24);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTicketDAO.addTicket(ticket);
        return ticket.getTicket();
    }
    public User getUser(int id) {
        return userDAO.selectById(id);
    }
    public void logout(String ticket) {
        loginTicketDAO.updateStatus(ticket, 1);
    }
}
