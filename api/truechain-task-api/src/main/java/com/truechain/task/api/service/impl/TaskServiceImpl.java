package com.truechain.task.api.service.impl;

import com.google.common.base.Preconditions;
import com.truechain.task.api.model.dto.TaskDTO;
import com.truechain.task.api.model.dto.TaskTotalDTO;
import com.truechain.task.api.model.dto.UserTaskInfoDTO;
import com.truechain.task.api.repository.BsTaskRepository;
import com.truechain.task.api.repository.SysUserRepository;
import com.truechain.task.api.service.TaskService;
import com.truechain.task.model.entity.BsTask;
import com.truechain.task.model.entity.BsTaskUser;
import com.truechain.task.model.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private SysUserRepository userRepository;

    @Autowired
    private BsTaskRepository taskRepository;

    @Override
    public Page<BsTask> getTaskPage(BsTask task, Pageable pageable) {
        Page<BsTask> taskPage = taskRepository.findAll(pageable);
        return taskPage;
    }

    @Override
    public TaskDTO getTaskInfo(Long taskId) {
        TaskDTO taskDTO = new TaskDTO();
        BsTask task = taskRepository.findOne(taskId);
        return taskDTO;
    }

    @Override
    public TaskTotalDTO getUserTaskList(Long userId) {
        TaskTotalDTO taskTotalDTO = new TaskTotalDTO();
        SysUser user = userRepository.findOne(userId);
        Preconditions.checkArgument(user != null, "用户不存在");
        Set<BsTaskUser> taskUserSet = user.getTaskUserSet();
        if (CollectionUtils.isEmpty(taskUserSet)) {
            return null;
        }
        taskTotalDTO.setTaskTotal(taskUserSet.size());
        taskTotalDTO.setTaskingTotal(0);
        taskTotalDTO.setTaskComplateTolal(0);
        taskTotalDTO.setTaskList(new ArrayList<>(taskUserSet.size()));
        for (BsTaskUser taskUser : taskUserSet) {
            if (taskUser.getStatus() == 1) {
                taskTotalDTO.setTaskComplateTolal(taskTotalDTO.getTaskComplateTolal() + 1);
            } else {
                taskTotalDTO.setTaskingTotal(taskTotalDTO.getTaskingTotal() + 1);
            }
            BsTask task = taskUser.getTask();
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
        Optional<BsTaskUser> taskUser = taskUserSet.stream().filter(x -> x.getTask().getId().equals(taskId)).findFirst();
        Preconditions.checkArgument(taskUser.isPresent(), "该任务不存在");
        userTaskInfoDTO.setTaskCompleteInfo(taskUser.get());
        userTaskInfoDTO.setTask(taskUser.get().getTask());
        return userTaskInfoDTO;
    }

    @Override
    public void commitUserTask(Long userId, Long taskId, String commitAddress, String remark) {

    }
}
