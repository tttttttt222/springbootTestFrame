package com.examples.demo.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import javax.crypto.Cipher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Author:
 * @Date: 2019/6/23
 */
@Slf4j
public class ThirdSecurityUtil {

    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";
    /**
     * 填充方式
     */
    public static final String CIPHER_TRANSFORMAT = "RSA/ECB/PKCS1Padding";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 245;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 256;

    public static final String KEY_MD5 = "MD5";

    /**
     * 公钥加密
     *
     * @param content      原文
     * @param publicKey    公钥
     * @param inputCharset 字符编码
     * @return 加密后的字符串（base64）
     * @throws Exception
     */
    public static String encryptByPublicKey(String content, String publicKey, String inputCharset)
            throws Exception {

        byte[] data = content.getBytes(inputCharset);
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMAT);
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();

        return Base64.encodeBase64String(encryptedData);
    }

    public static String Md5(String text) {
        // 加密后的字符串
        String encodeStr = DigestUtils.md5Hex(text);
        return encodeStr;
    }

    /**
     * 私钥解密
     *
     * @param data         数据
     * @param privateKey   私钥
     * @param inputCharset 字符集
     * @return
     */
    public static String privateDecrypt(String data, String privateKey, String inputCharset) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
            RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMAT);
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] datas = Base64.decodeBase64(data);
            int maxBlock = MAX_DECRYPT_BLOCK;

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] buff;
            int i = 0;
            try {
                while (datas.length > offSet) {
                    if (datas.length - offSet > maxBlock) {
                        buff = cipher.doFinal(datas, offSet, maxBlock);
                    } else {
                        buff = cipher.doFinal(datas, offSet, datas.length - offSet);
                    }
                    out.write(buff, 0, buff.length);
                    i++;
                    offSet = i * maxBlock;
                }
            } catch (Exception e) {
                throw new RuntimeException("加解密阀值为[" + maxBlock + "]的数据时发生异常", e);
            }
            byte[] resultDatas = out.toByteArray();
            return new String(resultDatas,inputCharset);
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * json排序
     *
     * @param json
     * @return
     */
    public static String sortJsonAndTransfer(String json) {
        String res = null;
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            ArrayList<String> nameList = new ArrayList<>();
            Iterator keys = jsonObject.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next().toString();
                nameList.add(key.substring(0, 1).toUpperCase() + key.substring(1));
            }
            Collections.sort(nameList);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < nameList.size(); i++) {
                String name = nameList.get(i);
                String value = jsonObject.getString(name.substring(0, 1).toLowerCase() + name.substring(1));
                sb.append(value);
                System.out.println(nameList.get(i) + ":" + value);
            }
            res = sb.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        System.out.println(res);
        return res;
    }

    public static String getSignature(String json) {
        String data = sortJsonAndTransfer(json).toLowerCase();
        if (null != data) {
            return ThirdSecurityUtil.Md5(data.toLowerCase());
        }
        return null;
    }

    public static String getCallBackSignature(String json, String userKey) {
        String data = (sortJsonAndTransfer(json) + userKey).toLowerCase();
        if (null != data) {
            return ThirdSecurityUtil.Md5(data.toLowerCase()).toUpperCase();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        Object[] obj = SecurityUtil.generatorKeyPair();
        String originStr = "abc";
        String encrypt = encryptByPublicKey(originStr, Base64.encodeBase64String(((RSAPublicKey) obj[0]).getEncoded()), "utf-8");
        System.out.println(encrypt);
        System.out.println(privateDecrypt(encrypt, Base64.encodeBase64String(((RSAPrivateKey) obj[1]).getEncoded()), "utf-8"));
        /*String content="JGCyi8uKnXwqiLo7DyhR0yzR2K+6VfSALiUpvGqkJ2kY/Ms7zADtfwxC6T/HFz4b9vCNFnuDeP8ooCwyMJID7ugLx7OYTS3hqpKMPH8OmkPWO+NIpIRE8PeYO0wClaFBOwG+s8cj1Qd33GGYYsfxzXuZ3RhUI/nYvnLvLAgQhovlIaki1ykD2XmQmdBvTZX1RiKLMJHrxeRXB6/kYXqVpAD2H2tAJ+AWubGVp4ljMu0jMJ79wcgSWCRgr2Jbn7fvT7BJqHNA/fP0Pmt++oT4DoLR287j3XIcojpIk29KEnVK1Xx2Xv4baQnRKy8Rf1sQAflBv4Eri91UNOnQsOcJbw==";
        String key="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlx3xnf/1uRtg6nEREKJu6RIp/Fjg8SbRUJKc5vyMA6CJNwgdMnMLih8LsI0869hIodA4Kl3XHEDY/1Lg2lrYYKw8Mg5qGa9drR5sYl4ZCrGyJj/TCxQJKSGlQ63Mb40fVOzJLLI80+lRqWS1I67oTbjckGiriTcZQuxaK+bzAzImWsdIH7MqCqsxQj+G6RgQzz1aD26xRa5FSFYHeP0GamD8ji8yI4fjET/4xjI8IYWzoP+yfuDuByg8vY86TG7GViSTB+z+OCI0NqlHRwBXakBPq6D+J6J3WfgK4SS+PCzHuXVF5Km6yl3EX8k4y3gitWa5QLZ63FLLHPWpVYnvIQIDAQAB";
        System.out.println(privateDecrypt(content,key,"utf-8"));*/
    }
}
