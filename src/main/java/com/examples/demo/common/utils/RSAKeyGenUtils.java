package com.examples.demo.common.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

/**
 * 项目名称:trade-web 描述: 创建人:ryw 创建时间:2019/7/15
 */
@Slf4j
public class RSAKeyGenUtils {


	public static String[] generatorKeyPair() {
		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
			generator.initialize(2048);
			KeyPair keyPair = generator.generateKeyPair();
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			RSAPrivateKey privatekey = (RSAPrivateKey) keyPair.getPrivate();
			byte[] publicEncoded = publicKey.getEncoded();
			byte[] privateEncoded = privatekey.getEncoded();
			return new String[]{Base64.encodeBase64String(publicEncoded), Base64.encodeBase64String(privateEncoded)};
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String[] generator1024KeyPair() {
		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
			generator.initialize(1024);
			KeyPair keyPair = generator.generateKeyPair();
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			RSAPrivateKey privatekey = (RSAPrivateKey) keyPair.getPrivate();
			byte[] publicEncoded = publicKey.getEncoded();
			byte[] privateEncoded = privatekey.getEncoded();
			return new String[]{Base64.encodeBase64String(publicEncoded), Base64.encodeBase64String(privateEncoded)};
		} catch (Exception e) {
			log.info("生成公私钥信息异常",e);
		}
		return null;
	}

}
