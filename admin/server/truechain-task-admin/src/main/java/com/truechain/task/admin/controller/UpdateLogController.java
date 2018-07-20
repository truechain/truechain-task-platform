package com.truechain.task.admin.controller;

import com.truechain.task.admin.service.UpdateLogService;
import com.truechain.task.core.WrapMapper;
import com.truechain.task.core.Wrapper;
import com.truechain.task.model.entity.SysUpdateLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * 更新日志Controller
 */
@RestController
@RequestMapping("/updateLog")
public class UpdateLogController extends BasicController {

    @Autowired
    private UpdateLogService updateLogService;

    /**
     * 获取更新日志记录
     */
    @PostMapping("/getUpdateLogPage")
    public Wrapper getUpdateLogPage(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent,
                                    @RequestParam int pageIndex, @RequestParam int pageSize) {
        Pageable pageable = new PageRequest(pageIndex - 1, pageSize);
        SysUpdateLog updateLog = new SysUpdateLog();
        Page<SysUpdateLog> updateLogPage = updateLogService.getUpdateLogPage(updateLog, pageable);
        return WrapMapper.ok(updateLogPage);
    }

    /**
     * 添加更新日志记录
     */
    @PostMapping("/addUpdateLog")
    public Wrapper addUpdateLog(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestBody SysUpdateLog updateLog) {
        updateLogService.addUpdateLog(updateLog);
        return WrapMapper.ok();
    }

    /**
     * 更新日志记录
     */
    @PostMapping("/updateUpdateLog")
    public Wrapper updateUpdateLog(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestBody SysUpdateLog updateLog) {
        updateLogService.updateUpdateLog(updateLog);
        return WrapMapper.ok();
    }

    /**
     * 删除日志记录
     */
    @PostMapping("/deleteUpdateLog")
    public Wrapper deleteUpdateLog(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam Long logId) {
        updateLogService.deleteUpdateLog(logId);
        return WrapMapper.ok();
    }

}
