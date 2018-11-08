package com.truechain.task.admin.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.truechain.task.admin.model.dto.RewardViewDTO;
import com.truechain.task.admin.model.dto.TaskDTO;
import com.truechain.task.admin.model.dto.TimeRangeDTO;
import com.truechain.task.admin.model.dto.UserDTO;
import com.truechain.task.admin.repository.BsRecommendTaskRepository;
import com.truechain.task.admin.repository.BsUserAccountDetailRepository;
import com.truechain.task.admin.service.BsUserAccountDetailService;
import com.truechain.task.core.BusinessException;
import com.truechain.task.model.entity.BsTask;
import com.truechain.task.model.entity.BsUserAccountDetail;
import com.truechain.task.model.entity.QBsTask;
import com.truechain.task.model.entity.QBsUserAccountDetail;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BsUserAccountDetailServiceImpl implements BsUserAccountDetailService {

    @Autowired
    private BsUserAccountDetailRepository bsUserAccountDetailRepository;

    @Override
    public Page<BsUserAccountDetail> getBsUserAccountDetail(RewardViewDTO rewardViewDTO, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        QBsUserAccountDetail qBsUserAccountDetail = QBsUserAccountDetail.bsUserAccountDetail;
        if (rewardViewDTO.getUserId() != null) {
            builder.and(qBsUserAccountDetail.userAccount.user.id.eq(rewardViewDTO.getUserId()));
        }
        if (null != rewardViewDTO.getRewardResource()) {
            int channel = rewardViewDTO.getRewardResource();
//            if("完成任务".equals(rewardViewDTO.getChannel())){
//                builder.and(qBsUserAccountDetail.task.isNull());
//            }
//            if("推荐".equals(rewardViewDTO.getChannel())){
//                builder.and(qBsUserAccountDetail.recommendTask.isNull());
//            }
//            if("评级".equals(rewardViewDTO.getChannel())){
//                builder.and(qBsUserAccountDetail.task.isNull()).and(qBsUserAccountDetail.recommendTask.isNull());
//            }
            builder.and(qBsUserAccountDetail.rewardResource.eq(channel));
        }
        if ( rewardViewDTO.getRewardType() != null ) {
            int rewardType = rewardViewDTO.getRewardType();
            builder.and(qBsUserAccountDetail.rewardType.eq(rewardType));
        }

        if (StringUtils.isNotBlank(rewardViewDTO.getStartDate())) {
            builder.and(qBsUserAccountDetail.updateTime.gt(rewardViewDTO.getStartDate()));
        }
        if (StringUtils.isNotBlank(rewardViewDTO.getEndDate())) {
            builder.and(qBsUserAccountDetail.updateTime.lt(rewardViewDTO.getEndDate()));
        }

        Page<BsUserAccountDetail> taskPage = bsUserAccountDetailRepository.findAll(builder, pageable);
        return taskPage;
    }

    @Override
    public Page<BsUserAccountDetail> getRecommendTask(UserDTO userDTO, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        QBsUserAccountDetail qBsUserAccountDetail = QBsUserAccountDetail.bsUserAccountDetail;
        if (userDTO.getId() != null) {
            builder.and(qBsUserAccountDetail.userAccount.user.id.eq(userDTO.getId()));
        }
        if (StringUtils.isNotBlank(userDTO.getStartDate())) {
            builder.and(qBsUserAccountDetail.updateTime.gt(userDTO.getStartDate()));
        }
        if (StringUtils.isNotBlank(userDTO.getEndDate())) {
            builder.and(qBsUserAccountDetail.updateTime.lt(userDTO.getEndDate()));
        }
        if (StringUtils.isNotBlank(userDTO.getName())) {       //名称
            builder.and(qBsUserAccountDetail.recommendTask.user.personName.likeIgnoreCase("%" + userDTO.getName() + "%"));
        }
        if (StringUtils.isNotBlank(userDTO.getName())) {       //wx名称
            builder.and(qBsUserAccountDetail.recommendTask.user.wxNickName.likeIgnoreCase("%" + userDTO.getWxNickName() + "%"));
        }
        if (StringUtils.isNotBlank(userDTO.getLevel())) {       //level
            builder.and(qBsUserAccountDetail.recommendTask.user.level.eq(userDTO.getLevel()));
        }
        Page<BsUserAccountDetail> taskPage = bsUserAccountDetailRepository.findAll(builder, pageable);
        return taskPage;
    }

    @Override
    public Page<BsUserAccountDetail> getBsUserAccountDetail(TimeRangeDTO timeRangeDTO, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        QBsUserAccountDetail qBsUserAccountDetail = QBsUserAccountDetail.bsUserAccountDetail;

        if (StringUtils.isNotBlank(timeRangeDTO.getStartDate())) {
            builder.and(qBsUserAccountDetail.updateTime.gt(timeRangeDTO.getStartDate()));
        }
        if (StringUtils.isNotBlank(timeRangeDTO.getEndDate())) {
            builder.and(qBsUserAccountDetail.updateTime.lt(timeRangeDTO.getEndDate()));
        }

        Page<BsUserAccountDetail> taskPage = bsUserAccountDetailRepository.findAll(builder, pageable);
        return taskPage;
    }
    
    @Override
    public List<BsUserAccountDetail> getBsUserAccountDetail(Long taskId, Long userId){
        BooleanBuilder builder = new BooleanBuilder();
        QBsUserAccountDetail qBsUserAccountDetail = QBsUserAccountDetail.bsUserAccountDetail;
        builder.and(qBsUserAccountDetail.task.id.eq(taskId)).and(qBsUserAccountDetail.userAccount.user.id.eq(userId));
        List<BsUserAccountDetail> list = (List<BsUserAccountDetail>) bsUserAccountDetailRepository.findAll(builder);
        return list;
    }
    
    @Override
    public BsUserAccountDetail rewardUserAccountDetail(Long UserAccountDetailId){
    	BsUserAccountDetail buad = bsUserAccountDetailRepository.findOne(UserAccountDetailId);
    	if(buad == null){
    		throw new BusinessException("账户明细id="+UserAccountDetailId+"记录不存在");
    	}
    	buad.setRewardResource(3);  //1推荐2完成任务3评级
    	buad.setLssuingState(1);//已发放     	
    	buad.setUpdatetime();
    	return bsUserAccountDetailRepository.save(buad);
    }
}
