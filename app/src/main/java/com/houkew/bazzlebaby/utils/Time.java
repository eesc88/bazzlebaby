package com.houkew.bazzlebaby.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author SUGUOJUN(cceecc@sina.cn)
 * @version V1.0
 * @date 2015/12/4 14:36
 * @Description: 时间类工具
 */

public class Time {
    private static SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");

    /**
     * 时间戳转换成字符窜
     */
    public static String dateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }

    /**
     * 时间戳转换成字符窜
     */
    public static String dateToString(Date date) {
        return sf.format(date);
    }


}
