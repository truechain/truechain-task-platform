package com.truechain.task.admin.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import com.querydsl.core.BooleanBuilder;
import com.truechain.task.model.entity.QConfigManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Preconditions;
import com.truechain.task.admin.model.dto.ManageDTO;
import com.truechain.task.admin.model.viewPojo.ManagePojo;
import com.truechain.task.admin.repository.ConfigManageRepository;
import com.truechain.task.admin.service.ManageService;
import com.truechain.task.model.entity.ConfigManage;
import com.truechain.task.util.JsonUtil;

@Service
public class ManageServiceImpl implements ManageService {

	public static class ManagePojoConverter implements Converter<ConfigManage,ManagePojo>{

		@Override
		public ManagePojo convert(ConfigManage source) {
			ManagePojo managePojo = new ManagePojo();
			managePojo.setId(source.getId());
			managePojo.setManageName(source.getManageName());
			if(source.getConfigType()==0){
				Long num=Long.parseLong(source.getConfigData());
				managePojo.setConfigdata(num);
			}
			if(source.getConfigType()==1){
				managePojo.setConfigdata(source.getConfigData());
			}
			if(source.getConfigType()==2) {
				List<ManagePojo.Option> oList = JsonUtil.parseObject(source.getConfigData(), new TypeReference<List<ManagePojo.Option>>() {
				});
				managePojo.setConfigdata(oList);
			}
			managePojo.setConfigType(source.getConfigType());
			managePojo.setType(source.getTypeName());

			return managePojo;
		}
	}

	@Autowired
	private ConfigManageRepository configManageRepository;
	@Override
	public Page<ManagePojo> getTaskPage(ManageDTO manageDTO, Pageable pageable) {
		BooleanBuilder builder = new BooleanBuilder();
		QConfigManage qConfigManage = QConfigManage.configManage;
		if (null != manageDTO.getConfigType()) {
			builder.and(qConfigManage.configType.eq(manageDTO.getConfigType()));
		}
		if(StringUtils.isNotBlank(manageDTO.getTypeName())){
			builder.and(qConfigManage.typeName.eq(manageDTO.getTypeName()));
		}
		Page<ConfigManage> managePage=configManageRepository.findAll(builder,pageable);
		Page<ManagePojo> managePojoPage = managePage.map(new ManagePojoConverter());		
		return managePojoPage;
	}

	@Override
	public ManagePojo getManageInfo(Long manageId) {
		ConfigManage manage = configManageRepository.findOne(manageId);
		ManagePojo managePojo = new ManagePojo();
		managePojo.setId(manage.getId());
		managePojo.setManageName(manage.getManageName());
		managePojo.setType(manage.getTypeName());
		managePojo.setConfigType(manage.getConfigType());
		if(manage.getConfigType()==0){
			Long num=Long.parseLong(manage.getConfigData());
			managePojo.setConfigdata(num);
		}
		if(manage.getConfigType()==1)
		{
			managePojo.setConfigdata(manage.getConfigData());
		}
		if(manage.getConfigType()==2) {
			List<ManagePojo.Option> oList = JsonUtil.parseObject(manage.getConfigData(), new TypeReference<List<ManagePojo.Option>>() {
			});
			managePojo.setConfigdata(oList);
		}
		return managePojo;
	}
	@Override
	public ManagePojo addManage(ManagePojo managePojo) {
		ConfigManage manage = new ConfigManage();
		manage.setManageName(managePojo.getManageName());
		manage.setTypeName(managePojo.getType());
		if(managePojo.getConfigdata() !=null){
			if(managePojo.getConfigType()==0){
				manage.setConfigData(String.valueOf(managePojo.getConfigdata()));
			}
			if(managePojo.getConfigType()==1)
			{
				manage.setConfigData(String.valueOf(managePojo.getConfigdata()));
			}
			if(managePojo.getConfigType()==2) {
				String configData = JsonUtil.toJsonString(managePojo.getConfigdata());
				manage.setConfigData(configData);
			}
		}
		manage.setConfigType(managePojo.getConfigType());
		configManageRepository.save(manage);
		managePojo.setId(manage.getId());
		return managePojo;
	}
	@Override
	public ManagePojo updateManage(ManagePojo managePojo) {
		ConfigManage manage = configManageRepository.findOne(managePojo.getId());
		Preconditions.checkArgument(null != manage, "该任务不存在");
		manage.setManageName(managePojo.getManageName());
		manage.setConfigType(managePojo.getConfigType());
		if(managePojo.getConfigdata() !=null){
			if(managePojo.getConfigType()==0){
				manage.setConfigData(String.valueOf(managePojo.getConfigdata()));

			}
			if(managePojo.getConfigType()==1)
			{
				manage.setConfigData(String.valueOf(managePojo.getConfigdata()));
			}
			if(managePojo.getConfigType()==2) {
				String configData = JsonUtil.toJsonString(managePojo.getConfigdata());
				manage.setConfigData(configData);
			}
		}
		manage.setTypeName(managePojo.getType());
		configManageRepository.save(manage);
		return managePojo;
	}
}