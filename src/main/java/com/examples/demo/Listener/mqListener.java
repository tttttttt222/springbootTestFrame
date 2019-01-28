package com.examples.demo.Listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 项目名称:demo 描述: 创建人:ryw 创建时间:2019/1/28
 */
@Component
@Slf4j
public class mqListener {


    @RabbitListener(queues = "testmq")
	public void process(String msgContent) {
		try {
			log.info(msgContent);
		} catch (Throwable e) {
			log.error("MQ监听消费系统异常", e);
		}
	}

}
