package com.hzxiaojietan.base.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by xiaojie.tan on 2017/10/26
 * 时间帮助类
 */
public class TimeUtils {

    /**
     *
     * @return 标准时间格式long
     * @since V1.0
     */
    public static long toMillSec(long t) {
        // 解决部分时间不是毫秒的问题
        int cp = 13 - String.valueOf(t).length();
        if (cp > 0) {
            t = (long) (t * Math.pow(10, (cp)));
        } else {
            t = (long) (t / Math.pow(10, (-cp)));
        }

        return t;
    }

    public  static String getMMDD(long tms) {
        Date date = new Date(toMillSec(tms));
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        return sdf.format(date);
    }

    /**
     * 字符串转指定格式时间
     * @param str
     * @return
     */
    public static String getMMDDByStr(String str) {
        return stringToDate(str, "yyyy-MM-dd HH:mm:ss", "MM-dd");
    }

    public static String stringToDate(String dateStr, String dateFormatStr, String formatStr) {
        DateFormat sdf = new SimpleDateFormat(dateFormatStr);
        Date date = null;
        try{
            date = sdf.parse(dateStr);
        } catch (ParseException e){
            e.printStackTrace();
        }
        SimpleDateFormat s = new SimpleDateFormat(formatStr);

        return s.format(date);
    }

    /**
     * 根据当前时间生产文件名 yyyyMMddHHmmss
     * @return
     */
    public static String getNameByNowTime (){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        return format.format(date);
    }
}
