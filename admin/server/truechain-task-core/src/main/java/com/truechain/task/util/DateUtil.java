package com.truechain.task.util;

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
}
