package com.trans.common;

import com.trans.entity.RabbitMetaMessage;

import java.util.List;

/**
 * 存储相关数据时使用的通用接口
 * 消息状态一分为 prepare 和ready状态
 *
 * @author: zhoum
 * @Date: 2019-11-21
 * @Time: 9:39
 */
public interface DBCoordinator<T extends RabbitMetaMessage > {
    //设置消息为prepare状态
    void setPrepare(String msgId);

    //将prepare状态的消息设置为ready
    void setReady(String msgId , T message);

    //获取Prepare状态的消息
    List<T> getPrepare();

    //获取reday状态的消息
    List<T> getReady();

    //删除ready状态的消息
    void deleteReady(String msgId);

    //获取消息
    T getMsgById(String msgId);
    //增加消息的累计次数
    Long increatmentHash(String msgId,Long count);

    //删除ready状态的消息
    void deleteReatry(String msgId);





}
