package com.examples.demo.config;

import com.examples.demo.chain.ActionBean;
import com.examples.demo.chain.ActionListBean;
import com.examples.demo.chain.BaseChain;
import com.examples.demo.chain.Command;
import com.examples.demo.chain.IFBean;
import com.examples.demo.config.bean.SpringBeanUtil;
import com.examples.demo.service.command.CheckTestCommand;
import com.examples.demo.service.command.FirstCommand;
import com.examples.demo.service.command.InitCommand;
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
	public SpringBeanUtil springBeanUtil() {
		return new SpringBeanUtil();
	}

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

	@Bean
	public BaseChain testCommand4(){
		BaseChain baseChain = new BaseChain();
		baseChain.addCommand(new ThirdCommand());
		baseChain.addCommand(new CheckTestCommand());
		baseChain.addCommand(new FirstCommand());
		baseChain.addCommand(new SecondCommand());
		return baseChain;
	}


	@Bean
	public BaseChain testCommand5(){
		ActionListBean baseBean = new ActionListBean("next");

		ActionBean actionBean = new ActionBean(new InitCommand());
		baseBean.addCommand(actionBean);

			IFBean ifBean = new IFBean(new CheckTestCommand());

				ActionListBean actionList2 = new ActionListBean("next");
				actionList2.addCommand(new FirstCommand());
				actionList2.addCommand(new SecondCommand());

			ifBean.setCheckOkAction(actionList2);


		baseBean.addCommand(ifBean);
		baseBean.addCommand(new ThirdCommand());
		baseBean.addCommand(new ThirdCommand());

		return baseBean;
	}


	@Bean
	public BaseChain testCommand6(){
		BaseChain baseChain = new BaseChain();
		baseChain.addNameCommand("firstCommand","");
		return baseChain;
	}


}
