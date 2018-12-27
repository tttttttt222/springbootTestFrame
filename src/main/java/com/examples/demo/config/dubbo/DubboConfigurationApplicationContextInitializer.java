package com.examples.demo.config.dubbo;

import com.alibaba.dubbo.config.spring.AnnotationBean;
import java.io.IOException;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 项目名称:com.examples.demo.mapper 描述: 创建人:ryw 创建时间:2018/5/29
 */
@Slf4j
public class DubboConfigurationApplicationContextInitializer implements
		ApplicationContextInitializer<ConfigurableApplicationContext> {

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
//        Environment env = applicationContext.getEnvironment();
//        String scan = env.getProperty("spring.dubbo.scan");
		try {
			Properties properties = PropertiesLoaderUtils.loadAllProperties("dubbo.properties");
			String scan = properties.getProperty("spring.dubbo.scan");
			if (scan != null) {
				AnnotationBean scanner = BeanUtils.instantiate(AnnotationBean.class);
				scanner.setPackage(scan);
				scanner.setApplicationContext(applicationContext);
				applicationContext.addBeanFactoryPostProcessor(scanner);
				applicationContext.getBeanFactory().addBeanPostProcessor(scanner);
				applicationContext.getBeanFactory().registerSingleton("annotationBean", scanner);
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.error("加载annotationBean时,获取扫描包路径错误");
		}

	}
}
