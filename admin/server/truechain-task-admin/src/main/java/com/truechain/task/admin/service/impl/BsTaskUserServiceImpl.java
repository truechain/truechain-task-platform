package com.truechain.task.admin.service.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.querydsl.core.BooleanBuilder;
import com.truechain.task.admin.model.dto.AuditBsTaskUserDTO;
import com.truechain.task.admin.model.dto.TaskDTO;
import com.truechain.task.admin.repository.BsTaskUserRepository;
import com.truechain.task.admin.service.BsTaskUserService;
import com.truechain.task.model.entity.BsTaskDetail;
import com.truechain.task.model.entity.BsTaskUser;
import com.truechain.task.model.entity.QBsTaskUser;

@Service
public class BsTaskUserServiceImpl implements BsTaskUserService {

	@Autowired
	private BsTaskUserRepository bsTaskUserRepository;

	@Override
	public List<BsTaskUser> getBsTaskUserByUserIds(Collection<Long> userIds) {
		return bsTaskUserRepository.findByUserIds(userIds);
	}

	@Override
	public Page<BsTaskUser> getBsTaskUser(TaskDTO task, Pageable pageable) {
		BooleanBuilder builder = new BooleanBuilder();
		QBsTaskUser qBsTaskUser = QBsTaskUser.bsTaskUser;

		if (task.getUserId() != null && task.getUserId() > 0) { // 名称
			builder.and(qBsTaskUser.user.id.eq(task.getUserId()));
		}
		if (StringUtils.isNotBlank(task.getName())) { // 名称
			builder.and(qBsTaskUser.taskDetail.task.name.likeIgnoreCase("%" + task.getName() + "%"));
		}
		if (null != task.getTaskStatus()) { // 状态(0-任务中,1-已经完成)
			builder.and(qBsTaskUser.taskStatus.eq(task.getTaskStatus()));
		}
		if (StringUtils.isNotBlank(task.getLevel())) { // 等级
			builder.and(qBsTaskUser.taskDetail.task.level.eq(task.getLevel()));
		}
		if (null != task.getCategory()) { // 类别
			builder.and(qBsTaskUser.taskDetail.task.category.eq(task.getCategory()));
		}

		if (StringUtils.isNotBlank(task.getStartDateTime())) { // 抢任务时间
			builder.and(qBsTaskUser.createTime.gt(task.getStartDateTime()));
		}
		if (StringUtils.isNotBlank(task.getEndDateTime())) { // 抢任务时间
			builder.and(qBsTaskUser.createTime.lt(task.getEndDateTime()));
		}

		Page<BsTaskUser> taskPage = bsTaskUserRepository.findAll(builder, pageable);
		return taskPage;
	}

	@Override
	public long getBsTaskUserCount(TaskDTO task) {
		BooleanBuilder builder = new BooleanBuilder();
		QBsTaskUser qBsTaskUser = QBsTaskUser.bsTaskUser;
		if (null != task.getTaskStatus()) { // 状态
			builder.and(qBsTaskUser.taskStatus.eq(task.getTaskStatus()));
		}

		if (StringUtils.isNotBlank(task.getStartDateTime())) { // 抢任务时间
			builder.and(qBsTaskUser.createTime.gt(task.getStartDateTime()));
		}
		if (StringUtils.isNotBlank(task.getEndDateTime())) { // 抢任务时间
			builder.and(qBsTaskUser.createTime.lt(task.getEndDateTime()));
		}

		long count = bsTaskUserRepository.count(builder);
		return count;
	}

	@Override
	public void cancelBsTaskUser(Long id) {
		BsTaskUser bsTaskUser = bsTaskUserRepository.getOne(id);
		Preconditions.checkArgument(null != bsTaskUser, "任务报名不存在");
		bsTaskUser.setTaskStatus(-1);
		bsTaskUserRepository.save(bsTaskUser);
	}

	@Override
	public void auditBsTaskUser(AuditBsTaskUserDTO auditBsTaskUserDTO) {
		BsTaskUser bsTaskUser = bsTaskUserRepository.getOne(auditBsTaskUserDTO.getId());
		Preconditions.checkArgument(null != bsTaskUser, "任务报名不存在");
		Preconditions.checkArgument(bsTaskUser.getTaskStatus() == 1, "任务报名无法审核");

		BsTaskDetail bsTaskDetail = bsTaskUser.getTaskDetail();
		Preconditions.checkArgument(bsTaskDetail.getRewardNum().floatValue() >= auditBsTaskUserDTO.getRewardNum(),
				"奖励数量异常");
		Preconditions.checkArgument(bsTaskDetail.getRewardNum().floatValue() >= auditBsTaskUserDTO.getReferralNum(),
				"奖励数量异常");
		int finishLevel = auditBsTaskUserDTO.getFinishLevel();

		if(finishLevel < 4 && finishLevel >0){
			Preconditions.checkArgument( bsTaskUserRepository.countByTaskDetailAndFinishLevel(bsTaskUser.getTaskDetail(),finishLevel) == 0,"任务评级已经被使用");  
		}
		bsTaskUser.setFinishLevel(finishLevel);
		if (finishLevel < 6 && finishLevel >0) {
			bsTaskUser.setTaskStatus(2);
			bsTaskUser.setFinishLevel(finishLevel);
			bsTaskUser.setRewardNum(new BigDecimal(auditBsTaskUserDTO.getRewardNum()));
			bsTaskUser.setReferralNum(new BigDecimal(auditBsTaskUserDTO.getRewardNum()));
		} else
			bsTaskUser.setTaskStatus(3);
		bsTaskUserRepository.save(bsTaskUser);
	}

	@Override
	public AuditBsTaskUserDTO getDefaultReward(Long id) {
		BsTaskUser bsTaskUser = bsTaskUserRepository.getOne(id);
    	AuditBsTaskUserDTO  auditBsTaskUserDTO= new AuditBsTaskUserDTO();
    	auditBsTaskUserDTO.setRewardNum(bsTaskUser.getTaskDetail().getRewardNum().floatValue());
		return auditBsTaskUserDTO;
	}
}
