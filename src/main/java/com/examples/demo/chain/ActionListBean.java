package com.examples.demo.chain;


/**
 * 项目名称:demo 描述: 创建人:ryw 创建时间:2019/9/12
 */
public class ActionListBean extends BaseChain {

//	private List<Command> actionList;
	private String whenBreakOverrideResult;


	public ActionListBean() {

	}

	public ActionListBean(String whenBreakOverrideResult) {
		this.whenBreakOverrideResult = whenBreakOverrideResult;
	}

//	public void afterPropertiesSet() throws Exception {
//		if (this.actionList != null && this.actionList.size() > 0) {
//			this.setCommandList(this.actionList);
//		}
//	}


//	public List<Command> getActionList() {
//		return this.actionList;
//	}
//
//	public void setActionList(List<Command> actionList) {
//		this.actionList = actionList;
//	}
//
	public String getWhenBreakOverrideResult() {
		return this.whenBreakOverrideResult;
	}

	public void setWhenBreakOverrideResult(String whenBreakOverrideResult) {
		this.whenBreakOverrideResult = whenBreakOverrideResult;
	}

}
