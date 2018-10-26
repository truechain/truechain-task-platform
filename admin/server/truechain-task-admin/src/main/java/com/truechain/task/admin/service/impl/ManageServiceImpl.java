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
			managePojo.setName(source.getName());
			if(source.getTypeId()==0){
				Long num=Long.parseLong(source.getConfigData());
				managePojo.setConfigdata(num);
			}
			if(source.getTypeId()==1){
				managePojo.setConfigdata(source.getConfigData());
			}
			if(source.getTypeId()==2) {
				List<ManagePojo.Option> oList = JsonUtil.parseObject(source.getConfigData(), new TypeReference<List<ManagePojo.Option>>() {
				});
				managePojo.setConfigdata(oList);
			}
			managePojo.setTypeId(source.getTypeId());
			managePojo.setType(source.getConfigType());

			return managePojo;
		}
	}

	@Autowired
	private ConfigManageRepository configManageRepository;
	@Override
	public Page<ManagePojo> getTaskPage(ManageDTO manageDTO, Pageable pageable) {
		BooleanBuilder builder = new BooleanBuilder();
		QConfigManage qConfigManage = QConfigManage.configManage;
		if (null != manageDTO.getTypeId()) {
			builder.and(qConfigManage.typeId.eq(manageDTO.getTypeId()));
		}
		if(StringUtils.isNotBlank(manageDTO.getConfigtype())){
			builder.and(qConfigManage.configType.eq(manageDTO.getConfigtype()));
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
		managePojo.setName(manage.getName());
		managePojo.setType(manage.getConfigType());
		managePojo.setTypeId(manage.getTypeId());
		if(manage.getTypeId()==0){
			Long num=Long.parseLong(manage.getConfigData());
			managePojo.setConfigdata(num);
		}
		if(manage.getTypeId()==1)
		{
			managePojo.setConfigdata(manage.getConfigData());
		}
		if(manage.getTypeId()==2) {
			List<ManagePojo.Option> oList = JsonUtil.parseObject(manage.getConfigData(), new TypeReference<List<ManagePojo.Option>>() {
			});
			managePojo.setConfigdata(oList);
		}
		return managePojo;
	}
	@Override
	public ManagePojo addManage(ManagePojo managePojo) {
		ConfigManage manage = new ConfigManage();
		manage.setName(managePojo.getName());
		manage.setConfigType(managePojo.getType());
		if(managePojo.getConfigdata() !=null){
			if(managePojo.getTypeId()==0){
				manage.setConfigData(String.valueOf(managePojo.getConfigdata()));
			}
			if(managePojo.getTypeId()==1)
			{
				manage.setConfigData(String.valueOf(managePojo.getConfigdata()));
			}
			if(managePojo.getTypeId()==2) {
				String configData = JsonUtil.toJsonString(managePojo.getConfigdata());
				manage.setConfigData(configData);
			}
		}
		manage.setTypeId(managePojo.getTypeId());
		configManageRepository.save(manage);
		managePojo.setId(manage.getId());
		return managePojo;
	}
	@Override
	public ManagePojo updateManage(ManagePojo managePojo) {
		ConfigManage manage = configManageRepository.findOne(managePojo.getId());
		Preconditions.checkArgument(null != manage, "该任务不存在");
		manage.setName(managePojo.getName());
		manage.setTypeId(managePojo.getTypeId());
		if(managePojo.getConfigdata() !=null){
			if(managePojo.getTypeId()==0){
				manage.setConfigData(String.valueOf(managePojo.getConfigdata()));

			}
			if(managePojo.getTypeId()==1)
			{
				manage.setConfigData(String.valueOf(managePojo.getConfigdata()));
			}
			if(managePojo.getTypeId()==2) {
				String configData = JsonUtil.toJsonString(managePojo.getConfigdata());
				manage.setConfigData(configData);
			}
		}
		manage.setTypeId(managePojo.getTypeId());
		configManageRepository.save(manage);
		return managePojo;
	}
}
