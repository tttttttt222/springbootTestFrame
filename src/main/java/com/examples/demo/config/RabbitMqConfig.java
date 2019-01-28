package com.examples.demo.config;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 项目名称:demo 描述: 创建人:ryw 创建时间:2019/1/28
 */
@Configuration
public class RabbitMqConfig {

	@Bean
	public Queue Queue() {
		return new Queue("testmq");
	}

}
