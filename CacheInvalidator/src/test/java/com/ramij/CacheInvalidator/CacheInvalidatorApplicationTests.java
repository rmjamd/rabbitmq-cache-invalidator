package com.ramij.CacheInvalidator;

import com.rabbitmq.client.Channel;
import com.ramij.CacheInvalidator.rmq.MessageBus;
import com.ramij.CacheInvalidator.rmq.MessageTopic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CacheInvalidatorApplicationTests {

	Channel channel;
	@Autowired
	MessageBus messageBus;
	@Autowired
	MessageBus messageBus2;

	@Test
	public void contextLoads() throws IOException {
		MessageTopic<String> topic1=messageBus.createTopic(this.getClass().getSimpleName(),String.class);
		topic1.addListener(System.out::println);

		MessageTopic<String> topic2=messageBus2.createTopic(this.getClass().getSimpleName(),String.class);
		topic2.addListener(System.out::println);
		topic1.publish("hi i am ramij");
//		MessageBus messageBus3 =new MessageBus(rmqProperty);
//		MessageTopic<String> topic3=messageBus3.createTopic(this.getClass().getSimpleName(),String.class);
//		topic3.addListener(System.out::println);



	}

}
