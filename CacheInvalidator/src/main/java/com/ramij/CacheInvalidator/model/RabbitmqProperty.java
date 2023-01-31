package com.ramij.CacheInvalidator.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ConfigurationProperties(prefix = "rmq")
@Configuration("rmqProperty")
public class RabbitmqProperty {
    private String url;
    private String username;
    private String password;
}
