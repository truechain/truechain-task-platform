package com.truechain.task.core;

import com.truechain.task.model.enums.GlobalStatusEnum;
import org.apache.commons.lang3.StringUtils;

/**
 * Wrap Mapper
 */
public class WrapMapper {

    private WrapMapper() {

    }

    /**
     * wrap
     *
     * @param code
     * @param message
     * @param o
     * @param <E>
     * @return
     */
    public static <E> Wrapper<E> wrap(int code, String message, E o) {
        return new Wrapper<>(code, message, o);
    }

    /**
     * wrap
     *
     * @param code
     * @param message
     * @param <E>
     * @return
     */
    public static <E> Wrapper<E> wrap(int code, String message) {
        return new Wrapper<>(code, message, null);
    }

    /**
     * ok
     *
     * @param <E>
     * @return
     */
    public static <E> Wrapper<E> ok() {
        return new Wrapper<>(GlobalStatusEnum.SUCCESS.getCode(), GlobalStatusEnum.SUCCESS.getDesc());
    }

    /**
     * ok
     *
     * @param o
     * @param <E>
     * @return
     */
    public static <E> Wrapper<E> ok(E o) {
        return ok().setResult(o);
    }

    /**
     * error
     *
     * @param <E>
     * @return
     */
    public static <E> Wrapper<E> error() {
        return wrap(GlobalStatusEnum.FAIL.getCode(), GlobalStatusEnum.FAIL.getDesc());
    }

    /**
     * error
     *
     * @param message
     * @param <E>
     * @return
     */
    public static <E> Wrapper<E> error(String message) {
        return wrap(GlobalStatusEnum.FAIL.getCode(), StringUtils.isEmpty(message) ? GlobalStatusEnum.FAIL.getDesc() : message);
    }

    /**
     * 数据为null
     * @param message
     * @param <E>
     * @return
     */
    public static <E> Wrapper<E> errorNull(String message) {
        return wrap(GlobalStatusEnum.NULL.getCode(), StringUtils.isEmpty(message) ? GlobalStatusEnum.NULL.getDesc() : message);
    }
}
