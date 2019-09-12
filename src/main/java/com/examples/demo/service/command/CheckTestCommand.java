package com.examples.demo.service.command;

import com.examples.demo.chain.AbsIFCommand;
import java.util.Map;

/**
 * 项目名称:demo 描述: 创建人:ryw 创建时间:2019/7/10
 */
public class CheckTestCommand extends AbsIFCommand {

	@Override
	public boolean checkIfCondition(Map context) throws Exception {
		System.out.println("checkCondition");
		return true;
	}

}
