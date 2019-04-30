package com.examples.demo.chain;

/**
 * 项目名称:demo 描述: 创建人:ryw 创建时间:2019/4/30
 */
public enum EnumChainResult {
	CHAIN_NEXT(0, "继续下一个"),
	CHAIN_BREAK(1, "完成|终止");

	private int state;
	private String name;

	private EnumChainResult(int state, String name) {
		this.state = state;
		this.name = name;
	}

	public int getState() {
		return this.state;
	}

	public String getName() {
		return this.name;
	}
}
