package com.trans.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置RabbitMq的连接工厂
 * @author: zhoum
 * @Date: 2019-11-20
 * @Time: 15:06
 */
@Configuration
@EnableRabbit
public class RabbitConnectionConfig {

    @Value("${rabbitmq.host}")
    private String host;
    @Value("${rabbitmq.port}")
    private int port;
    @Value("${rabbitmq.username}")
    private String username;
    @Value("${rabbitmq.password}")
    private String password;
    @Value("${rabbitmq.virtualHost}")
    private String virtualHost;
    @Value("${rabbitmq.cacheSize}")
    private int cacheSize;

    @Bean
    public CachingConnectionFactory initConnectionFactory(){
        CachingConnectionFactory factory = new CachingConnectionFactory();
        //设置基本信息
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setVirtualHost(virtualHost);
        factory.setChannelCacheSize(cacheSize);
        //设置未发送到合适路由开启确认
        factory.setPublisherReturns(true);
        //设置未发送到交换机开启确认
        factory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        return factory;
    }
    @Bean
    public SimpleRabbitListenerContainerFactory initListenerFactory(ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory sp = new SimpleRabbitListenerContainerFactory();
        sp.setConnectionFactory(connectionFactory);
        sp.setConcurrentConsumers(3);
        sp.setMaxConcurrentConsumers(10);
        //回调确认需要手动确认
        sp.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return sp;
    }

}
