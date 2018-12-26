package com.examples.demo.service.impl;

import com.examples.demo.service.NormalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 项目名称:com.examples.demo.mapper 描述: 创建人:ryw 创建时间:2018/12/25
 */
@Service
@Slf4j
public class NormalServiceImpl implements NormalService {

	@Override
	public String normalServiceTest() {
		log.info("日志");
		System.out.println("NormalService");
		return "NormalService";
	}
}
