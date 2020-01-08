package com.examples.demo.common.utils;

import org.apache.commons.codec.binary.Base64;

public class Base64Tools {

	public Base64Tools() {

	}

    /**
     * 解密
     *
     * @param pwd
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String decodeStr(String pwd)
    {
        byte[] debytes = Base64.decodeBase64(pwd);
        return new String(debytes);
    }

    /**
     * 加密
     *
     * @param pwd
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String encodeStr(String pwd)
    {
        byte[] enbytes = Base64.encodeBase64(pwd.getBytes());
        return new String(enbytes);
    }
}
