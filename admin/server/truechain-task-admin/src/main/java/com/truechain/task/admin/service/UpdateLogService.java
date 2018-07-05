package com.truechain.task.admin.service;

import com.truechain.task.model.entity.SysUpdateLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UpdateLogService {


    /**
     * 获取更新日志记录
     *
     * @param sysUpdateLog
     * @param pageable
     * @return
     */
    Page<SysUpdateLog> getUpdateLogPage(SysUpdateLog sysUpdateLog, Pageable pageable);

    /**
     * 添加更新日志记录
     *
     * @param updateLog
     */
    SysUpdateLog addUpdateLog(SysUpdateLog updateLog);

    /**
     * 更新日志记录
     *
     * @param updateLog
     */
    SysUpdateLog updateUpdateLog(SysUpdateLog updateLog);

    /**
     * 删除日志记录
     *
     * @param logId
     */
    void deleteUpdateLog(Long logId);
}
