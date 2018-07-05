package com.truechain.task.admin.service.impl;

import com.google.common.base.Preconditions;
import com.truechain.task.model.entity.SysUpdateLog;
import com.truechain.task.admin.repository.SysUpdateLogRepository;
import com.truechain.task.admin.service.UpdateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UpdateLogServiceImpl implements UpdateLogService {

    @Autowired
    private SysUpdateLogRepository sysUpdateLogRepository;

    @Override
    public Page<SysUpdateLog> getUpdateLogPage(SysUpdateLog sysUpdateLog, Pageable pageable) {
        Page<SysUpdateLog> updateLogPage = sysUpdateLogRepository.findAll(pageable);
        return updateLogPage;
    }

    @Override
    public SysUpdateLog addUpdateLog(SysUpdateLog updateLog) {
        return sysUpdateLogRepository.save(updateLog);
    }

    @Override
    public SysUpdateLog updateUpdateLog(SysUpdateLog updateLog) {
        SysUpdateLog sysUpdateLog = sysUpdateLogRepository.findOne(updateLog.getId());
        Preconditions.checkArgument(null != sysUpdateLog, "该条记录不存在");
        return sysUpdateLogRepository.save(updateLog);
    }

    @Override
    public void deleteUpdateLog(Long logId) {
        SysUpdateLog sysUpdateLog = sysUpdateLogRepository.findOne(logId);
        Preconditions.checkArgument(null != sysUpdateLog, "该条记录不存在");
        sysUpdateLogRepository.delete(sysUpdateLog);
    }
}
