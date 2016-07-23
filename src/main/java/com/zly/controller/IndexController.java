package com.zly.controller;

import com.zly.aspect.LogAspect;
import com.zly.model.User;
import com.zly.service.WendaService;
import org.apache.commons.digester.annotations.rules.AttributeCallParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HttpServletBean;
import org.springframework.web.servlet.mvc.method.annotation.ListenableFutureReturnValueHandler;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by ly on 2016/7/16.
 * 该部分为Controller，主要实现请求参数解析，以及返回的数据的控制
 */
//@Controller
public class IndexController {
    private static final Logger logger  = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    WendaService wendaService;

    @RequestMapping(path={"/","/index"})
    //如果访问的是“/”这样一个地址，即默认首页，则返回一个如下return的内容
    @ResponseBody
    //ResponseBody表示返回的是一个字符串而不是模板，如果返回的是一个模板需要将此注释掉
    public String index(HttpSession httpSession) {
        logger.info("VISIT HOME");
        return wendaService.getMessage(2)+".Hello world  "+httpSession.getAttribute("msg");
    }

    @RequestMapping(path={"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("userId") int userId,       //PathVariable表示要解析一个路径变量
                          @PathVariable("groupId") String groupId,
                          @RequestParam(value = "type",defaultValue = "1") int type,           //RequestPara表示要解析一个请求变量
                          @RequestParam(value = "key",defaultValue = "zz",required = false) String key) {   //required表示如果没有默认也没有输入，则返回null
        return String.format("Profile Page of %s / %d，type:%d key:%s",groupId,userId,type,key);
    }

    @RequestMapping(path={"/vm"},method = {RequestMethod.GET})
    public String template(Model model) {
        model.addAttribute("value1","vvvv1");
        List<String> colors = Arrays.asList(new String[]{"RED","GREEN","BLUE"});
        model.addAttribute("colors",colors);

        Map<String, String> map = new HashMap<>();
        for(int i=0;i<4;++i) {
            map.put(String.valueOf(i),String.valueOf(i*i));
        }
        model.addAttribute("map",map);
        model.addAttribute("user", new User("LEE"));
        return "home";  //返回名为“home”的模板
    }

    @RequestMapping(path = {"/request"},method = {RequestMethod.GET})
    @ResponseBody
    public String template(Model model, HttpServletResponse response,
                           HttpServletRequest request,
                           HttpSession httpsession,
                           @CookieValue("JSESSIONID") String sessionId) {
        StringBuilder sb = new StringBuilder();
        sb.append(sessionId+"<br>");
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            sb.append(name+":"+request.getHeader(name)+"<br>");
        }
        if(request.getCookies()!=null) {
            for(Cookie cookie : request.getCookies()) {
                sb.append("Cookie:"+cookie.getName()+" value:"+cookie.getValue()+"<br>");
            }
        }
        sb.append(request.getMethod()+"<br>");
        sb.append(request.getQueryString()+"<br>");
        sb.append(request.getPathInfo()+"<br>");
        sb.append(request.getRequestURI()+"<br>");

        response.addHeader("coderId","hello");
        response.addCookie(new Cookie("username","coder"));

        return sb.toString();
    }

    @RequestMapping(path = {"/redirect/{code}"},method = {RequestMethod.GET})
    //页面跳转
    public RedirectView redirect(@PathVariable("code") int code,
                           HttpSession httpSession) {
        httpSession.setAttribute("msg","jump from redirect");
        RedirectView red = new RedirectView("/",true);
        if(code == 301) {       //301：永久跳转（原有页面将永久不存在），302：临时跳转（原页面在修改，可能会恢复）
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }

        return red;            //跳转到首页，返回的是什么？
    }
    @RequestMapping(path = "/admin",method = {RequestMethod.GET})
    @ResponseBody
    public String admin(@RequestParam("key") String key) {
        if("admin".equals(key)) {
            return "hello admin";
        }
        throw new IllegalArgumentException("参数不对");
    }


    @ExceptionHandler()
    @ResponseBody
    public String error(Exception e) {
        return "error:"+e.getMessage();
    }
}
