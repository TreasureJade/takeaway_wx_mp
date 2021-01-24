package com.cins.hobo.takeaway_wx_mp.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : hobo
 * @className : TimeUtils
 * @date: 2021/1/23
 * @description: TODO
 */
public class TimeUtils {

    /**
     * 获得当前的时间 格式为 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getNowTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * 获得当前的时间 格式为：yyyy年yy月yy日 HH:mm:ss
     *
     * @return
     */
    public static String getTimeCN() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        return format.format(date);
    }

    /**
     * 将时间格式转换为订单所需格式
     *
     * @return java.lang.String
     * @author hobo
     */
    public static String getOrderTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(date);
    }

}
