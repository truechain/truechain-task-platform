package com.truechain.task.admin.service.impl;

import com.google.common.collect.Maps;
import com.querydsl.core.BooleanBuilder;
import com.truechain.task.admin.model.dto.UserDTO;
import com.truechain.task.admin.repository.BsRecommendTaskRepository;
import com.truechain.task.admin.service.BsRecommendTaskService;
import com.truechain.task.model.entity.BsRecommendTask;
import com.truechain.task.model.entity.BsUserAccountDetail;
import com.truechain.task.model.entity.QBsRecommendTask;
import com.truechain.task.model.entity.QBsUserAccountDetail;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class BsRecommendTaskServiceImpl implements BsRecommendTaskService {

    @Autowired
    private BsRecommendTaskRepository bsRecommendTaskRepository;

    @Override
    public Map<Long,BsRecommendTask> getMyRecommendUser(Collection<Long> ids) {
        List<BsRecommendTask> bsRecommendTasks = bsRecommendTaskRepository.getMyRecommendUser(ids);
        Map<Long,BsRecommendTask> map = Maps.newHashMap();
        bsRecommendTasks.forEach(item->{
            map.put(item.getRecommendUser().getId(),item);
        });
        return map;
    }

    @Override
    public Map<Long,Integer> getMyRecommendCount(Collection<Long> ids) {
        Map<Long,Integer> result = Maps.newHashMap();
        List<Object> objects = bsRecommendTaskRepository.getMyRecommendCount(ids);
        objects.forEach(obj->{
            Object[] objArray = (Object[])obj;
            Long id = Long.parseLong(objArray[0].toString());
            Integer count = Integer.parseInt(objArray[1].toString());
            result.put(id,count);
        });
        return result;
    }

    @Override
    public Page<BsRecommendTask> getRecommendTask(UserDTO userDTO, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        QBsRecommendTask qBsRecommendTask = QBsRecommendTask.bsRecommendTask;
        if (userDTO.getId() != null) {
            builder.and(qBsRecommendTask.recommendUser.id.eq(userDTO.getId()));                 //推荐人id
        }
        if (StringUtils.isNotBlank(userDTO.getStartDate())) {
            builder.and(qBsRecommendTask.updateTime.gt(userDTO.getStartDate()));
        }
        if (StringUtils.isNotBlank(userDTO.getEndDate())) {
            builder.and(qBsRecommendTask.updateTime.lt(userDTO.getEndDate()));
        }
        if (StringUtils.isNotBlank(userDTO.getName())) {       //被推荐人名称
            builder.and(qBsRecommendTask.user.personName.likeIgnoreCase("%" + userDTO.getName() + "%"));
        }
        if (StringUtils.isNotBlank(userDTO.getName())) {       //被推荐人wx名称
            builder.and(qBsRecommendTask.user.wxNickName.likeIgnoreCase("%" + userDTO.getWxNickName() + "%"));
        }
        if (StringUtils.isNotBlank(userDTO.getLevel())) {       //被推荐人level
            builder.and(qBsRecommendTask.user.level.eq(userDTO.getLevel()));
        }
        Page<BsRecommendTask> taskPage = bsRecommendTaskRepository.findAll(builder, pageable);
        return taskPage;
    }
}
