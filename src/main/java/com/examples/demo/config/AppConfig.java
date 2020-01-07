package com.examples.demo.config;

import com.examples.demo.common.utils.ZookeeperLockHelper;
import com.examples.demo.config.bean.SpringBeanUtil;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 项目名称:com.examples.demo.mapper 描述: 创建人:ryw 创建时间:2018/12/25
 */
@Configuration
public class AppConfig {

	@Value("${threadPool.corePoolSize}")
	private int corePoolSize;

	@Value("${threadPool.keepAliveSeconds}")
	private int keepAliveSeconds;

	@Value("${threadPool.maxPoolSize}")
	private int maxPoolSize;

	@Value("${threadPool.queueCapacity}")
	private int queueCapacity;

	@Bean
	public SpringBeanUtil springBeanUtil() {
		return new SpringBeanUtil();
	}

	@Bean
	public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setKeepAliveSeconds(keepAliveSeconds);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setRejectedExecutionHandler(new CallerRunsPolicy());
		return executor;
	}


	@Value("${zookeeper.server.address}")
	private String zkserverAdress;

	@Bean
	public ZookeeperLockHelper zookeeperLockHelper() {
		String lockPrefix = "/lock/demo";
		String zookeeperAddress = zkserverAdress;
		int retryInterval = 100;
		int maxRetryCount = 3;
		int lockTimeWait = 100;
		return new ZookeeperLockHelper(lockPrefix, zookeeperAddress, retryInterval, maxRetryCount, lockTimeWait);
	}

}
