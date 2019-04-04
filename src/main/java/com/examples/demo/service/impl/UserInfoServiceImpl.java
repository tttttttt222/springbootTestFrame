package com.examples.demo.service.impl;

import com.examples.demo.dao.mapper.PermissionDao;
import com.examples.demo.dao.mapper.RoleDao;
import com.examples.demo.dao.mapper.UserDao;
import com.examples.demo.dao.model.Permission;
import com.examples.demo.dao.model.Role;
import com.examples.demo.dao.model.User;
import com.examples.demo.service.UserInfoService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 项目名称:demo 描述: 创建人:ryw 创建时间:2019/3/27
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private PermissionDao permissionDao;

	@Override
	public User findByUsername(String username) {
		User user = userDao.findByUsername(username);
		Role roleById = roleDao.findRoleById(user.getRoleId());
		ArrayList<Role> rols = new ArrayList<>();
		rols.add(roleById);
		user.setRoles(rols);

		List<Permission> permissionById = permissionDao.findPermissionById(user.getRoleId());
		roleById.setPermissions(permissionById);

		return user;
	}

}
