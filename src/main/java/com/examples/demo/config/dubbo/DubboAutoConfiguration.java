package com.examples.demo.config.dubbo;

import com.alibaba.dubbo.config.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 项目名称:com.examples.demo.mapper
 * 描述:
 * 创建人:ryw
 * 创建时间:2018/5/4
 */
@Configuration
@EnableConfigurationProperties(DubboProperties.class)//开启属性注入,通过@autowired注入
public class DubboAutoConfiguration {

    @Autowired
    private DubboProperties dubboProperties;

    @Bean
    public ApplicationConfig requestApplicationConfig() {
        return dubboProperties.getApplication();
    }

    @Bean
    public RegistryConfig requestRegistryConfig() {
        return dubboProperties.getRegistry();
    }

    @Bean
    public ProtocolConfig requestProtocolConfig() {
        return dubboProperties.getProtocol();
    }


}
