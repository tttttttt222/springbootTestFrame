package com.examples.demo.chain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.util.StringUtils;

/**
 * 项目名称:demo 描述: 创建人:ryw 创建时间:2019/4/30
 */
public class BaseChain implements Chain {


	public List<Command> commandList = new ArrayList<>();

	@Override
	public void addCommand(Command command) {
		if (command == null) {
			throw new IllegalArgumentException();
		} else {
			this.commandList.add(command);
		}
	}


	public EnumChainResult execute(Map context) throws Exception {
		if (context == null) {
			throw new IllegalArgumentException();
		} else {
			EnumChainResult enumChainResult = EnumChainResult.CHAIN_NEXT;
			Iterator it = this.commandList.iterator();

			while(it.hasNext()) {
				Command command = (Command)it.next();

				try {
					enumChainResult = command.execute(context);
					if (EnumChainResult.CHAIN_BREAK.equals(enumChainResult)) {
						break;
					}
				} catch (Exception e) {
					throw e;
				}
			}

			if (this instanceof ActionListBean) {
				ActionListBean temp = (ActionListBean)this;
				if (!StringUtils.isEmpty(temp.getWhenBreakOverrideResult()) && "next".equalsIgnoreCase(temp.getWhenBreakOverrideResult())) {
					return EnumChainResult.CHAIN_NEXT;
				}
			}

			return enumChainResult;
		}
	}

	public List<Command> getCommandList() {
		return this.commandList;
	}

	public void setCommandList(List<Command> commandList) {
		this.commandList = commandList;
	}

}
