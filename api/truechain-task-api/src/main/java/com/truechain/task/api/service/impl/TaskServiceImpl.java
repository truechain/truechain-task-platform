package com.truechain.task.api.service.impl;

import com.google.common.base.Preconditions;
import com.querydsl.core.BooleanBuilder;
import com.truechain.task.api.model.dto.TaskDTO;
import com.truechain.task.api.model.dto.TaskTotalDTO;
import com.truechain.task.api.model.dto.UserTaskInfoDTO;
import com.truechain.task.api.repository.BsTaskDetailRepository;
import com.truechain.task.api.repository.BsTaskRepository;
import com.truechain.task.api.repository.BsTaskUserRepository;
import com.truechain.task.api.repository.SysUserRepository;
import com.truechain.task.api.service.TaskService;
import com.truechain.task.core.BusinessException;
import com.truechain.task.model.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
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
                pageable = new PageRequest(pageIndex - 1, pageSize, Sort.Direction.ASC, "rewardNum");
            } else {
                pageable = new PageRequest(pageIndex - 1, pageSize, Sort.Direction.DESC, "rewardNum");
            }
        } else {
            pageable = new PageRequest(pageIndex - 1, pageSize);
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
                taskDTO.setUserLevel(user.getLevel());
                taskDTO.setIsLevelEnough(1);
                Preconditions.checkArgument(null != user, "用户不存在");
                QBsTaskDetail qtaskDetail = QBsTaskDetail.bsTaskDetail;
                long exists = taskDetailRepository.count(qtaskDetail.id.eq(taskDetail.getId()).and(qtaskDetail.taskUserSet.any().user.eq(user)));
                if (exists > 0) {
                    taskDTO.setIsHold(1);
                    taskDetail.setIsHold(1);
                } else {
                    if (user.getLevel().compareTo(task.getLevel()) > 0) {
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
        long count = taskUserRepository.count(qTaskUser.user.id.eq(userId));
        taskTotalDTO.setTaskTotal(count);
        count = taskUserRepository.count(qTaskUser.user.id.eq(userId).and(qTaskUser.status.eq(0)));
        taskTotalDTO.setTaskingTotal(count);
        count = taskUserRepository.count(qTaskUser.user.id.eq(userId).and(qTaskUser.status.eq(1)));
        taskTotalDTO.setTaskComplateTolal(count);
        BooleanBuilder builder = new BooleanBuilder();
        if (null != taskStatus) {
            builder.and(qTaskUser.user.id.eq(userId).and(qTaskUser.status.eq(taskStatus)));
        }
        Iterable<BsTaskUser> taskUserIterable = taskUserRepository.findAll(builder);
        taskTotalDTO.setTaskList(new ArrayList<>());
        Iterator<BsTaskUser> iterable = taskUserIterable.iterator();
        while (iterable.hasNext()) {
            BsTaskUser taskUser = iterable.next();
            BsTask task = taskUser.getTaskDetail().getTask();
            task.setTaskStatus(taskUser.getStatus());
            taskTotalDTO.getTaskList().add(task);
        }
        return taskTotalDTO;
    }

    @Override
    public UserTaskInfoDTO getUserTaskInfo(Long userId, Long taskId) {
        UserTaskInfoDTO userTaskInfoDTO = new UserTaskInfoDTO();
        SysUser user = userRepository.findOne(userId);
        Preconditions.checkArgument(user != null, "用户不存在");
        Set<BsTaskUser> taskUserSet = user.getTaskUserSet();
        Preconditions.checkArgument(!CollectionUtils.isEmpty(taskUserSet), "用户尚无任务");
        Optional<BsTaskUser> taskUser = taskUserSet.stream().filter(x -> x.getTaskDetail().getTask().getId().equals(taskId)).findFirst();
        Preconditions.checkArgument(taskUser.isPresent(), "该任务不存在");
        userTaskInfoDTO.setTaskCompleteInfo(taskUser.get());
        userTaskInfoDTO.setTask(taskUser.get().getTaskDetail().getTask());
        return userTaskInfoDTO;
    }

    @Transactional
    @Override
    public void holdTask(Long taskDetailId, Long userId) {
        SysUser user = userRepository.findOne(userId);
        Preconditions.checkArgument(null != user, "用户不存在");
        BsTaskDetail taskDetail = taskDetailRepository.findOne(taskDetailId);
        Preconditions.checkArgument(null != taskDetail, "任务不存在");
        QBsTaskDetail qtaskDetail = QBsTaskDetail.bsTaskDetail;
        long count = taskDetailRepository.count(qtaskDetail.id.eq(taskDetailId).and(qtaskDetail.taskUserSet.any().id.eq(userId)));
        if (count > 0) {
            throw new BusinessException("该用户已经抢到任务");
        }
        count = taskDetailRepository.count(qtaskDetail.taskUserSet.isNotEmpty());
        if (count >= taskDetail.getPeopleNum()) {
            throw new BusinessException("任务已满");
        }
        BsTaskUser taskUser = new BsTaskUser();
        taskUser.setTaskDetail(taskDetail);
        taskUser.setUser(user);
        taskUserRepository.save(taskUser);
    }

    @Override
    public void commitUserTask(Long userId, Long taskId, String commitAddress, String remark) {
        BsTask task = taskRepository.findOne(taskId);
        Preconditions.checkArgument(null != task, "任务不存在");
        QBsTaskUser qTaskUser = QBsTaskUser.bsTaskUser;
        BsTaskUser taskUser = taskUserRepository.findOne(qTaskUser.user.id.eq(userId).and(qTaskUser.taskDetail.task.eq(task)));
        Preconditions.checkArgument(null != taskUser, "用户未执行该任务");
        taskUser.setPushAddress(commitAddress);
        taskUser.setRemark(remark);
        taskUser.setStatus(1);
        taskUserRepository.save(taskUser);
    }
}
