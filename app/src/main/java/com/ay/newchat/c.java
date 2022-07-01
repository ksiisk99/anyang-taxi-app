package com.ay.newchat;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class c {

    public static String sha256(String pass){
        String rst=null;
        try {
            MessageDigest md=MessageDigest.getInstance("SHA-256");
            md.update(pass.getBytes("UTF-8"));
            byte[] bytes=md.digest();
            StringBuilder sb=new StringBuilder();
            for(byte b:bytes){
                sb.append(String.format("%02x",b));
            }
            rst=sb.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return rst;
    }
}
