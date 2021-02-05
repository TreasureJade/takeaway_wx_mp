package com.cins.hobo.takeaway_wx_mp.util;

import java.util.Random;

/**
 * @author : hobo
 * @className : RandomUtils
 * @date: 2021/2/1
 * @description: 随机数生成工具类
 */
public class RandomUtils {

    /**
     * @return java.lang.String
     * 获取一定长度的字符串，范围0~9，a-z
     * @Param [length] 指定字符串长度
     **/
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(base.length());
            sb.append(base.charAt(num));
        }
        return sb.toString();
    }


    public static String getRandomStringByLength(String s,int length){
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        sb.append(s);
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(base.length());
            sb.append(base.charAt(num));
        }
        return sb.toString();
    }
}
