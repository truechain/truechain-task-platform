package com.truechain.task.util;

import java.util.regex.Pattern;

public class ValidateUtil {

    /**
     * 正则表达式：验证手机号
     */
    // public static final String REGEX_MOBILE =
    // "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    public static final String REGEX_MOBILE = "^\\d{11}$";

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }
}
