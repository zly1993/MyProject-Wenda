package com.zly.async;

import com.zly.model.EntityType;

import java.util.List;

/**
 * Created by ly on 2016/8/11.
 */
public interface EventHandler {
    void doHandle(EventModel model);
    List<EventType> getSupportEventTypes();
}
