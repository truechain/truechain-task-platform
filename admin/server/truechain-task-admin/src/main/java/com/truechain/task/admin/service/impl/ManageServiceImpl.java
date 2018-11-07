package com.truechain.task.admin.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.querydsl.core.BooleanBuilder;
import com.truechain.task.admin.model.dto.ManageDTO;
import com.truechain.task.admin.model.viewPojo.ManagePojo;
import com.truechain.task.admin.repository.ConfigManageRepository;
import com.truechain.task.admin.service.ManageService;
import com.truechain.task.core.BusinessException;
import com.truechain.task.model.entity.ConfigManage;
import com.truechain.task.model.entity.QConfigManage;
import com.truechain.task.util.DateUtil;

@Service
public class ManageServiceImpl implements ManageService {

	public static class ManagePojoConverter implements Converter<ConfigManage,ManagePojo>{

		@Override
		public ManagePojo convert(ConfigManage source) {
			ManagePojo managePojo = new ManagePojo();
			managePojo.setId(source.getId());
			managePojo.setManageName(source.getManageName());
//			if(source.getConfigType()==0){
//				try{
//					Long num=Long.parseLong(source.getConfigData());
//					managePojo.setConfigData(num);
//				}catch(Exception e){
//					throw new BusinessException("数据记录Id="+source.getId()+",configType=0时,configData="+source.getConfigData()+"不为Num类型");
//				}
//			}
//			if(source.getConfigType()==1){
//				managePojo.setConfigData(source.getConfigData());
//			}
//			if(source.getConfigType()==2) {
//				try{
//					List<ManagePojo.Option> oList = JsonUtil.parseObject(source.getConfigData(), new TypeReference<List<ManagePojo.Option>>(){});
//					managePojo.setConfigData(oList);
//				}catch(Exception e){
//					throw new BusinessException("数据记录Id="+source.getId()+",configType=2时,configData="+source.getConfigData()+"不为枚举类型");
//				}
//			}
			managePojo.setConfigData(source.getConfigData());
			managePojo.setConfigType(source.getConfigType());
			managePojo.setTypeName(source.getTypeName());
			managePojo.setDatetime(source.getUpdateTime());
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
		managePojo.setTypeName(manage.getTypeName());
		managePojo.setConfigType(manage.getConfigType());		
//		if(manage.getConfigType()==0){
//			Long num=Long.parseLong(manage.getConfigData());
//			managePojo.setConfigData(num);
//		}else if(manage.getConfigType()==1)
//		{
//			managePojo.setConfigData(manage.getConfigData());
//		}else if(manage.getConfigType()==2) {
//			List<ManagePojo.Option> oList = JsonUtil.parseObject(manage.getConfigData(), new TypeReference<List<ManagePojo.Option>>() {
//			});
//			managePojo.setConfigData(oList);
//		}
		managePojo.setConfigData(manage.getConfigData());
		return managePojo;
	}
	@Override
	public ManagePojo addManage(ManagePojo managePojo) {
		ConfigManage manageDb = configManageRepository.findByTypeName(managePojo.getTypeName());
		if(manageDb != null){
			throw new BusinessException("type="+managePojo.getTypeName()+"已存在");
		}
		ConfigManage manage = new ConfigManage();
		manage.setManageName(managePojo.getManageName());
		manage.setTypeName(managePojo.getTypeName());
//		if(managePojo.getConfigData() !=null){
//			if(managePojo.getConfigType()==0){
//				if(managePojo.getConfigData() instanceof Number){
//					manage.setConfigData(String.valueOf(managePojo.getConfigData()));
//				}else{
//					throw new BusinessException("configType=0时,configData必须为Num类型");
//				}
//			}
//			if(managePojo.getConfigType()==1)
//			{
//				if(managePojo.getConfigData() instanceof String){
//					manage.setConfigData(String.valueOf(managePojo.getConfigData()));
//				}else{
//					throw new BusinessException("configType=1时,configData必须为String类型");
//				}
//			}
//			if(managePojo.getConfigType()==2) {
//				if(managePojo.getConfigData() instanceof List){
//					String configData = JsonUtil.toJsonString(managePojo.getConfigData());
//					manage.setConfigData(configData);
//				}else{
//					throw new BusinessException("configType=2时,configData必须为枚举类型");
//				}
//			}
//		}
		manage.setConfigData(managePojo.getConfigData());
		manage.setConfigType(managePojo.getConfigType());
		String date = DateUtil.getDate();
		manage.setCreateTime(date);
		manage.setUpdateTime(date);
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
//		if(managePojo.getConfigData() !=null){			
//			if(managePojo.getConfigType()==0){
//				if(managePojo.getConfigData() instanceof Number){
//					manage.setConfigData(String.valueOf(managePojo.getConfigData()));
//				}else{
//					throw new BusinessException("configType=0时,configData必须为Num类型");
//				}
//			}
//			if(managePojo.getConfigType()==1)
//			{
//				if(managePojo.getConfigData() instanceof String){
//					manage.setConfigData(String.valueOf(managePojo.getConfigData()));
//				}else{
//					throw new BusinessException("configType=1时,configData必须为String类型");
//				}
//			}
//			if(managePojo.getConfigType()==2) {
//				if(managePojo.getConfigData() instanceof List){
//					String configData = JsonUtil.toJsonString(managePojo.getConfigData());
//					manage.setConfigData(configData);
//				}else{
//					throw new BusinessException("configType=2时,configData必须为枚举类型");
//				}
//			}
//		}
		manage.setConfigData(managePojo.getConfigData());
		manage.setTypeName(managePojo.getTypeName());
		String date = DateUtil.getDate();
		manage.setUpdateTime(date);
		configManageRepository.save(manage);
		return managePojo;
	}
	
	
	@Override
    public void delete(Long id) {
		configManageRepository.delete(id);
    }
}
