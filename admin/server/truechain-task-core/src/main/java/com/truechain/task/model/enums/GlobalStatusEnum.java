package com.truechain.task.model.enums;

public enum GlobalStatusEnum {

    SUCCESS(200, "成功"), FAIL(500, "系统异常");

    private int code;

    private String desc;

    private GlobalStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
