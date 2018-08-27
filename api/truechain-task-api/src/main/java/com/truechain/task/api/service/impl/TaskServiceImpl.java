package com.truechain.task.api.service.impl;

import com.google.common.base.Preconditions;
import com.querydsl.core.BooleanBuilder;
import com.truechain.task.api.model.dto.TaskDTO;
import com.truechain.task.api.model.dto.TaskDetailDTO;
import com.truechain.task.api.model.dto.TaskTotalDTO;
import com.truechain.task.api.model.dto.UserTaskInfoDTO;
import com.truechain.task.api.repository.BsTaskDetailRepository;
import com.truechain.task.api.repository.BsTaskRepository;
import com.truechain.task.api.repository.BsTaskUserRepository;
import com.truechain.task.api.repository.SysUserRepository;
import com.truechain.task.api.service.TaskService;
import com.truechain.task.core.BusinessException;
import com.truechain.task.model.entity.*;
import com.truechain.task.model.enums.AuditStatusEnum;
import com.truechain.task.model.enums.TaskStatusEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

@Service
public class TaskServiceImpl extends BasicService implements TaskService {

    @Autowired
    private SysUserRepository userRepository;

    @Autowired
    private BsTaskRepository taskRepository;

    @Autowired
    private BsTaskDetailRepository taskDetailRepository;

    @Autowired
    private BsTaskUserRepository taskUserRepository;

