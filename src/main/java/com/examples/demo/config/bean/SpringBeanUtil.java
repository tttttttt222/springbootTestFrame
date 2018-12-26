package com.examples.demo.config.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 项目名称:com.examples.demo.mapper 描述: 创建人:ryw 创建时间:2018/12/25
 */
@Slf4j
public class SpringBeanUtil implements ApplicationContextAware {


	private static ApplicationContext context;

	/**
	 * @return the context
	 */
	public static ApplicationContext getContext() {
		return context;
	}

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		SpringBeanUtil.context = context;
		log.debug("ApplicationContext registed");
	}

	public static Object getBean(String beanName) {
		return context.getBean(beanName);
	}

	public static <T> T getBean(Class<T> clazz) {
		return context.getBean(clazz);
	}

	public static <T> T getBean(String beanName, Class<T> clazz) {
		return context.getBean(beanName, clazz);
	}

}
