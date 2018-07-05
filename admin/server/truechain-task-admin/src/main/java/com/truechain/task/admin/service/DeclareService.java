package com.truechain.task.admin.service;

import com.truechain.task.model.entity.SysDeclare;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 说明Service
 */
public interface DeclareService {

    /**
     * 获取说明列表
     *
     * @param declare
     * @param pageable
     * @return
     */
    Page<SysDeclare> getDeclarePage(SysDeclare declare, Pageable pageable);

    /**
     * 获取说明详情
     *
     * @param declareId
     * @return
     */
    SysDeclare getDeclareInfo(Long declareId);

    /**
     * 添加说明
     *
     * @param declare
     * @return
     */
    SysDeclare addDeclare(SysDeclare declare);

    /**
     * 更新说明
     *
     * @param declare
     * @return
     */
    SysDeclare updateDeclare(SysDeclare declare);

    /**
     * 删除说明
     *
     * @param declareId
     */
    void deleteDeclare(Long declareId);
}
