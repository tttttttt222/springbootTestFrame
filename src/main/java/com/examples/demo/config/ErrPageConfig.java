package com.examples.demo.config;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * 项目名称:demo 描述: 创建人:ryw 创建时间:2020/1/9
 */
@Configuration
public class ErrPageConfig {

	@Bean
	public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
		return (factory -> {
			ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/index.html");
			factory.addErrorPages(errorPage404);
		});
	}

}
