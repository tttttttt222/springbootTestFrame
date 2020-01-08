package com.examples.demo.test;

import com.alibaba.dubbo.common.json.JSON;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

/**
 * 项目名称:rewardpoints-front 描述: 创建人:ryw 创建时间:2020/1/3
 */
public class TestHttp {

	private String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmGzhTGwVujigGaUynVbD5pryXCo+mQJvGP7Vq33Agu4x1kiosotNnkdO9SCgh3G6asYhtLR3PAhxzV5nFz4QZ5nCwtG4ALwJ39lkvfiJdEjyjNDMOXy5Twp2wRXhUmQOJWWsTBW+bKW7pW7iDxdzWH4cS6vb+hHUg3vd9oj4BwcvN+5rXhwfCfcw9N60/1elqVbqTKDEVJs/rLx9/22JEp3yXwkaFobgNylxl+4srBVdlxigg4Ko+Dv386FGfQtFjq+GTmuPL1NZmr1Uo6M5ZgfFtLaXS+AqW9GSG8YjxcBzn8qB8o2/QVDG0x1oNP2s5rj3mTRYU022EPG8RhgNUwIDAQAB";

	@Test
	public void test() throws Exception {
		String accountQueryOperateURL = "http://127.0.0.1:8333/rewardpoints/api/testRsaRequest";
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "x-www-form-urlencoded");

		//拼接冻结的参数
		String memberId = "10000178";
		String channelId = "1";
		String version = "1.0.0";

//		SearchReqDto req = new SearchReqDto();
//		req.setMobile("13917917927");

		Map<String, String> params = new HashMap<>();
		params.put("memberId", memberId);
		params.put("channelId", channelId);
		params.put("version", version);

//		System.out.println("请求参数" + JSON.toJSONString(req));

//		params.put("content", EnDecryptUtils.encryptData(JSON.toJSONString(req), publicKey));
//		String response = HttpTools.sendPost(accountQueryOperateURL, headers, params);
//		System.out.println("返回结果" + response);
//		if(!response.contains("msg")){
//			System.out.println("返回结果明文" + EnDecryptUtils.decryptData(response, publicKey));
//		}
	}

}
