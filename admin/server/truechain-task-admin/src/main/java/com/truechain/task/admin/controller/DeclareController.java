package com.truechain.task.admin.controller;

import com.truechain.task.core.WrapMapper;
import com.truechain.task.core.Wrapper;
import com.truechain.task.model.entity.SysDeclare;
import com.truechain.task.admin.service.DeclareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * 配置说明Controller
 */
@RestController
@RequestMapping("/Declare")
public class DeclareController extends BasicController {

    @Autowired
    private DeclareService declareService;

    /**
     * 获取使用说明数据
     */
    @PostMapping("/getDeclarePage")
    public Wrapper getDeclarePage(@RequestParam int pageIndex, @RequestParam int pageSize) {
        Pageable pageable = new PageRequest(pageIndex - 1, pageSize);
        Page<SysDeclare> declarePage = declareService.getDeclarePage(null, pageable);
        return WrapMapper.ok(declarePage);
    }

    /**
     * 获取使用说明详情
     */
    @PostMapping("/getDeclareInfo")
    public Wrapper getDeclareInfo(@RequestParam Long declareId) {
        SysDeclare declare = declareService.getDeclareInfo(declareId);
        return WrapMapper.ok(declare);
    }

    /**
     * 添加说明
     */
    @PostMapping("/addDeclare")
    public Wrapper addDeclare(@RequestBody SysDeclare declare) {
        declareService.addDeclare(declare);
        return WrapMapper.ok();
    }

    /**
     * 更新说明
     */
    @PostMapping("/updateDeclare")
    public Wrapper updateDeclare(@RequestBody SysDeclare declare) {
        declareService.updateDeclare(declare);
        return WrapMapper.ok();
    }

    /**
     * 删除说明
     */
    @PostMapping("/deleteDeclare")
    public Wrapper deleteDeclare(@RequestParam Long declareId) {
        declareService.deleteDeclare(declareId);
        return WrapMapper.ok();
    }
}
