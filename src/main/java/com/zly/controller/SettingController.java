package com.zly.controller;

import com.zly.service.WendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by ly on 2016/7/16.
 */
@Controller
public class SettingController {

    @Autowired
    WendaService wendaService;

    @RequestMapping(path = {"/setting"},method = {RequestMethod.GET})
    @ResponseBody
    public  String setting(HttpSession httpSession) {
        return "Setting OK. "+ wendaService.getMessage(1);
    }
}
