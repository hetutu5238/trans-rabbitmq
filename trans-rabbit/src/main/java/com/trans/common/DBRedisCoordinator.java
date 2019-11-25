package com.trans.common;

import com.trans.entity.RabbitMetaMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 默认的redis数据处理
 * @author: zhoum
 * @Date: 2019-11-21
 * @Time: 9:46
 */
@Component
public class DBRedisCoordinator implements DBCoordinator {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void setPrepare(String msgId) {
        redisTemplate.opsForSet().add(CommonConstant.MSG_STATUS_PREPARE,msgId);
    }

    @Override
    public void setReady(String msgId , RabbitMetaMessage message) {
        redisTemplate.opsForHash().put(CommonConstant.MSG_STATUS_READY,msgId,message);
        redisTemplate.opsForSet().remove(CommonConstant.MSG_STATUS_PREPARE,msgId);
    }

    @Override
    public List getPrepare() {
        Set<RabbitMetaMessage> members = redisTemplate.opsForSet().members(CommonConstant.MSG_STATUS_PREPARE);
        return new ArrayList(members);
    }

    @Override
    public List getReady() {
        List<RabbitMetaMessage> values = redisTemplate.opsForHash().values(CommonConstant.MSG_STATUS_READY);
        return values;
    }

    @Override
    public void deleteReady(String msgId) {
        redisTemplate.opsForHash().delete(CommonConstant.MSG_STATUS_READY,msgId);
    }

    @Override
    public RabbitMetaMessage getMsgById(String msgId) {
        return (RabbitMetaMessage)redisTemplate.opsForHash().get(CommonConstant.MSG_STATUS_READY,msgId);
    }

    @Override
    public Long increatmentHash(String msgId , Long count) {
        return redisTemplate.opsForHash().increment(CommonConstant.MQ_CONSUMER_RETRY_COUNT_KEY,msgId,count);
    }

    @Override
    public void deleteReatry(String msgId) {
         redisTemplate.opsForHash().delete(CommonConstant.MQ_CONSUMER_RETRY_COUNT_KEY,msgId);
    }
}
