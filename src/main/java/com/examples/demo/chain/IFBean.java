package com.examples.demo.chain;

import java.util.Map;

/**
 * 项目名称:demo 描述: 创建人:ryw 创建时间:2019/9/12
 */
public class IFBean implements Command {


	private AbsIFCommand ifCommand;

	private Command checkOkAction;

	public IFBean(AbsIFCommand ifCommand) {
		this.ifCommand = ifCommand;
	}

	@Override
	public EnumChainResult execute(Map context) throws Exception {
		return EnumChainResult.CHAIN_NEXT.equals(this.ifCommand.execute(context)) ? this.checkOkAction.execute(context) : EnumChainResult.CHAIN_NEXT;
	}

	public AbsIFCommand getIfCommand() {
		return ifCommand;
	}

	public void setIfCommand(AbsIFCommand ifCommand) {
		this.ifCommand = ifCommand;
	}

	public Command getCheckOkAction() {
		return checkOkAction;
	}

	public void setCheckOkAction(Command checkOkAction) {
		this.checkOkAction = checkOkAction;
	}
}
