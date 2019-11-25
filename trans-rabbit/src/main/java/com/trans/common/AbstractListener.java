package com.trans.common;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 通用绑定消费者
 * @author: zhoum
 * @Date: 2019-11-21
 * @Time: 11:43
 */
@Slf4j
public abstract class AbstractListener implements ChannelAwareMessageListener {

    public abstract void handleMessage(Message message);

    @Autowired
    private DBCoordinator dbCoordinator;

    @Override
    public void onMessage(Message message , Channel channel) throws Exception {

        String messageId = message.getMessageProperties().getMessageId();
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        log.info("消费端收到消息  消息id为:{},消息体为:{}",messageId,new String(message.getBody()));
        Long retryCount = dbCoordinator.increatmentHash(messageId , 1L);
        try {
            handleMessage(message);
            //确认成功
            channel.basicAck(deliveryTag,false);
            dbCoordinator.deleteReatry(messageId);
            log.info("消费端消费成功  消息id为:{}",messageId);
        }catch (Exception e){
            log.error("消费端消费失败  消息id为:{}",messageId);
            //代表消费失败
            //大于重试次数  那么直接拒绝消息  次消息会进入死信队列  这儿有疑问 如何确认哪个是死信队列?
//            if ( retryCount > CommonConstant.RETRY_COUNT ){
                log.error("消费端消费进入死信队列  消息id为:{}",messageId);
                channel.basicReject(deliveryTag, false);
//            }
//            //没有大于重试次数 那么就进行重试
//            else{
//                Thread.sleep((1<<retryCount)*1000);
//                channel.basicNack(deliveryTag,false,true);
//                dbCoordinator.increatmentHash(messageId , 1L);
//            }
        }
    }
}
