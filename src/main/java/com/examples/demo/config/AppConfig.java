package com.examples.demo.config;

import com.examples.demo.config.bean.SpringBeanUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 项目名称:com.examples.demo.mapper 描述: 创建人:ryw 创建时间:2018/12/25
 */
@Configuration
public class AppConfig {

	@Bean
	public SpringBeanUtil springBeanUtil() {
		return new SpringBeanUtil();
	}



}
