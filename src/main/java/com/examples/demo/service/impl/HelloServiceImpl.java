package com.examples.demo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.examples.demo.facade.HelloService;

/**
 * 项目名称:com.examples.demo.mapper
 * 描述:
 * 创建人:ryw
 * 创建时间:2018/5/4
 */
@Service(version = "1.0.0")
public class HelloServiceImpl implements HelloService {

    @Override
    public String build(String str) {
        return "hello " + str + " !";
    }

}
