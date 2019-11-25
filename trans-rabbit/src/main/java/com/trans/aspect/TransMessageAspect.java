package com.trans.aspect;

import com.trans.anno.TransMessage;
import com.trans.common.CommonConstant;
import com.trans.common.DBCoordinator;
import com.trans.entity.RabbitMetaMessage;
import com.trans.sender.RabbitSender;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-21
 * @Time: 15:00
 */
@Component
@Aspect
@Slf4j
public class TransMessageAspect {

    @Autowired
    private DBCoordinator dbCoordinator;

    @Autowired
    private RabbitSender rabbitSender;

    @Pointcut("@annotation(com.trans.anno.TransMessage)")
    public void transMessageCut(){};

    @Around("transMessageCut() && @annotation(rd)")
    public void sendMessage(ProceedingJoinPoint joinpoint, TransMessage rd){
        String exchange = rd.exchange();
        String bindingKey = rd.bindingKey();
        String bussnessId = rd.bussnessId();
        //生成业务id
        String msgId = getMsgId(bussnessId);

        //1.设置消息的prepare状态
        dbCoordinator.setPrepare(msgId);
        //2.执行业务代码
        Object result;
        try {
            result = joinpoint.proceed();
        } catch (Throwable e) {
            log.error("业务代码执行失败 业务id:{}",msgId);
            throw new RuntimeException("业务执行失败:"+bussnessId,e);
        }
        //3.组装消息发送
        RabbitMetaMessage rabbitMetaMessage = new RabbitMetaMessage();
        rabbitMetaMessage.setMessageId(msgId);
        rabbitMetaMessage.setExchange(exchange);
        rabbitMetaMessage.setRoutingKey(bindingKey);
        rabbitMetaMessage.setPayload(result);

        //4.消息设置为ready状态
        dbCoordinator.setReady(msgId,rabbitMetaMessage);

        //5.发送
        try {
            rabbitSender.sendMsg(rabbitMetaMessage);
        }catch (Exception e){
            log.error("消息发送异常 业务id:{}",msgId);
            throw e;
        }
    }

    private String getMsgId(String bussnessId){
        SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sb.format(new Date());
        return bussnessId+ CommonConstant.DB_SPLIT+format+CommonConstant.DB_SPLIT+ UUID.randomUUID().toString().replaceAll("-","");
    }
}
