package com.examples.demo.controller;

import com.examples.demo.dao.model.Person;
import com.examples.demo.service.PersonService;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
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

	@RequestMapping(value = "/admin/index", method = RequestMethod.POST)
	@RequiresPermissions("admin/index")
	public String index(ModelMap map) {
		// 加入一个属性，用来在模板中读取
		Person person = personService.queryPerson(4L);
		map.addAttribute("person", person);
		map.addAttribute("currentDate", new Date());
		// return模板文件的名称，对应src/main/resources/templates/index.html
		return "index";
	}


	//post登录
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(ModelMap map,String username, String password) {
		//添加用户认证信息
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		//进行验证，这里可以捕获异常，然后返回对应信息
		subject.login(token);
		return "forward:/admin/index";
	}


	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String error(ModelMap map) {
		return "error";
	}



	@RequestMapping(value = "/notLogin", method = RequestMethod.GET)
	public String notLogin(ModelMap map) {
		return "notlogin";
	}


}
