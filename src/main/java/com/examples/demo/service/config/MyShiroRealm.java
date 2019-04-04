package com.examples.demo.service.config;

import com.examples.demo.dao.model.Permission;
import com.examples.demo.dao.model.Role;
import com.examples.demo.dao.model.User;
import com.examples.demo.service.UserInfoService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 项目名称:demo 描述: 创建人:ryw 创建时间:2019/3/26
 */
public class MyShiroRealm extends AuthorizingRealm {

	//用于用户查询
	@Autowired
	private UserInfoService userInfoService;


	//角色权限和对应权限添加
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		//获取登录用户名
		String name= (String) principalCollection.getPrimaryPrincipal();
		//查询用户名称
		User user = userInfoService.findByUsername(name);
		//添加角色和权限
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		for (Role role:user.getRoles()) {
			//添加角色
			simpleAuthorizationInfo.addRole(role.getRole());
			for (Permission permission:role.getPermissions()) {
				//添加权限
				simpleAuthorizationInfo.addStringPermission(permission.getPermission());
			}
		}
		return simpleAuthorizationInfo;
	}

	//用户认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		//加这一步的目的是在Post请求的时候会先进认证，然后在到请求
		if (authenticationToken.getPrincipal() == null) {
			return null;
		}
		//获取用户信息
		String name = authenticationToken.getPrincipal().toString();
		User user = userInfoService.findByUsername(name);
		if (user == null) {
			//这里返回后会报出对应异常
			return null;
		} else {
			//这里验证authenticationToken和simpleAuthenticationInfo的信息
			SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, user.getPassword(), getName());
			return simpleAuthenticationInfo;
		}
	}
}
