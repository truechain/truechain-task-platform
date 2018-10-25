package com.truechain.task.admin.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.truechain.task.admin.model.dto.ManageDTO;
import com.truechain.task.admin.model.viewPojo.ManagePojo;
import com.truechain.task.admin.service.ManageService;
import com.truechain.task.core.WrapMapper;
import com.truechain.task.core.Wrapper;
import com.truechain.task.model.entity.ConfigManage;
import com.truechain.task.util.JsonUtil;

import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/manage")
public class ManageController extends BasicController{

    @Autowired
    private ManageService manageService;

    @ApiOperation(value="配置管理")
    @PostMapping("/getTaskPage")
    public Wrapper getTaskPage(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestBody ManageDTO manageDTO){
        Preconditions.checkArgument(manageDTO.getPageIndex()>0,"分页信息错误");
        Preconditions.checkArgument(manageDTO.getPageSize()>1,"分页信息错误");
        Pageable pageable=new PageRequest(manageDTO.getPageIndex()-1,manageDTO.getPageSize());
        List<ManagePojo> managePojoList= Lists.newArrayList();
        Page<ConfigManage> page=manageService.getConfigManage(pageable);
        for (ConfigManage item : page) {
            ManagePojo managePojo = new ManagePojo();
            managePojo.setId(item.getId());
            managePojo.setName(item.getName());
//            managePojo.setConfigdata(item.getConfigData());
            List<ManagePojo.Option> oList = JsonUtil.parseObject(item.getConfigData(), new TypeReference<List<ManagePojo.Option>>() {	});
            managePojo.setConfigdataByList(oList);
            managePojo.setType(item.getConfigType());           
            managePojoList.add(managePojo);

        }
        return WrapMapper.ok(managePojoList);

    }
    
    
    @PostMapping("/updateManage")
    public Wrapper updateManage (@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestBody ManageDTO manageDTO){
    	 return WrapMapper.ok();
    }





}
