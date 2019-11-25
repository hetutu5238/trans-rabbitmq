package com.trans.common;

/**
 * 公共常量类
 * @author: zhoum
 * @Date: 2019-11-21
 * @Time: 10:03
 */
public class CommonConstant {

    /*****************************消息状态********************************************/
    /**消息为prepare状态*/
    public static final String MSG_STATUS_PREPARE = "msg:status:prepare";
    /**消息为ready状态*/
    public static final String MSG_STATUS_READY = "msg:status:ready";

    /*****重试*******/
    public static final String MQ_CONSUMER_RETRY_COUNT_KEY = "msg:status:retrycount";

    /******************************死信队列***********************************************/

    public static final String DLX_EXCHANGE = "dlx.exchange";
    public static final String DLX_QUEUE = "dlx.queue";
    public static final String DLX_ROUTING_KEY = "dlx.routing.key";


    /******************************分隔符***********************************************/
    public static final String DB_SPLIT = ",";


    /******************************数量区域***********************************************/
    public static final Integer RETRY_COUNT = 5;
    /** 递增时的基本常量 */
    public static final int BASE_NUM = 2;
}
