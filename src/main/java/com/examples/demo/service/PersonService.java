package com.examples.demo.service;

import com.examples.demo.dao.model.Person;
import java.util.List;

/**
 * 项目名称:demo 描述: 创建人:ryw 创建时间:2018/12/25
 */
public interface PersonService {

	Person queryPerson(Long id);

	List<Person> queryPersonAll();

	Boolean insertPerson(Person person);
}
