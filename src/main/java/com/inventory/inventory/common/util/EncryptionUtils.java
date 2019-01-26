package com.inventory.inventory.common.util;

import org.springframework.util.StringUtils;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @Title:
 * @Auther: shixianqing
 * @Date: 2018/11/26 11:08
 * @Description: EncryptionUtils
 */
public class EncryptionUtils {

    // 测试用
	public static void main(String[] args) {
        System.out.println(encryPwd("sxq123"));
	}

	public static String encryPwd(String pwd){
        String salt = createSalt();
        pwd = blendSaltAndPwd(salt,pwd);//salt+str;
        String sha512 = SHA512(pwd);
        return sha512;
    }

    //生成盐(128个字符)  盐的长度应该等同于加密后字符串的长度
    public static String createSalt() {
        char[] chars="0123456789abcdefghijklmnopqrwtuvzxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*~".toCharArray();
        char[] saltchars=new char[128];
        Random RANDOM = new SecureRandom();
        for(int i=0;i<128;i++)
        {
            int n=RANDOM.nextInt(chars.length);
            saltchars[i]=chars[n];
        }
        String salt=new String(saltchars);
        return salt;
    }

    public static String createSHA512(String salt,String pwd){

	    String blendSalt = blendSaltAndPwd(salt,pwd);
	    return SHA512(blendSalt);
    }
    //将密码混合到盐中
    public static String blendSaltAndPwd(String salt,String pwd){

	    String resultStr = null;
        StringBuilder strBuilder = new StringBuilder();
	    if ((!StringUtils.isEmpty(pwd))&&(pwd.length()>0)){

	        int pwdLength = pwd.length();
	        int saltLength = salt.length();
	        String tempStr = null;
	        int startIndex = 0;
	        int endIndex = 0;

            int timeLength = saltLength / pwdLength;
            int residue = saltLength % pwdLength;
            if (residue != 0) {
                timeLength++;
            }
	        for (int i = 0; i < pwdLength;i++){
                startIndex = timeLength * i;
                endIndex = startIndex + timeLength;
                char tempChar = pwd.charAt(i);
                strBuilder.append(tempChar);
                if (saltLength > endIndex){
                    tempStr = salt.substring(startIndex,endIndex);
                }else {
                    tempStr = salt.substring(startIndex,saltLength);
                }
                strBuilder.append(tempStr);
            }
            resultStr = strBuilder.toString();
        }
	    return resultStr;
    }

    //SHA512加密(128个字符)
    public static String SHA512(String pwd) {
        String shaPwd = null;
        if (pwd != null && pwd.length() > 0) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
                messageDigest.update(pwd.getBytes());
                byte byteBuffer[] = messageDigest.digest();
                StringBuffer strHexString = new StringBuffer();
                for (int i = 0; i < byteBuffer.length; i++) {
                    String hex = Integer.toHexString(0xff & byteBuffer[i]);
                    if (hex.length() == 1) {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                shaPwd = strHexString.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return shaPwd;
    }
}
