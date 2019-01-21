package com.examples.demo.test;

import com.alibaba.dubbo.config.annotation.Reference;
import com.examples.demo.DemoApplication;
import com.examples.demo.common.utils.ZookeeperLockHelper;
import com.examples.demo.config.bean.SpringBeanUtil;
import com.examples.demo.facade.HelloService;
import com.examples.demo.service.NormalService;
import com.examples.demo.service.impl.NormalServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
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
@Slf4j
public class DubboTest {



	@Reference(version = "1.0.0")
	private HelloService helloService;

	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@Autowired
	private ZookeeperLockHelper zookeeperLockHelper;

	@Test
	public void dubboTest() throws Exception {
		String build = helloService.build("123");
		System.out.println(build);
	}

	@Test
	public void springBeantest() throws Exception {
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


	@Test
	public void threadZkhelper() throws Exception {
		String KEY = "TASK_ACTIVITY";
		InterProcessMutex lock = zookeeperLockHelper.createPath(KEY);
		try {
			if (null == lock) {
				log.info("创建并发锁失败！");
				return;
			}
			if (!zookeeperLockHelper.lockAcquired(lock)) {
				log.info("获取并发锁失败！");
				return;
			}
			log.info("获取到并发锁！开始读取数据......");
			//todo 业务
		} catch (Exception e) {
			log.info("处理异常！", e);
		} finally {
			if (lock != null) {
				//释放锁
				log.info("释放锁");
				zookeeperLockHelper.lockRelease(lock, KEY);
			}
		}
	}

}
