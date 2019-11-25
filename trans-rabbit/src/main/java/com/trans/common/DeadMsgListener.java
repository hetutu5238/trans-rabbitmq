package com.trans.common;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * 死信队列处理
 * @author: zhoum
 * @Date: 2019-11-21
 * @Time: 11:45
 */
@Component
@Slf4j
public class DeadMsgListener implements ChannelAwareMessageListener {


    @Override
    public void onMessage(Message message , Channel channel) throws Exception {
        String msg = new String(message.getBody());
        String messageId = message.getMessageProperties().getMessageId();
        log.warn("dead letter message：{} | tag：{}", msg, message.getMessageProperties().getDeliveryTag());
		//收到死信后可以做的一些处理
        // 入库
		//insertRecord(logKey, message);
		// 发邮件
		//sendEmail(logKey, messageProperties.getMessageId(), messageBody);

        //手动回复确认收到
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);

        //可以进行DBCoordinator操作  例如删除掉需要重试的数据



    }
}
