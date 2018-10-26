package com.truechain.task.admin.service.impl;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
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
			//            managePojo.setConfigdata(item.getConfigData());
			List<ManagePojo.Option> oList = JsonUtil.parseObject(source.getConfigData(), new TypeReference<List<ManagePojo.Option>>() {	});
			managePojo.setConfigdataByList(oList);
			managePojo.setType(source.getConfigType());           

			return managePojo;
		}
	}

	@Autowired
	private ConfigManageRepository configManageRepository;
	@Override
	public Page<ManagePojo> getTaskPage(ManageDTO manageDTO, Pageable pageable) {
		Page<ConfigManage> managePage=configManageRepository.findAll(pageable);
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
		List<ManagePojo.Option> oList = JsonUtil.parseObject(manage.getConfigData(), new TypeReference<List<ManagePojo.Option>>() {	});
		managePojo.setConfigdataByList(oList);
		return managePojo;
	}
	@Override
	public ManagePojo addManage(ManagePojo managePojo) {
		ConfigManage manage = new ConfigManage();
		manage.setName(managePojo.getName());
		manage.setConfigType(managePojo.getType());
		if(managePojo.getConfigdataByList() !=null){
			String configData = JsonUtil.toJsonString(managePojo.getConfigdataByList());
			manage.setConfigData(configData);
		}
		configManageRepository.save(manage);
		managePojo.setId(manage.getId());
		return managePojo;
	}
	@Override
	public ManagePojo updateManage(ManagePojo managePojo) {
		ConfigManage manage = configManageRepository.findOne(managePojo.getId());
		Preconditions.checkArgument(null != manage, "该任务不存在");
		manage.setName(managePojo.getName());
		if(managePojo.getConfigdataByList() !=null){
			String configData = JsonUtil.toJsonString(managePojo.getConfigdataByList());
			manage.setConfigData(configData);
		}
		configManageRepository.save(manage);
		return managePojo;
	}
}
