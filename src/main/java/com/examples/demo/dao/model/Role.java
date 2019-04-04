package com.examples.demo.dao.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * 项目名称:demo 描述: 创建人:ryw 创建时间:2019/3/26
 */
@Getter
@Setter
public class Role {

	//
	private Long  id;

	//
	private String  role;

	private List<Permission> permissions;
}
