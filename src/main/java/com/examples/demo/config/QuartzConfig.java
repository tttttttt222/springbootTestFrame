package com.examples.demo.config;

import com.examples.demo.task.MyFirstExerciseJob;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * 项目名称:demo 描述: 创建人:ryw 创建时间:2019/1/29
 */
@Configuration
public class QuartzConfig {


	/**
	 * 方法调用任务明细工厂Bean
	 */
	@Bean(name = "myFirstExerciseJobBean")
	public MethodInvokingJobDetailFactoryBean myFirstExerciseJobBean(MyFirstExerciseJob myFirstExerciseJob) {
		MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
		jobDetail.setConcurrent(false); // 是否并发
		jobDetail.setName("general-myFirstExerciseJob"); // 任务的名字
		jobDetail.setGroup("general"); // 任务的分组
		jobDetail.setTargetObject(myFirstExerciseJob); // 被执行的对象
		jobDetail.setTargetMethod("myJobBusinessMethod"); // 被执行的方法
		return jobDetail;
	}

	/**
	 * 表达式触发器工厂Bean
	 */
	@Bean(name = "myFirstExerciseJobTrigger")
	public CronTriggerFactoryBean myFirstExerciseJobTrigger(@Qualifier("myFirstExerciseJobBean") MethodInvokingJobDetailFactoryBean myFirstExerciseJobBean) {
		CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
		tigger.setJobDetail(myFirstExerciseJobBean.getObject());
		tigger.setCronExpression("0/1 * * * * ?"); // 什么是否触发，Spring Scheduler Cron表达式
		tigger.setName("general-myFirstExerciseJobTrigger");
		return tigger;
	}

	/**
	 * 调度器工厂Bean
	 */
	@Bean(name = "schedulerFactory")
	public SchedulerFactoryBean schedulerFactory(@Qualifier("myFirstExerciseJobTrigger") Trigger myFirstExerciseJobTrigger) {
		SchedulerFactoryBean bean = new SchedulerFactoryBean();
		// 覆盖已存在的任务
		bean.setOverwriteExistingJobs(true);
		// 延时启动定时任务，避免系统未完全启动却开始执行定时任务的情况
		bean.setStartupDelay(15);
		// 注册触发器
		bean.setTriggers(myFirstExerciseJobTrigger);
		return bean;
	}


}
