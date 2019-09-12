package com.examples.demo.chain;

import java.util.Map;

/**
 * 项目名称:demo 描述: 创建人:ryw 创建时间:2019/9/12
 */
public class ActionBean implements Command{

	private Command refCommand;

	public ActionBean(Command refCommand) {
		this.refCommand = refCommand;
	}

	@Override
	public EnumChainResult execute(Map context) throws Exception {
		return this.refCommand.execute(context);
	}

	public Command getRefCommand() {
		return refCommand;
	}

	public void setRefCommand(Command refCommand) {
		this.refCommand = refCommand;
	}

}
