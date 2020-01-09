package com.examples.demo.controller;

import cn.teaey.sprintboot.test.EchoService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.examples.demo.config.bean.SpringBeanUtil;
import com.examples.demo.dao.model.Person;
import com.examples.demo.service.NormalService;
import com.examples.demo.service.PersonService;
import com.examples.demo.service.impl.NormalServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 项目名称:com.examples.demo.mapper
 * 描述:
 * 创建人:ryw
 * 创建时间:2018/5/23
 */
@RestController
@Slf4j
public class TestController {

    @Reference(version = "1.0.0")
    private EchoService echoService;

    @Autowired
    private PersonService personService;



    @RequestMapping("/hello")
    public String hello() {
        String sss = echoService.echo("sss");
        return sss;
    }


//    @RequestMapping("/")
//    public String index() {
//        NormalService normalService = SpringBeanUtil.getBean("normalServiceImpl", NormalServiceImpl.class);
//        String s = normalService.normalServiceTest();
//        return s;
//    }


    @RequestMapping("/person/{id}")
    public Person person(@PathVariable long id) {
        Person person = personService.queryPerson(id);
        return person;
    }


}
