package com.megalith.config;

import com.megalith.constant.MsgConstant;
import com.trans.common.CommonConstant;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-22
 * @Time: 15:28
 */
@Configuration
public class MoneyQueue  {
    /**
     * 绑定死信队列
     * @return
     */
    @Bean
    public Queue initQueue(){
        Map<String,Object> args = new HashMap<>(2);
        args.put("x-dead-letter-exchange",CommonConstant.DLX_EXCHANGE);
        args.put("x-dead-letter-routing-key",CommonConstant.DLX_ROUTING_KEY);
        return new Queue(MsgConstant.MONEY_QUEUE ,true,false,false,args);
    }

    @Bean
    public DirectExchange initExchange(){
        return new DirectExchange(MsgConstant.MONEY_EXCHANGE);
    }

    @Bean("moneyBind")
    public Binding bind(){
        return BindingBuilder.bind(initQueue()).to(initExchange()).with(MsgConstant.MONEY_ROUTE_KEY);
    }


    @Bean
    public SimpleMessageListenerContainer init(ConnectionFactory connectionFactory,MoneyConsumer moneyConsumer){
        SimpleMessageListenerContainer sp = new SimpleMessageListenerContainer(connectionFactory);
        //设置监听队列
        sp.setQueues(initQueue());
        sp.setExposeListenerChannel(true);
        //设置需要手动回复确认消息
        sp.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        //设置监听器
        sp.setMessageListener(moneyConsumer);
        //设置最大处理个数
        sp.setPrefetchCount(100);
        return sp;
    }
}
