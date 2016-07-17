package com.zly.aspect;



import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;


/**a
 * Created by ly on 2016/7/16.
 */
@Aspect
@Component
public class LogAspect {        //调用IndexController所有方法之前和之后都调用下面的方法
    private static final Logger logger  = LoggerFactory.getLogger(LogAspect.class);
    @Before("execution(* com.zly.controller.IndexController.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        StringBuilder sb = new StringBuilder();
        for (Object arg:joinPoint.getArgs())   //获取参数，用log显示
        {
            sb.append("arg:"+arg.toString()+"|");
        }
        logger.info("before method"+sb.toString());
    }
    @After("execution(* com.zly.controller.IndexController.*(..))")
    public void afterMethod() {
        logger.info("after method"+new Date());
    }
}


