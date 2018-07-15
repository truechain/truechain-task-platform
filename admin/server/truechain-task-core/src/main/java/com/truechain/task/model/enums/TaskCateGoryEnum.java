package com.truechain.task.model.enums;

public enum TaskCateGoryEnum {
    PERSON(1), TEAM(2);

    private int code;

    TaskCateGoryEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
