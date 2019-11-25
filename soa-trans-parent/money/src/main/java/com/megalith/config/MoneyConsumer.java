package com.megalith.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.megalith.entity.CommodityVO;
import com.trans.common.AbstractListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-22
 * @Time: 15:26
 */
@Component
@Slf4j
public class MoneyConsumer extends AbstractListener {


    @Override
    public void handleMessage(Message message) {
//        byte[] body = message.getBody();
//        String msg = new String(body);
//        ObjectMapper mapper = new ObjectMapper();
//        CommodityVO commodityVO = null;
//        try {
//            commodityVO = mapper.readValue(msg , CommodityVO.class);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("消费失败");
//        }
//        log.info("money端 收到消费消息:{}",commodityVO);
        throw new RuntimeException("测试错误");
    }
}
