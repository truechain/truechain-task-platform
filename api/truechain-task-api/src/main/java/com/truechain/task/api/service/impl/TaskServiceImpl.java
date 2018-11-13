package com.truechain.task.api.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.truechain.task.core.NullException;
import com.truechain.task.model.entity.BsTask;
import com.truechain.task.model.entity.BsTaskDetail;
import com.truechain.task.model.entity.BsTaskUser;
import com.truechain.task.model.entity.QBsTask;
import com.truechain.task.model.entity.QBsTaskDetail;
import com.truechain.task.model.entity.QBsTaskUser;
import com.truechain.task.model.entity.SysUser;
import com.truechain.task.model.enums.AuditStatusEnum;
import com.truechain.task.model.enums.TaskStatusEnum;

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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        Date today = DateUtils.truncate(now, Calendar.DATE);
        builder.and(qTask.startDateTime.lt(sdf.format(today))).and(qTask.endDateTime.gt(sdf.format(today)));	
        Order order1 = new Order(Sort.Direction.ASC,"isEnteredFull");
        Order order2 = new Order(Sort.Direction.DESC,"updateTime");
        Pageable pageable = new PageRequest(pageIndex - 1, pageSize,new Sort(order1,order2));

        Page<BsTask> taskPage = taskRepository.findAll(builder, pageable);
        return taskPage;
    }

    @Override
    public TaskDTO getTaskInfo(Long taskId, Long userId) {
        TaskDTO taskDTO = new TaskDTO();
        BsTask task = taskRepository.findOne(taskId);

        String[] startTimeArray = task.getStartDateTime().split("T");
        if (startTimeArray.length > 0) {
            task.setStartDateTime(startTimeArray[0]);
        }
        String[] endTimeArray = task.getEndDateTime().split("T");
        if (endTimeArray.length > 0) {
            task.setEndDateTime(endTimeArray[0]);
        }
        //数据库中是已经编码后的，直接返回到前端，由前端解析展示
//        String description = null;
//        try {
//            description = URLDecoder.decode(task.getDescription(), "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        task.setDescription(description);
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
//                Preconditions.checkArgument(null != user, "用户不存在");
                if (user == null) {
                    throw new NullException("用户不存在");
                }
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
//        Preconditions.checkArgument(user != null, "用户不存在");
        if (user == null) {
            throw new NullException("用户不存在");
        }
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
//        Preconditions.checkArgument(user != null, "用户不存在");
        if (user == null) {
            throw new NullException("用户不存在");
        }
        BsTaskDetail taskDetail = taskDetailRepository.findOne(taskDetailId);
        Preconditions.checkArgument(taskDetail != null, "任务不存在");
        BsTask task = taskDetail.getTask();
        Preconditions.checkArgument(task != null, "任务不存在");

        String description = null;
        try {
            description = URLEncoder.encode(task.getDescription(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        task.setDescription(description);

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
//        Preconditions.checkArgument(null != user, "用户不存在");
        if (user == null) {
            throw new NullException("用户不存在");
        }
        Preconditions.checkArgument(user.getAuditStatus() == AuditStatusEnum.AUDITED.getCode(), "当前资料审核中暂时无法接取该任务");
        BsTaskDetail taskDetail = taskDetailRepository.findOne(taskDetailId);
        Preconditions.checkArgument(null != taskDetail, "任务不存在");
        BsTask bsTask = taskDetail.getTask();

        //判断S级任务
        if (!user.getLevel().equalsIgnoreCase("s")) {
            if (bsTask.getLevel().equalsIgnoreCase("s")) {
                throw new BusinessException("您的等级不够，请选择符合您开发等级的任务");
            }
            Preconditions.checkArgument(user.getLevel().compareTo(bsTask.getLevel()) <= 0, "您的等级不够，请选择符合您开发等级的任务");
        }

        QBsTaskUser qtaskUser = QBsTaskUser.bsTaskUser;
        long count = taskUserRepository.count(qtaskUser.user.eq(user).and(qtaskUser.taskDetail.task.eq(taskDetail.getTask())));
        if (count > 0) {
            throw new BusinessException("你已抢过该任务，不可重复抢取");
        }
        count = taskUserRepository.count(qtaskUser.taskDetail.eq(taskDetail).and(qtaskUser.user.isNotNull()));
        if (count >= taskDetail.getPeopleNum()) {
            throw new BusinessException("任务已满");
        }
        
        //已报名人数+1
        int newEnteredPeopleNum = bsTask.getEnteredPeopleNum() + 1;
        bsTask.setEnteredPeopleNum(newEnteredPeopleNum);
        
        //是否报名已满
        if(newEnteredPeopleNum == bsTask.getPeopleNum()){
        	bsTask.setIsEnteredFull((short)1);
        }
        taskRepository.save(bsTask);
        
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
