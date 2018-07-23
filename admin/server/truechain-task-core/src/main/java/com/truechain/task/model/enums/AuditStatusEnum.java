package com.truechain.task.model.enums;

public enum AuditStatusEnum {

    UNCOMPLATE(0), UNAUDITED(-1), AUDITED(1);

    private int code;

    AuditStatusEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
