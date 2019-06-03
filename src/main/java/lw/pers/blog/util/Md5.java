package lw.pers.blog.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {
    private static String[] hexDigits = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
    public static String encodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] bytes = md5.digest(str.getBytes("utf-8"));
        return byteArrayToHexString(bytes);
    }
    private static String byteArrayToHexString(byte[] bytes){
        StringBuffer sb = new StringBuffer();
        for(int i=0;i< bytes.length;i++){
            sb.append(byteToHexString(bytes[i]));
        }
        return sb.toString();
    }
    private static String byteToHexString(byte b){
        int n = b;
        if(n<0){
            n = 256 + n;
        }
        int d1 = n/16;
        int d2 = n%16;
        return hexDigits[d1] + hexDigits[d2];
    }
}