    @Override
    public Page<BsTask> getTaskPage(BsTask task, int pageIndex, int pageSize) {
        QBsTask qTask = QBsTask.bsTask;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qTask.taskStatus.eq(TaskStatusEnum.ENABLE.getCode()));
        if (StringUtils.isNotBlank(task.getName())) {
            builder.and(qTask.name.like("%" + task.getName() + "%"));
        }
        if (null != task.getCategory()) {
            builder.and(qTask.category.eq(task.getCategory()));
        }
        if (null != task.getLevel()) {
            builder.and(qTask.level.eq(task.getLevel()));
        }
        Pageable pageable = null;
        if (null != task.getRewardType()) {
            if (task.getRewardType() == 1) {
                pageable = new PageRequest(pageIndex - 1, pageSize, Sort.Direction.ASC, "rewardNum", "updateTime");
            } else {
                pageable = new PageRequest(pageIndex - 1, pageSize, Sort.Direction.DESC, "rewardNum", "updateTime");
            }
        } else {
            pageable = new PageRequest(pageIndex - 1, pageSize, Sort.Direction.DESC, "updateTime");
        }
        Page<BsTask> taskPage = taskRepository.findAll(builder, pageable);
        return taskPage;
    }

    @Override
    public TaskDTO getTaskInfo(Long taskId, Long userId) {
        TaskDTO taskDTO = new TaskDTO();
        BsTask task = taskRepository.findOne(taskId);
        taskDTO.setTask(task);
        taskDTO.setIsHold(0);
        taskDTO.setIsFull(0);
        Set<BsTaskDetail> taskDetailSet = task.getTaskDetailSet();
        int totalPeppleNum = 0;
        for (BsTaskDetail taskDetail : taskDetailSet) {
            int count = taskUserRepository.countByTaskDetail(taskDetail);
            taskDetail.setHasPeople(count);
            if (count == taskDetail.getPeopleNum()) {
                taskDetail.setIsFull(1);
            }
            if (null != userId && taskDTO.getIsHold() == 0) {
                SysUser user = userRepository.findOne(userId);
                Preconditions.checkArgument(null != user, "用户不存在");
                taskDTO.setUserLevel(user.getLevel());
                taskDTO.setIsLevelEnough(1);
                QBsTaskDetail qtaskDetail = QBsTaskDetail.bsTaskDetail;
                long exists = taskDetailRepository.count(qtaskDetail.id.eq(taskDetail.getId()).and(qtaskDetail.taskUserSet.any().user.eq(user)));
                if (exists > 0) {
                    taskDTO.setIsHold(1);
                    taskDetail.setIsHold(1);
                } else {
                    if (StringUtils.isBlank(user.getLevel()) || user.getLevel().compareTo(task.getLevel()) > 0) {
                        taskDTO.setIsLevelEnough(0);
                    }
                }
            }
            totalPeppleNum += count;
        }
        if (totalPeppleNum >= task.getPeopleNum()) {
            taskDTO.setIsFull(1);
        }
        taskDTO.setTaskDetailList(taskDetailSet);
        return taskDTO;
    }

    @Override
    public TaskTotalDTO getUserTaskList(Long userId, Integer taskStatus) {
        TaskTotalDTO taskTotalDTO = new TaskTotalDTO();
        SysUser user = userRepository.findOne(userId);
        Preconditions.checkArgument(user != null, "用户不存在");
        QBsTaskUser qTaskUser = QBsTaskUser.bsTaskUser;
        long count = taskUserRepository.count(qTaskUser.user.eq(user));
        taskTotalDTO.setTaskTotal(count);
        count = taskUserRepository.count(qTaskUser.user.eq(user).and(qTaskUser.taskStatus.eq(0)));
        taskTotalDTO.setTaskingTotal(count);
        count = taskUserRepository.count(qTaskUser.user.eq(user).and(qTaskUser.taskStatus.eq(1)));
        taskTotalDTO.setTaskComplateTolal(count);
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qTaskUser.user.eq(user));
        if (null != taskStatus) {
            builder.and(qTaskUser.taskStatus.eq(taskStatus));
        }
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Iterable<BsTaskUser> taskUserIterable = taskUserRepository.findAll(builder, sort);
        taskTotalDTO.setTaskList(new ArrayList<>());
        Iterator<BsTaskUser> iterable = taskUserIterable.iterator();
        while (iterable.hasNext()) {
            BsTaskUser taskUser = iterable.next();
            BsTaskDetail taskDetail = taskUser.getTaskDetail();
            BsTask bsTask = taskDetail.getTask();
            BsTask task = new BsTask();
            task.setId(bsTask.getId());
            task.setName(bsTask.getName());
            task.setLevel(bsTask.getLevel());
            task.setCategory(bsTask.getCategory());
            task.setIconPath(bsTask.getIconPath());
            task.setPeopleNum(bsTask.getPeopleNum());
            task.setTaskStatus(taskUser.getTaskStatus());
            task.setTaskDetailId(taskDetail.getId());
            taskTotalDTO.getTaskList().add(task);
        }
        return taskTotalDTO;
    }

    @Override
    public UserTaskInfoDTO getUserTaskInfo(Long userId, Long taskDetailId) {
        UserTaskInfoDTO userTaskInfoDTO = new UserTaskInfoDTO();
        SysUser user = userRepository.findOne(userId);
        Preconditions.checkArgument(user != null, "用户不存在");
        BsTaskDetail taskDetail = taskDetailRepository.findOne(taskDetailId);
        Preconditions.checkArgument(taskDetail != null, "任务不存在");
        BsTask task = taskDetail.getTask();
        Preconditions.checkArgument(task != null, "任务不存在");
        userTaskInfoDTO.setTask(task);
        QBsTaskUser qBsTaskUser = QBsTaskUser.bsTaskUser;
        BsTaskUser taskUser = taskUserRepository.findOne(qBsTaskUser.user.eq(user).and(qBsTaskUser.taskDetail.eq(taskDetail)));
        userTaskInfoDTO.setTaskCompleteInfo(taskUser);
        return userTaskInfoDTO;
    }

    @Transactional
    @Override
    public void holdTask(Long taskDetailId, Long userId) {
        SysUser user = userRepository.findOne(userId);
        Preconditions.checkArgument(null != user, "用户不存在");
        Preconditions.checkArgument(user.getAuditStatus() == AuditStatusEnum.AUDITED.getCode(), "当前资料审核中暂时无法接取该任务");
        BsTaskDetail taskDetail = taskDetailRepository.findOne(taskDetailId);
        Preconditions.checkArgument(null != taskDetail, "任务不存在");
        BsTask bsTask = taskDetail.getTask();
        Preconditions.checkArgument(user.getLevel().compareTo(bsTask.getLevel()) <= 0, "您的等级不够，请选择符合您开发等级的任务");
        QBsTaskUser qtaskUser = QBsTaskUser.bsTaskUser;
        long count = taskUserRepository.count(qtaskUser.user.eq(user).and(qtaskUser.taskDetail.eq(taskDetail)));
        if (count > 0) {
            throw new BusinessException("你已抢过该任务，不可重复抢取");
        }
        count = taskUserRepository.count(qtaskUser.taskDetail.eq(taskDetail).and(qtaskUser.user.isNotNull()));
        if (count >= taskDetail.getPeopleNum()) {
            throw new BusinessException("任务已满");
        }
        BsTaskUser taskUser = new BsTaskUser();
        taskUser.setTaskDetail(taskDetail);
        taskUser.setUser(user);
        taskUserRepository.save(taskUser);
    }

    @Override
    public void commitUserTask(Long userId, TaskDetailDTO taskDetailDTO) {
        BsTaskDetail taskDetail = taskDetailRepository.findOne(taskDetailDTO.getTaskDetailId());
        Preconditions.checkArgument(null != taskDetail, "任务不存在");
        QBsTaskUser qTaskUser = QBsTaskUser.bsTaskUser;
        BsTaskUser taskUser = taskUserRepository.findOne(qTaskUser.user.id.eq(userId).and(qTaskUser.taskDetail.eq(taskDetail)));
        Preconditions.checkArgument(null != taskUser, "用户未执行该任务");
        taskUser.setPushAddress(taskDetailDTO.getCommitAddress());
        taskUser.setRemark(taskDetailDTO.getRemark());
        taskUser.setTaskStatus(1);
        taskUser.setRewardNum(taskDetail.getRewardNum());
        taskUserRepository.save(taskUser);
    }
}
