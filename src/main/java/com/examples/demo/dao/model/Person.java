package com.examples.demo.dao.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 项目名称:demo 描述: 创建人:ryw 创建时间:2018/12/25
 */
@Getter
@Setter
public class Person {

	private Long id; // bigint(20) NOT NULL AUTO_INCREMENT,
	private int age; //int(11) DEFAULT NULL,
	private String name; // varchar(255) DEFAULT NULL,
	private int sex; //tinyint(4) DEFAULT '1' COMMENT '男：1 女 0；',

}
