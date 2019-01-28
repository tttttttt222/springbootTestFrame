package com.examples.demo.test;

import com.examples.demo.DemoApplication;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 项目名称:demo 描述: 创建人:ryw 创建时间:2019/1/28
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@Slf4j
public class MqTest {


	@Autowired
	private AmqpTemplate rabbitTemplate;


	@Test
	public void mqTest() throws Exception {
		String context = "hello " + new Date();
		rabbitTemplate.convertAndSend("testmq",context);
		System.out.println("成功");
	}

}
