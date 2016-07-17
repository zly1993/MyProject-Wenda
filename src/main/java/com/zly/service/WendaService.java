package com.zly.service;

import org.springframework.stereotype.Service;

/**
 * Created by ly on 2016/7/16.
 */
@Service
public class WendaService {
    public String getMessage(int userId) {
        return "Hello Message " + String.valueOf(userId);
    }
}
