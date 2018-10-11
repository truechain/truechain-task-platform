package com.truechain.task.admin.service.impl;

import com.google.common.base.Preconditions;
import com.querydsl.core.BooleanBuilder;
import com.truechain.task.admin.config.AppProperties;
import com.truechain.task.admin.model.dto.*;
import com.truechain.task.admin.repository.*;
import com.truechain.task.admin.service.TaskService;
import com.truechain.task.core.BusinessException;
import com.truechain.task.model.entity.*;
import com.truechain.task.model.enums.TaskStatusEnum;
import com.truechain.task.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private SysUserRepository userRepository;

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
        pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "createTime");
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
            task.setIconPath(AppProperties.TASK_ICON_URL + iconPath);
        }
        String description = null;
        try {
            description = URLDecoder.decode(task.getDescription(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        task.setDescription(description);
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
        taskEntryFrom.setTotalAuditStatus(task.getAuditStatus());                   //任务审核状态
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
//            if (taskEntryFromDTO.getAuditStatus() == 0 && taskEntryFrom.getTotalAuditStatus() != 0) {                       //TODO 当最后一个任务被审核通过的时候BSTask.auditTask应该被设置为审核通过,此处逻辑可不保留
//                taskEntryFrom.setTotalAuditStatus(0);
//            }
            taskEntryFromDTO.setRewardNum(x.getRewardNum());
            taskEntryFromDTO.setPushAddress(x.getPushAddress());
            taskEntryFromDTO.setRemark(x.getRemark());
            taskEntryFromDTO.setStation(x.getTaskDetail().getStation());
            String recommendUserMobile = x.getUser().getRecommendUserMobile();
            taskEntryFromDTO.setRecommendUser(recommendUserMobile);
            taskEntryFromDTOList.add(taskEntryFromDTO);
        });
//        if (taskEntryFromDTOList.size() != task.getPeopleNum()) {
//            taskEntryFrom.setTotalAuditStatus(0);
//        }
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
        String description = null;
        try {
            description = URLEncoder.encode(task.getDescription(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        task.setDescription(description);
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
        double totalRewardNum = taskInfoDTO.getTaskDetailList().stream().mapToDouble(x -> x.getPeopleNum() * x.getRewardNum().doubleValue()).sum();
        if (task.getRewardNum().doubleValue() != totalRewardNum) {
            throw new BusinessException("任务奖励总和和明细项不匹配");
        }
        BsTask bsTask = taskRepository.findOne(task.getId());
        Preconditions.checkArgument(null != bsTask, "该任务不存在");
        QBsTaskDetail qBsTaskDetail = QBsTaskDetail.bsTaskDetail;
        long count = taskDetailRepository.count(qBsTaskDetail.task.eq(task).and(qBsTaskDetail.taskUserSet.isNotEmpty()));
        Preconditions.checkArgument(count <= 0, "任务已经有人在执行");
        long peopleNum = taskInfoDTO.getTaskDetailList().stream().mapToInt(x -> x.getPeopleNum()).sum();

        bsTask.setPeopleNum((int) peopleNum);
        bsTask.setName(task.getName());
        bsTask.setLevel(task.getLevel());
        bsTask.setTaskStatus(task.getTaskStatus());
        bsTask.setCategory(task.getCategory());
        bsTask.setStartDateTime(task.getStartDateTime());
        bsTask.setEndDateTime(task.getEndDateTime());
        bsTask.setRewardType(task.getRewardType());
        bsTask.setRewardNum(task.getRewardNum());
        bsTask.setPushAddress(task.getPushAddress());
        String description = null;
        try {
            description = URLEncoder.encode(task.getDescription(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        bsTask.setDescription(description);
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
    public AuditEntryFormUserDTO auditEntryFormUser(Long taskUserId) {
        BsTaskUser taskUser = taskUserRepository.findOne(taskUserId);
        Preconditions.checkArgument(null != taskUser, "数据有误");
        Preconditions.checkArgument(1 == taskUser.getTaskStatus(), "用户尚未提交任务");
//        taskUser.setAuditStatus(1);
//        taskUserRepository.save(taskUser);
        BsTaskDetail bsTaskDetail = taskUser.getTaskDetail();
        BsTask bsTask = bsTaskDetail.getTask();
        QBsTaskUser qBsTaskUser = QBsTaskUser.bsTaskUser;
        long count = taskUserRepository.count(qBsTaskUser.taskDetail.eq(bsTaskDetail).and(qBsTaskUser.auditStatus.eq(1)));
        if (count == bsTask.getPeopleNum()) {                                                           //TODO 当最后一个任务被审核通过的时候BSTask.auditTask应该被设置为审核通过,此处逻辑应该保留
            bsTask.setAuditStatus(1);
            taskRepository.save(bsTask);
        }
        AuditEntryFormUserDTO auditUserInfo = new AuditEntryFormUserDTO();
        auditUserInfo.setTaskName(bsTask.getName());
        SysUser user = taskUser.getUser();
        auditUserInfo.setRewardNum(bsTaskDetail.getRewardNum());
        auditUserInfo.setPersonName(user.getPersonName());
        auditUserInfo.setPushAddress(user.getTrueChainAddress());
        return auditUserInfo;
    }

    @Override
    @Transactional
    public BsTaskUser rewardEntryFromUser(Long taskUserId, Double userReward, Double recommendUserReward) {
        BsTaskUser taskUser = taskUserRepository.findOne(taskUserId);
        Preconditions.checkArgument(null != taskUser, "数据有误");
//        if (taskUser.getAuditStatus() == 0) {
//            throw new BusinessException("数据尚未审核");
//        }
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

        BigDecimal userRewardNum = taskDetail.getRewardNum();
        if (userReward != null) {
            userRewardNum = new BigDecimal(userReward);
        }
        BigDecimal recommendUserRewardNum = userRewardNum.divide(new BigDecimal(10));
        if (recommendUserReward != null) {
            recommendUserRewardNum = new BigDecimal(recommendUserReward);
        }
        
        BigDecimal rewardNum = null;
        switch (task.getRewardType()) {
            case 1:
                rewardNum = userAccount.getTrueReward().add(userRewardNum);
                userAccount.setTrueReward(rewardNum);
                break;
            case 2:
                rewardNum = userAccount.getTtrReward().add(userRewardNum);
                userAccount.setTtrReward(rewardNum);
                break;
            case 3:
                rewardNum = userAccount.getGitReward().add(userRewardNum);
                userAccount.setGitReward(rewardNum);
                break;
        }
        userAccountRepository.save(userAccount);
        BsUserAccountDetail userAccountDetail = new BsUserAccountDetail();
        userAccountDetail.setUserAccount(userAccount);
        userAccountDetail.setTask(task);
        userAccountDetail.setRewardType(task.getRewardType());
        userAccountDetail.setRewardNum(userRewardNum);
        userAccountDetail.setRewardResource(2);                      //1推荐2完成任务3评级
        userAccountDetailRepository.save(userAccountDetail);

        //任务推荐人奖励
        if (task.getRewardType() == 1) {
            long recommendUserId = user.getRecommendUserId();
            if (recommendUserId != 0) {
                SysUser reUser = userRepository.findOne(recommendUserId);
                if (reUser != null) {
                    BsUserAccount reUserAccount = userAccountRepository.getByUser(reUser);
                    Preconditions.checkArgument(null != reUserAccount, "推荐人不存在");
                    reUserAccount.setTrueReward(reUserAccount.getTrueReward().add(recommendUserRewardNum));
                    userAccountRepository.save(reUserAccount);
                    BsUserAccountDetail reUserAccountDetail = new BsUserAccountDetail();
                    reUserAccountDetail.setUserAccount(reUserAccount);
                    reUserAccountDetail.setTask(task);
                    reUserAccountDetail.setRewardType(task.getRewardType());
                    reUserAccountDetail.setRewardNum(recommendUserRewardNum);
                    reUserAccountDetail.setRewardResource(1);    //1推荐2完成任务3评级
                    userAccountDetailRepository.save(reUserAccountDetail);
                }
            }
        }

        return taskUser;
    }

    @Override
    public long countTotalTask() {
        return taskRepository.count();
    }

    @Override
    public long countComplateTask() {
        QBsTask qbsTask = QBsTask.bsTask;
        return taskRepository.count(qbsTask.auditStatus.eq(1));
    }

    public Page<BsRecommendTask> getBsRecommendTaskList(UserDTO user, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        QBsRecommendTask qbsRecommendTask = QBsRecommendTask.bsRecommendTask;
        builder.and(qbsRecommendTask.recommendUser.auditStatus.eq(1));      //1审核通过
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
