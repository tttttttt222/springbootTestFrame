package com.examples.demo.service.impl;

import com.examples.demo.dao.mapper.PersonDao;
import com.examples.demo.dao.model.Person;
import com.examples.demo.service.PersonService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 项目名称:demo 描述: 创建人:ryw 创建时间:2018/12/25
 */
@Service
@Slf4j
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonDao personDao;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Override
	public Person queryPerson(Long id) {
		log.info("person");
		Person person = personDao.queryPerson(id);
		return person;
	}

	@Override
	public List<Person> queryPersonAll() {
		return personDao.queryPersonAll();
	}

	@Override
	public void addPerson(Person person) {
		log.info("add person");
		transactionTemplate.execute((TransactionCallback) transactionStatus -> {
			personDao.insertPerson(person);
			return null;
		});
	}


}
