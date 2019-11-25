package com.trans.sender;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.trans.entity.RabbitMetaMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static org.springframework.data.repository.init.ResourceReader.Type.JSON;

/**
 * 发送消息工具
 * @author: zhoum
 * @Date: 2019-11-21
 * @Time: 10:35
 */
@Component
@Slf4j
public class RabbitSender  {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void sendMsg(RabbitMetaMessage t){
        //String messageId = UUID.randomUUID().toString().replaceAll("-","");
        //设置消息属性
        MessagePostProcessor messagePostProcessor = message-> {
           message.getMessageProperties().setMessageId(t.getMessageId());
           message.getMessageProperties().setCorrelationId(t.getMessageId());
           message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
           return message;
        };
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        String json;
        try {
            json = mapper.writeValueAsString(t.getPayload());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("消息格式化失败");
        }
        Message msg = new Message(json.getBytes(),messageProperties);
        try {
            rabbitTemplate.convertAndSend(t.getExchange(),t.getRoutingKey(),msg,messagePostProcessor,new CorrelationData(t.getMessageId()));
            log.info("消息已发送:{},消息id:{}",t,t.getMessageId());
        }catch (Exception e){
            //消息发送即出现异常
            throw new RuntimeException(String.format("消息发送出现异常 %s",t));
        }




    }
}
