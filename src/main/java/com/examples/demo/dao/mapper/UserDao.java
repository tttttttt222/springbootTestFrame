package com.examples.demo.dao.mapper;

import com.examples.demo.dao.model.User;

/**
 * 项目名称:demo 描述: 创建人:ryw 创建时间:2019/3/27
 */
public interface UserDao {

	User findByUsername(String userName);

}
