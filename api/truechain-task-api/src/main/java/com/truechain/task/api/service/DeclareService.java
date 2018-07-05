package com.truechain.task.api.service;


import com.truechain.task.model.entity.SysDeclare;

public interface DeclareService {

    /**
     * 获取默认的说明
     *
     * @return
     */
    SysDeclare getDefaultDeclare();
}
