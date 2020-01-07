package com.examples.demo.dao.mapper;

import com.examples.demo.dao.model.Person;
import java.util.List;

/**
 * 项目名称:demo 描述: 创建人:ryw 创建时间:2018/12/25
 */
public interface PersonDao {

	Person queryPerson(Long id);

	void insertPerson(Person person);

	List<Person> queryPersonAll();
}
