package com.truechain.task.core;

import java.io.Serializable;

/**
 * 返回对象
 *
 * @param <T>
 */
public class Wrapper<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;

    private String message;

    private T result;

    public Wrapper() {

    }

    public Wrapper(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Wrapper(int code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public Wrapper setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Wrapper setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getResult() {
        return result;
    }

    public Wrapper setResult(T result) {
        this.result = result;
        return this;
    }
}
