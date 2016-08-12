package com.zly.async.handler;

import com.zly.async.EventHandler;
import com.zly.async.EventModel;
import com.zly.async.EventType;
import com.zly.util.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by ly on 2016/8/11.
 */
@Component
public class LoginExceptionHandler implements EventHandler{
    @Autowired
    MailSender mailSender;

    @Override
    public void doHandle(EventModel model) {
        //判断登录异常
        Map<String,Object> map = new HashMap<String ,Object>();
        map.put("username",model.getExt("username"));
        System.out.println(model.getExt("email"));
        mailSender.sendWithHTMLTemplate(model.getExt("email"),"这是邮件发送测试","mails/login_exception.html",map);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LOGIN);
    }
}
