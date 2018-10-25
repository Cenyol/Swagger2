package com.cenyol.boot.utils;

import java.util.Random;

/**
 * @author Chenhanqun mail: chenhanqun1@corp.netease.com
 * @date 2018/10/22 16:07
 */
public class StringUtils {
    public static int random() {
        return new Random().nextInt(10);
    }

    public static String baseCharString=
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789~!@#$%^&*()_+=-[]{};':,./<>?";

    //length用户要求产生字符串的长度
    public static String getRandomString(int length){
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        int bound = baseCharString.length();
        for(int i=0;i<length;i++){
            int number=random.nextInt(bound);
            sb.append(baseCharString.charAt(number));
        }
        return sb.toString();
    }
}
