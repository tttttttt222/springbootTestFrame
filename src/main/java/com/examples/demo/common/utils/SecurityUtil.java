package com.examples.demo.common.utils;

import java.io.BufferedReader;
import java.io.StringReader;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;

/**
 * @Author:
 * @Date: 2019/6/23
 */
@Slf4j
public class SecurityUtil {

    public final static String RSA_CHIPER = "RSA/ECB/PKCS1Padding";

    /**
     * 2048bit 加密块 大小
     */
    public final static int ENCRYPT_KEYSIZE = 245;
    /**
     * 2048bit 解密块 大小
     */
    public final static int DECRYPT_KEYSIZE = 256;


    //生成公私钥
    public static Object[] generatorKeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            KeyPair keyPair = generator.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privatekey = (RSAPrivateKey) keyPair.getPrivate();
            return new Object[]{publicKey, privatekey};
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String cer = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmmlg7tOc4vABvOgqxB9jw2qcq/9PHzNG3ptM/aUA3MYV8Ms2V+snlwKhp7rVPe6n8YsFe0aK2im3fPOoN/vj9zvd+aBxTBNLK/aDgUCLDklsyQ/j63uOp2p/zx0vTXBn0B7U51moG5KRK/CnyPZKjH0zQJ+aKfY28vNSjLnHnW3Ehx3LnwX8JBSfWEm/D0nlTEIVJy+lbyS1uWG0OqwVYi2YuFBkvGIqLzun2wTk/9h++gCjFZi6PZ4j9oZAQ1f8qX7sRYIikl0VAAV+WswI5vQqGPZDUmLUW4kacpArShJxgqMP8Oaj9SasYT8k7+Lbk4rW7reFM+vomIbNu8zRbQIDAQAB";
        System.out.println(cer.length());
        System.out.println(getPublicKeyByText(cer));
    }
    /**
     * 根据公钥Cer文本串读取公钥
     *
     * @param pubKeyText
     * @return
     */
    private static PublicKey getPublicKeyByText(String pubKeyText) {
        try {
            BufferedReader br = new BufferedReader(new StringReader(pubKeyText));
            String line = null;
            StringBuilder keyBuffer = new StringBuilder();
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("-")) {
                    keyBuffer.append(line);
                }
            }
            return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decodeBase64(keyBuffer.toString())));
        } catch (Exception e) {
            log.error("解析公钥内容失败:", e);
        }
        return null;
    }

    /**
     * 方式1
     * 根据公钥Cer文本串读取公钥
     *
     * @param pubKeyText
     * @return
     */
    private static PrivateKey getPrivateKeyByText(String pubKeyText) {
        try {
            BufferedReader br = new BufferedReader(new StringReader(pubKeyText));
            String line = null;
            StringBuilder keyBuffer = new StringBuilder();
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("-")) {
                    keyBuffer.append(line);
                }
            }
            return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.decodeBase64(keyBuffer.toString())));
        } catch (Exception e) {
            log.error("解析公钥内容失败:", e);
        }
        return null;
    }

    //加密
    public static String encrypt(String src, String keyContent, boolean isPrivate) {
        if (isPrivate) {
            return byte2Hex(rsaByPrivateKey(src.getBytes(), getPrivateKeyByText(keyContent), 1));
        } else {
            return byte2Hex(rsaByPublicKey(src.getBytes(), getPublicKeyByText(keyContent), 1));
        }
    }

    //解密
    public static String decrypt(String src, String keyContent, boolean isPrivate) {
        if (isPrivate) {
            return new String(rsaByPrivateKey(hex2Bytes(src), getPrivateKeyByText(keyContent), 2));
        } else {
            return new String(rsaByPublicKey(hex2Bytes(src), getPublicKeyByText(keyContent), 2));
        }
    }


    // ======================================================================================
    // 公私钥算法
    // ======================================================================================

    /**
     * 公钥算法
     *
     * @param srcData   源字节
     * @param publicKey 公钥
     * @param mode      加密 OR 解密
     * @return
     */
    private static byte[] rsaByPublicKey(byte[] srcData, PublicKey publicKey, int mode) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_CHIPER);
            cipher.init(mode, publicKey);
            // 分段加密
            int blockSize = (mode == Cipher.ENCRYPT_MODE) ? ENCRYPT_KEYSIZE : DECRYPT_KEYSIZE;
            byte[] encryptedData = null;
            for (int i = 0; i < srcData.length; i += blockSize) {
                // 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
                byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(srcData, i, i + blockSize));
                encryptedData = ArrayUtils.addAll(encryptedData, doFinal);
            }
            return encryptedData;

        } catch (NoSuchAlgorithmException e) {
            log.error("公钥算法-不存在的解密算法:", e);
        } catch (NoSuchPaddingException e) {
            log.error("公钥算法-无效的补位算法:", e);
        } catch (IllegalBlockSizeException e) {
            log.error("公钥算法-无效的块大小:", e);
        } catch (BadPaddingException e) {
            log.error("公钥算法-补位算法异常:", e);
        } catch (InvalidKeyException e) {
            log.error("公钥算法-无效的私钥:", e);
        }
        return null;
    }

    /**
     * 私钥算法
     *
     * @param srcData    源字节
     * @param privateKey 私钥
     * @param mode       加密 OR 解密
     * @return
     */
    private static byte[] rsaByPrivateKey(byte[] srcData, PrivateKey privateKey, int mode) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_CHIPER);
            cipher.init(mode, privateKey);
            // 分段加密
            int blockSize = (mode == Cipher.ENCRYPT_MODE) ? ENCRYPT_KEYSIZE : DECRYPT_KEYSIZE;
            byte[] decryptData = null;
            for (int i = 0; i < srcData.length; i += blockSize) {
                byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(srcData, i, i + blockSize));
                decryptData = ArrayUtils.addAll(decryptData, doFinal);
            }
            return decryptData;
        } catch (NoSuchAlgorithmException e) {
            log.error("私钥算法-不存在的解密算法:", e);
        } catch (NoSuchPaddingException e) {
            log.error("私钥算法-无效的补位算法:", e);
        } catch (IllegalBlockSizeException e) {
            log.error("私钥算法-无效的块大小:", e);
        } catch (BadPaddingException e) {
            log.error("私钥算法-补位算法异常:", e);
        } catch (InvalidKeyException e) {
            log.error("私钥算法-无效的私钥:", e);
        }
        return null;
    }

    // ==Aes加解密==================================================================

    /**
     * aes解密-128位
     */
    public static String aesDecrypt(String encryptContent, String password) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            keyGen.init(128, secureRandom);
            SecretKey secretKey = keyGen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(hex2Bytes(encryptContent)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * aes加密-128位
     */
    public static String aesEncrypt(String content, String password) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            keyGen.init(128, secureRandom);
            SecretKey secretKey = keyGen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return byte2Hex(cipher.doFinal(content.getBytes("UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将byte[] 转换成字符串
     *
     * @return
     */
    public static String byte2Hex(byte[] srcBytes) {
        StringBuilder hexRetSB = new StringBuilder();
        for (byte b : srcBytes) {
            String hexString = Integer.toHexString(0x00ff & b);
            hexRetSB.append(hexString.length() == 1 ? 0 : "").append(hexString);
        }
        return hexRetSB.toString();
    }

    /**
     * 将16进制字符串转为转换成字符串
     *
     * @param source
     * @return
     */
    public static byte[] hex2Bytes(String source) {
        byte[] sourceBytes = new byte[source.length() / 2];
        for (int i = 0; i < sourceBytes.length; i++) {
            sourceBytes[i] = (byte) Integer.parseInt(source.substring(i * 2, i * 2 + 2), 16);
        }
        return sourceBytes;
    }

}
