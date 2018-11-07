package com.truechain.task.admin.controller;

import io.swagger.annotations.ApiOperation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Preconditions;
import com.truechain.task.admin.model.dto.ManageDTO;
import com.truechain.task.admin.model.viewPojo.ManagePojo;
import com.truechain.task.admin.service.ManageService;
import com.truechain.task.core.WrapMapper;
import com.truechain.task.core.Wrapper;


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
       
        Page<ManagePojo> page=manageService.getTaskPage(manageDTO,pageable);
      
        return WrapMapper.ok(page);
    }
    
    @ApiOperation(value="添加配置",notes="configType数据类型,0-数字，1-字符串，2-枚举")
    @PostMapping("/addManage")
    public Wrapper addManage (@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestBody ManagePojo managePojo){
    	Preconditions.checkArgument(StringUtils.isNotBlank(managePojo.getTypeName()), "管理类型不能为空");
    	Preconditions.checkArgument(StringUtils.isNotBlank(managePojo.getManageName()), "管理名称不能为空");
    	Preconditions.checkArgument(null != managePojo.getConfigType(), "数据类型不能为空");
    	Preconditions.checkArgument(StringUtils.isNotBlank(managePojo.getConfigData()), "数据值不能为空");
    	manageService.addManage(managePojo);
    	return WrapMapper.ok();
    }
    
    @ApiOperation(value="修改配置",notes="configType数据类型,0-数字，1-字符串，2-枚举")
    @PostMapping("/updateManage")
    public Wrapper updateManage (@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, 
    		@RequestBody ManagePojo managePojo){ 
    	Preconditions.checkArgument(StringUtils.isNotBlank(managePojo.getTypeName()), "管理类型不能为空");
    	Preconditions.checkArgument(StringUtils.isNotBlank(managePojo.getManageName()), "管理名称不能为空");
    	Preconditions.checkArgument(null != managePojo.getConfigType(), "数据类型不能为空");
    	Preconditions.checkArgument(StringUtils.isNotBlank(managePojo.getConfigData()), "数据值不能为空");
    	manageService.updateManage(managePojo);
    	return WrapMapper.ok();
    }
    
    /**
     * 删除配置
     */
    @ApiOperation(value = "删除配置")
    @PostMapping("/deleteManage")
    public Wrapper deleteManage(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam Long id) {
    	manageService.delete(id);
        return WrapMapper.ok();
    }





}
