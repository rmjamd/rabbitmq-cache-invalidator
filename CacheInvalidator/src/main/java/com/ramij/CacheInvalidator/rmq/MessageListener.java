package com.ramij.CacheInvalidator.rmq;

public interface MessageListener<Type> {
    void onMessage(Type type);
}
