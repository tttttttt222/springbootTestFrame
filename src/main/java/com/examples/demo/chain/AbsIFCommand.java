package com.examples.demo.chain;

import java.util.Map;

/**
 * 项目名称:demo 描述: 创建人:ryw 创建时间:2019/4/30
 */
public abstract class AbsIFCommand implements Command{


	public final EnumChainResult execute(Map context) throws Exception {
		boolean checkResult = this.checkIfCondition(context);
		return checkResult ? EnumChainResult.CHAIN_NEXT : EnumChainResult.CHAIN_BREAK;
	}

	public abstract boolean checkIfCondition(Map context) throws Exception;

}
