package com.trans.entity;

import lombok.Data;

/**
 * rabbitmq的发送的消息 元数据信息
 */
@Data
public class RabbitMetaMessage {
	/**
	 * 消息id
	 */
	private String messageId;
	/**
	 * 交换机
	 */
	private String exchange;
	/**
	 * 路由id
	 */
	private String routingKey;
	/**
	 * 消息主体
	 */
	private Object payload;
}
