package com.trans.config;

import com.trans.common.DBCoordinator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置RabbitMq的消息处理模板
 * @author: zhoum
 * @Date: 2019-11-20
 * @Time: 15:58
 */
@Configuration
@Slf4j
public class RabbitTemplateConfig {

    @Autowired
    private DBCoordinator dbCoordinator;

    @Bean
    public RabbitTemplate initRabbitTemplate(ConnectionFactory connectionFactory){
        //设置连接工厂
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        //设置消息转换器
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        //设置这个才会调用returnCallBack
        rabbitTemplate.setMandatory(true);

        //开始设置回调  即判断消息是否发送成功 消息发送后会回调该接口
        rabbitTemplate.setConfirmCallback((correlationData,ack,cause)->{
            log.info("confirm回调:{} correlationData:{} ,ack:{},cause{}",correlationData,ack,cause);
            String msgId = correlationData.getId();
            if ( ack ){
                //代表消息成功发送到broker
                log.info("消息已正确投递到队列, correlationData:{}", correlationData);
                //删除ready状态的消息
                dbCoordinator.deleteReady(msgId);
            }else {
                //消息发送失败
                log.error("消息投递至交换机失败,业务号:{}，原因:{}",correlationData.getId(),cause);
            }

        });

        rabbitTemplate.setReturnCallback((message,replyCode,replyText,exchange,routingKey)->{
                    log.error("出现returnCallBack错误 : message:{},replyCode:{},replyText:{},exchange:{},routingKey:{},");

        });
        return rabbitTemplate;

    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        Jackson2JsonMessageConverter jsonMessageConverter = new Jackson2JsonMessageConverter();
        return jsonMessageConverter;
    }
}
