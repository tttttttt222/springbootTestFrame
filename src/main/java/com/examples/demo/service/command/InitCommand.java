package com.examples.demo.service.command;

import com.examples.demo.chain.Command;
import com.examples.demo.chain.EnumChainResult;
import java.util.Map;

/**
 * 项目名称:demo 描述: 创建人:ryw 创建时间:2019/4/30
 */
public class InitCommand implements Command {

	@Override
	public EnumChainResult execute(Map context) throws Exception {
		System.out.println("init-step --" + context.get("context"));
		return EnumChainResult.CHAIN_NEXT;
	}
}
