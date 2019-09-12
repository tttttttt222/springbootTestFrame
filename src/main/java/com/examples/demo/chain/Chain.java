package com.examples.demo.chain;

import java.util.Map;

/**
 * 项目名称:demo 描述: 创建人:ryw 创建时间:2019/4/30
 */
public interface Chain extends Command{

	void addCommand(Command command);

}
