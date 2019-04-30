package com.examples.demo.config;

import com.examples.demo.chain.BaseChain;
import com.examples.demo.service.command.FirstCommand;
import com.examples.demo.service.command.SecondCommand;
import com.examples.demo.service.command.ThirdCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 项目名称:demo 描述: 创建人:ryw 创建时间:2019/4/30
 */
@Configuration
public class ChainConfig {


	@Bean
	public BaseChain testCommand1(){
		BaseChain baseChain = new BaseChain();
		baseChain.addCommand(new FirstCommand());
		baseChain.addCommand(new SecondCommand());
		baseChain.addCommand(new ThirdCommand());
		return baseChain;
	}


	@Bean
	public BaseChain testCommand2(){
		BaseChain baseChain = new BaseChain();
		baseChain.addCommand(new SecondCommand());
		baseChain.addCommand(new FirstCommand());
		baseChain.addCommand(new ThirdCommand());
		return baseChain;
	}


	@Bean
	public BaseChain testCommand3(){
		BaseChain baseChain = new BaseChain();
		baseChain.addCommand(new ThirdCommand());
		baseChain.addCommand(new FirstCommand());
		baseChain.addCommand(new SecondCommand());
		return baseChain;
	}


}
