package com.ramij.CacheInvalidator.rmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.ramij.CacheInvalidator.model.RabbitmqProperty;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MessageBus implements Closeable{

    String id;
    @Autowired
    RabbitmqProperty rmqProperty;

    private Connection connection;
    private ConnectionFactory factory;
    private final Map<String, Channel> channelMap=new HashMap<>();
    public MessageBus(){
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername(rmqProperty.getUsername());
        factory.setPassword(rmqProperty.getPassword());
        id= UUID.randomUUID().toString();
        

    }

    public <EntityType> MessageTopic<EntityType> createTopic(String queueName , Class<EntityType> entityType) throws IOException {
        channelMap.putIfAbsent(queueName,connection.createChannel());
        Channel channel=channelMap.get(queueName);
        return new MessageTopicImpl(channel,id,queueName,entityType);
    }

    @Override
    public void close() throws IOException {
        connection.close();
    }
}
