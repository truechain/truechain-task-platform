package com.truechain.task.admin.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.truechain.task.admin.model.dto.TaskDTO;
import com.truechain.task.admin.repository.BsTaskUserRepository;
import com.truechain.task.admin.service.BsTaskUserService;
import com.truechain.task.model.entity.BsTaskUser;
import com.truechain.task.model.entity.QBsTaskUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class BsTaskUserServiceImpl implements BsTaskUserService {

    @Autowired
    private BsTaskUserRepository bsTaskUserRepository;

    @Override
    public List<BsTaskUser> getBsTaskUserByUserIds(Collection<Long> userIds) {
        return bsTaskUserRepository.findByUserIds(userIds);
    }

    @Override
    public Page<BsTaskUser> getBsTaskUser(TaskDTO task, Pageable pageable){
        BooleanBuilder builder = new BooleanBuilder();
        QBsTaskUser qBsTaskUser = QBsTaskUser.bsTaskUser;

        if (task.getUserId() != null && task.getUserId() > 0) {       //名称
            builder.and(qBsTaskUser.user.id.eq(task.getUserId()));
        }
        if (StringUtils.isNotBlank(task.getName())) {       //名称
            builder.and(qBsTaskUser.taskDetail.task.name.likeIgnoreCase("%" + task.getName() + "%"));
        }
        if (null != task.getTaskStatus()) {                 //状态(0-任务中,1-已经完成)
            builder.and(qBsTaskUser.taskStatus.eq(task.getTaskStatus()));
        }
        if (StringUtils.isNotBlank(task.getLevel())) {          //等级
            builder.and(qBsTaskUser.taskDetail.task.level.eq(task.getLevel()));
        }
        if (null != task.getCategory()) {                //类别
            builder.and(qBsTaskUser.taskDetail.task.category.eq(task.getCategory()));
        }

        if (StringUtils.isNotBlank(task.getStartDateTime())) {          //抢任务时间
            builder.and(qBsTaskUser.createTime.gt(task.getStartDateTime()));
        }
        if (StringUtils.isNotBlank(task.getEndDateTime())) {            //抢任务时间
            builder.and(qBsTaskUser.createTime.lt(task.getEndDateTime()));
        }

        Page<BsTaskUser> taskPage = bsTaskUserRepository.findAll(builder, pageable);
        return taskPage;
    }

    @Override
    public long getBsTaskUserCount(TaskDTO task){
        BooleanBuilder builder = new BooleanBuilder();
        QBsTaskUser qBsTaskUser = QBsTaskUser.bsTaskUser;
        if (null != task.getTaskStatus()) {                 //状态
            builder.and(qBsTaskUser.taskStatus.eq(task.getTaskStatus()));
        }

        if (StringUtils.isNotBlank(task.getStartDateTime())) {          //抢任务时间
            builder.and(qBsTaskUser.createTime.gt(task.getStartDateTime()));
        }
        if (StringUtils.isNotBlank(task.getEndDateTime())) {            //抢任务时间
            builder.and(qBsTaskUser.createTime.lt(task.getEndDateTime()));
        }

        long count = bsTaskUserRepository.count(builder);
        return count;
    }
}
