package com.truechain.task.admin.service.impl;

import com.google.common.base.Preconditions;
import com.querydsl.core.BooleanBuilder;
import com.truechain.task.admin.model.dto.*;
import com.truechain.task.admin.repository.*;
import com.truechain.task.admin.service.TaskService;
import com.truechain.task.core.BusinessException;
import com.truechain.task.model.entity.*;
import com.truechain.task.model.enums.TaskStatusEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    @Autowired
    private BsRecommendTaskRepository bsRecommendTaskRepository;

    @Autowired
    private BsUserAccountRepository userAccountRepository;

    @Autowired
    private BsUserAccountDetailRepository userAccountDetailRepository;

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
        String iconPath = task.getIconPath();
        if (StringUtils.isNotBlank(iconPath)) {
            iconPath = iconPath.substring(iconPath.lastIndexOf("/"));
            task.setIconPath("http://phptrain.cn/taskicon" + iconPath);
        }
        taskInfoDTO.setTask(task);
        Set<BsTaskDetail> taskDetailList = task.getTaskDetailSet();
        taskInfoDTO.setTaskDetailList(taskDetailList);
        return taskInfoDTO;
    }

    @Override
    public TaskEntryFromDTO getEntryFormInfo(Long taskId) {
        BsTask task = taskRepository.findOne(taskId);
        Preconditions.checkArgument(null != task, "任务不存在");
        TaskEntryFromDTO taskEntryFrom = new TaskEntryFromDTO();
        taskEntryFrom.setTaskName(task.getName());
        taskEntryFrom.setRewardType(task.getRewardType());
        taskEntryFrom.setTotalAuditStatus(1);
        QBsTaskUser qBsTaskUser = QBsTaskUser.bsTaskUser;
        Iterable<BsTaskUser> taskUserIterable = taskUserRepository.findAll(qBsTaskUser.taskDetail.task.eq(task));
        List<TaskEntryFromInfoDTO> taskEntryFromDTOList = new ArrayList<>();
        taskUserIterable.forEach(x -> {
            TaskEntryFromInfoDTO taskEntryFromDTO = new TaskEntryFromInfoDTO();
            taskEntryFromDTO.setTaskUserId(x.getId());
            SysUser user = x.getUser();
            taskEntryFromDTO.setPersonName(user.getPersonName());
            taskEntryFromDTO.setWxNickName(user.getWxNickName());
            taskEntryFromDTO.setAuditStatus(x.getAuditStatus());
            if (taskEntryFromDTO.getAuditStatus() == 0 && taskEntryFrom.getTotalAuditStatus() != 0) {
                taskEntryFrom.setTotalAuditStatus(0);
            }
            taskEntryFromDTO.setRewardNum(x.getRewardNum());
            taskEntryFromDTO.setPushAddress(x.getPushAddress());
            taskEntryFromDTO.setRemark(x.getRemark());
            taskEntryFromDTO.setStation(x.getTaskDetail().getStation());
            taskEntryFromDTOList.add(taskEntryFromDTO);
        });
        taskEntryFrom.setTaskEntryFromInfoList(taskEntryFromDTOList);
        return taskEntryFrom;
    }

    @Override
    @Transactional
    public BsTask addTask(TaskInfoDTO taskInfoDTO) {
        BsTask task = taskInfoDTO.getTask();
        double totalRewardNum = taskInfoDTO.getTaskDetailList().stream().mapToDouble(x -> x.getPeopleNum() * x.getRewardNum().doubleValue()).sum();
        if (task.getRewardNum().doubleValue() != totalRewardNum) {
            throw new BusinessException("任务奖励总和和明细项不匹配");
        }
        long peopleNum = taskInfoDTO.getTaskDetailList().stream().mapToInt(x -> x.getPeopleNum()).sum();
        task.setPeopleNum((int) peopleNum);
        task.setAuditStatus(0);
        task = taskRepository.save(task);
        for (BsTaskDetail taskDetail : taskInfoDTO.getTaskDetailList()) {
            taskDetail.setTask(task);
            taskDetailRepository.save(taskDetail);
        }
        return task;
    }

    @Override
    @Transactional
    public BsTask updateTask(TaskInfoDTO taskInfoDTO) {
        BsTask task = taskInfoDTO.getTask();
        BsTask bsTask = taskRepository.findOne(task.getId());
        Preconditions.checkArgument(null != bsTask, "该任务不存在");
        Preconditions.checkArgument(1 == bsTask.getTaskStatus(), "任务不是启用状态");
        QBsTaskDetail qBsTaskDetail = QBsTaskDetail.bsTaskDetail;
        long count = taskDetailRepository.count(qBsTaskDetail.task.eq(task).and(qBsTaskDetail.taskUserSet.isNotEmpty()));
        Preconditions.checkArgument(count <= 0, "任务已经有人在执行");
        long peopleNum = taskInfoDTO.getTaskDetailList().stream().mapToInt(x -> x.getPeopleNum()).sum();
        task.setPeopleNum((int) peopleNum);
        bsTask = taskRepository.save(bsTask);
        taskDetailRepository.deleteByTask(bsTask);
        for (BsTaskDetail taskDetail : taskInfoDTO.getTaskDetailList()) {
            taskDetail.setTask(task);
            taskDetailRepository.save(taskDetail);
        }
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
    @Transactional
    public void auditEntryFormUser(Long taskUserId) {
        BsTaskUser taskUser = taskUserRepository.findOne(taskUserId);
        Preconditions.checkArgument(null != taskUser, "数据有误");
        taskUser.setAuditStatus(1);
        taskUserRepository.save(taskUser);
        BsTaskDetail bsTaskDetail = taskUser.getTaskDetail();
        BsTask bsTask = bsTaskDetail.getTask();
        QBsTaskUser qBsTaskUser = QBsTaskUser.bsTaskUser;
        long count = taskUserRepository.count(qBsTaskUser.taskDetail.eq(bsTaskDetail).and(qBsTaskUser.auditStatus.eq(1)));
        if (count == bsTask.getPeopleNum()) {
            bsTask.setAuditStatus(1);
            taskRepository.save(bsTask);
        }
    }

    @Override
    @Transactional
    public BsTaskUser rewardEntryFromUser(Long taskUserId) {
        BsTaskUser taskUser = taskUserRepository.findOne(taskUserId);
        Preconditions.checkArgument(null != taskUser, "数据有误");
        if (taskUser.getAuditStatus() == 0) {
            throw new BusinessException("数据尚未审核");
        }
        if (taskUser.getAuditStatus() == 2) {
            throw new BusinessException("奖励已经发放，不可重复发放");
        }
        taskUser.setAuditStatus(2);
        taskUser = taskUserRepository.save(taskUser);
        //发放奖励
        SysUser user = taskUser.getUser();
        BsUserAccount userAccount = userAccountRepository.getByUser(user);
        Preconditions.checkArgument(null != userAccount, "用户账户不存在");
        BsTaskDetail taskDetail = taskUser.getTaskDetail();
        BsTask task = taskDetail.getTask();
        BigDecimal rewardNum = null;
        switch (task.getRewardType()) {
            case 1:
                rewardNum = userAccount.getTrueReward().add(taskDetail.getRewardNum());
                userAccount.setTrueReward(rewardNum);
                break;
            case 2:
                rewardNum = userAccount.getTtrReward().add(taskDetail.getRewardNum());
                userAccount.setTtrReward(rewardNum);
                break;
            case 3:
                rewardNum = userAccount.getGitReward().add(taskDetail.getRewardNum());
                userAccount.setGitReward(rewardNum);
                break;
        }
        userAccountRepository.save(userAccount);
        BsUserAccountDetail userAccountDetail = new BsUserAccountDetail();
        userAccountDetail.setUserAccount(userAccount);
        userAccountDetail.setTask(task);
        userAccountDetail.setRewardType(task.getRewardType());
        userAccountDetail.setRewardNum(taskDetail.getRewardNum());
        userAccountDetailRepository.save(userAccountDetail);
        return taskUser;
    }

    @Override
    public long countTotalTask() {
        return taskRepository.count();
    }

    @Override
    public long countComplateTask() {
        throw new UnsupportedOperationException();
    }

    public Page<BsRecommendTask> getBsRecommendTaskList(UserDTO user, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        QBsRecommendTask qbsRecommendTask = QBsRecommendTask.bsRecommendTask;
        if (user.getId() != null && user.getId() > 0) {
            builder.and(qbsRecommendTask.recommendUser.id.eq(user.getId()));
        }

        if (StringUtils.isNotBlank(user.getName())) {
            builder.and(qbsRecommendTask.user.personName.eq(user.getName()));
        }
//        if (StringUtils.isNotBlank(user.getLevel())) {
//            builder.and(qbsRecommendTask.recommendUser.level.eq("'A'"));
//        }
        if (StringUtils.isNotBlank(user.getWxNickName())) {
            builder.and(qbsRecommendTask.user.level.eq(user.getWxNickName()));
        }
        if (StringUtils.isNotBlank(user.getStartDate())) {
            builder.and(qbsRecommendTask.createTime.gt(user.getStartDate()));
        }
        if (StringUtils.isNotBlank(user.getEndDate())) {
            builder.and(qbsRecommendTask.createTime.lt(user.getEndDate()));
        }

        Page<BsRecommendTask> result = bsRecommendTaskRepository.findAll(builder, pageable);
        return result;
    }
}
