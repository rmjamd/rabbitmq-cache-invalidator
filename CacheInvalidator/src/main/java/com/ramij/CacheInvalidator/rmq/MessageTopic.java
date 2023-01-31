package com.ramij.CacheInvalidator.rmq;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface MessageTopic<EntityType> {
    public void addListener(MessageListener<EntityType> listener);
    public void removeListener();
    public void publish(EntityType t) throws JsonProcessingException;
}
