package com.examples.demo.task;

import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * 项目名称:demo 描述: 创建人:ryw 创建时间:2019/1/29
 */
@Component
@EnableScheduling
@Slf4j
public class MyFirstExerciseJob {

	public void myJobBusinessMethod() {
		log.info("定时任务触发" + new Date());
	}
}
