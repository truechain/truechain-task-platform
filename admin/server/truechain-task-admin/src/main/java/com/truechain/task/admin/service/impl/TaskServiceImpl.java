package com.truechain.task.admin.service.impl;

import com.google.common.base.Preconditions;
import com.querydsl.core.BooleanBuilder;
import com.truechain.task.admin.model.dto.TaskDTO;
import com.truechain.task.admin.model.dto.TaskEntryFromInfoDTO;
import com.truechain.task.admin.model.dto.TaskInfoDTO;
import com.truechain.task.admin.repository.BsTaskDetailRepository;
import com.truechain.task.admin.repository.BsTaskRepository;
import com.truechain.task.admin.repository.BsTaskUserRepository;
import com.truechain.task.admin.service.TaskService;
import com.truechain.task.model.entity.*;
import com.truechain.task.model.enums.TaskStatusEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private BsTaskRepository taskRepository;

    @Autowired
    private BsTaskDetailRepository taskDetailRepository;

    @Autowired
    private BsTaskUserRepository taskUserRepository;

    @Override
    public Page<BsTask> getTaskPage(TaskDTO task, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        QBsTask qTask = QBsTask.bsTask;
        if (StringUtils.isNotBlank(task.getName())) {
            builder.and(qTask.name.eq(task.getName()));
        }
        if (null != task.getTaskStatus()) {
            builder.and(qTask.taskStatus.eq(task.getTaskStatus()));
        }
        if (StringUtils.isNotBlank(task.getLevel())) {
            builder.and(qTask.level.eq(task.getLevel()));
        }
        if (null != task.getAuditStatus()) {
            builder.and(qTask.auditStatus.eq(task.getAuditStatus()));
        }
        if (null != task.getCategory()) {
            builder.and(qTask.category.eq(task.getCategory()));
        }
        if (StringUtils.isNotBlank(task.getStartDateTime())) {
            builder.and(qTask.startDateTime.gt(task.getStartDateTime()));
        }
        if (StringUtils.isNotBlank(task.getEndDateTime())) {
            builder.and(qTask.endDateTime.lt(task.getEndDateTime()));
        }
        if (StringUtils.isNotBlank(task.getPublisherName())) {
            builder.and(qTask.createUser.like("%" + task.getPublisherName() + "%"));
        }
        Page<BsTask> taskPage = taskRepository.findAll(builder, pageable);
        return taskPage;
    }

    @Override
    public TaskInfoDTO getTaskInfo(Long taskId) {
        TaskInfoDTO taskInfoDTO = new TaskInfoDTO();
        BsTask task = taskRepository.findOne(taskId);
        Preconditions.checkArgument(null != task, "任务不存在");
        taskInfoDTO.setTask(task);
        Set<BsTaskDetail> taskDetailList = task.getTaskDetailSet();
        taskInfoDTO.setTaskDetailList(taskDetailList);
        return taskInfoDTO;
    }

    @Override
    public List<TaskEntryFromInfoDTO> getEntryFormInfo(Long taskId) {
        BsTask task = taskRepository.findOne(taskId);
        Preconditions.checkArgument(null != task, "任务不存在");
        QBsTaskUser qBsTaskUser = QBsTaskUser.bsTaskUser;
        Iterable<BsTaskUser> taskUserIterable = taskUserRepository.findAll(qBsTaskUser.taskDetail.task.eq(task));
        List<TaskEntryFromInfoDTO> taskEntryFromInfoDTOList = new ArrayList<>();
        taskUserIterable.forEach(x -> {
            TaskEntryFromInfoDTO taskEntryFromInfoDTO = new TaskEntryFromInfoDTO();
            taskEntryFromInfoDTO.setTaskUserId(x.getId());
            SysUser user = x.getUser();
            taskEntryFromInfoDTO.setPersonName(user.getPersonName());
            taskEntryFromInfoDTO.setWxNickName(user.getWxNickName());
            taskEntryFromInfoDTO.setAuditStatus(x.getAuditStatus());
            taskEntryFromInfoDTO.setRewardNum(x.getRewardNum());
            taskEntryFromInfoDTO.setPushAddress(x.getPushAddress());
            taskEntryFromInfoDTO.setRemark(x.getRemark());
            taskEntryFromInfoDTO.setStation(x.getTaskDetail().getStation());
            taskEntryFromInfoDTOList.add(taskEntryFromInfoDTO);
        });
        return taskEntryFromInfoDTOList;
    }

    @Override
    public BsTask addTask(BsTask task) {
        task = taskRepository.save(task);
        return task;
    }

    @Override
    public BsTask updateTask(BsTask task) {
        BsTask bsTask = taskRepository.findOne(task.getId());
        Preconditions.checkArgument(null != bsTask, "该任务不存在");
        QBsTaskDetail qBsTaskDetail = QBsTaskDetail.bsTaskDetail;
        long count = taskDetailRepository.count(qBsTaskDetail.task.eq(task).and(qBsTaskDetail.taskUserSet.isNotEmpty()));
        Preconditions.checkArgument(count <= 0, "任务已经有人在执行");
        bsTask = taskRepository.save(bsTask);
        return bsTask;
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.delete(taskId);
    }

    @Override
    public void enableTask(Long taskId) {
        BsTask bsTask = taskRepository.findOne(taskId);
        Preconditions.checkArgument(null != bsTask, "该任务不存在");
        bsTask.setTaskStatus(TaskStatusEnum.ENABLE.getCode());
        taskRepository.save(bsTask);
    }

    @Override
    public void disableTask(Long taskId) {
        BsTask bsTask = taskRepository.findOne(taskId);
        Preconditions.checkArgument(null != bsTask, "该任务不存在");
        bsTask.setTaskStatus(TaskStatusEnum.DISABLE.getCode());
        taskRepository.save(bsTask);
    }

    @Override
    public void auditEntryFormUser(Long taskUserId) {
        BsTaskUser taskUser = taskUserRepository.findOne(taskUserId);
        Preconditions.checkArgument(null != taskUser, "数据有误");
        taskUser.setTaskStatus(1);
        taskUser.setAuditStatus(1);
        taskUserRepository.save(taskUser);
    }

    @Override
    public long countTotalTask() {
        return taskRepository.count();
    }

    @Override
    public long countComplateTask() {
        throw new UnsupportedOperationException();
    }
}
