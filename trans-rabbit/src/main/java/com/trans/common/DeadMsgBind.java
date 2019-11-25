package com.trans.common;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 其他地方要用此死信队列  可以采用
 * <code>
 *   <div>Map<String, Object> args = new HashMap<>(2);</div>
 *  <div>args.put("x-dead-letter-exchange", "dlx.exchange");</div>
 *  <div>args.put("x-dead-letter-routing-key", "dlx.routing.key");</div>
 *  <div>return new Queue(CommonConstant.DLX_QUEUE,true,false,false,args);</div>
 * </code>
 * "dlx.exchange" 和 "dlx.routing.key" 为自定义的死信队列交换机和绑定key
 * @author: zhoum
 * @Date: 2019-11-21
 * @Time: 11:56
 */
@Configuration
public class DeadMsgBind {

    @Bean
    public DirectExchange deadExchange(){
        return new DirectExchange(CommonConstant.DLX_EXCHANGE);
    }


    @Bean
    public Queue deadQueue(){
        return new Queue(CommonConstant.DLX_QUEUE,true,false,false);
    }

    @Bean
    public Binding bind(){
        return BindingBuilder.bind(deadQueue()).to(deadExchange()).with(CommonConstant.DLX_ROUTING_KEY);
    }

    @Bean
    public SimpleMessageListenerContainer deadMessageListener(ConnectionFactory connectionFactory,DeadMsgListener deadMsgListener){
        SimpleMessageListenerContainer sp = new SimpleMessageListenerContainer(connectionFactory);
        //设置监听队列
        sp.setQueues(deadQueue());
        sp.setExposeListenerChannel(true);
        //设置需要手动回复确认消息
        sp.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        //设置监听器
        sp.setMessageListener(deadMsgListener);
        //设置最大处理个数
        sp.setPrefetchCount(100);
        return sp;

    }
}
