package com.examples.demo.controller;

import com.examples.demo.dao.model.Person;
import com.examples.demo.service.PersonService;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 项目名称:demo 描述: 创建人:ryw 创建时间:2019/1/14
 */

@Controller
@RequestMapping(value = "/")
@Slf4j
public class WebController {

	@Autowired
	private PersonService personService;

	@RequestMapping(method = RequestMethod.GET)
	public String index(ModelMap map) {
		// 加入一个属性，用来在模板中读取
//		Person person = personService.queryPerson(1L);
		Person person = personService.queryPerson(4L);
		if(person == null){
			person =new Person();
		}
		map.addAttribute("person", person);
		map.addAttribute("currentDate", new Date());
		// return模板文件的名称，对应src/main/resources/templates/index.html
		return "index";
	}

}
