package com.zly.model;

/**
 * Created by ly on 2016/7/16.
 * 该部分为model，用于存储controller需要返回的一些信息，再使用模板将这些信息进行渲染
 */
public class User {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String name) {
        this.name = name;
    }

    public String getDescription() {
        return "This is "+name;
    }
}
