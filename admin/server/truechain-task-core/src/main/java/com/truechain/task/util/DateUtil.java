package com.truechain.task.util;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取日期
     *
     * @return
     */
    public static String getDate() {
        return simpleDateFormat.format(new Date());
    }

    /**
     * 根据精度获取当前时间
     *
     * @param precision
     * @return
     * @description:
     * @date: 2016年10月12日下午2:51:05
     * @author: wubj
     */
    public static long getCurrentTime(String precision) {
        long ct = 0L;
        if (StringUtils.isNotBlank(precision)) {
            if ("H".equals(precision))
                ct = System.currentTimeMillis() / (1000 * 60 * 60);// 精度到时
            else if ("M".equals(precision))
                ct = System.currentTimeMillis() / (1000 * 60);// 精度到分
            else if ("S".equals(precision))
                ct = System.currentTimeMillis() / 1000;// 精度到秒
            else
                ct = System.currentTimeMillis();// 精度到毫秒
        } else
            ct = System.currentTimeMillis();// 精度到毫秒
        return ct;
    }
}
