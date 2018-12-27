package com.examples.demo.test;

import com.alibaba.dubbo.config.annotation.Reference;
import com.examples.demo.DemoApplication;
import com.examples.demo.config.bean.SpringBeanUtil;
import com.examples.demo.facade.HelloService;
import com.examples.demo.service.NormalService;
import com.examples.demo.service.impl.NormalServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 项目名称:com.examples.demo.mapper 描述: 创建人:ryw 创建时间:2018/12/25
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class DubboTest {



	@Reference(version = "1.0.0")
	private HelloService helloService;

	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@Test
	public void test() throws Exception {
		String build = helloService.build("123");
		System.out.println(build);
		NormalService normalService = SpringBeanUtil.getBean("normalServiceImpl", NormalServiceImpl.class);
		normalService.normalServiceTest();
	}

	@Test
	public void threadTest() throws Exception {
		threadPoolTaskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				System.out.println("线程启动");
			}
		});
	}

}
