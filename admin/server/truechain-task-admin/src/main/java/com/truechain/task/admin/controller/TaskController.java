package com.truechain.task.admin.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Preconditions;
import com.truechain.task.admin.config.AppProperties;
import com.truechain.task.admin.model.dto.AuditBsTaskUserDTO;
import com.truechain.task.admin.model.dto.AuditEntryFormUserDTO;
import com.truechain.task.admin.model.dto.SessionPOJO;
import com.truechain.task.admin.model.dto.TaskDTO;
import com.truechain.task.admin.model.dto.TaskEntryFromDTO;
import com.truechain.task.admin.model.dto.TaskInfoDTO;
import com.truechain.task.admin.repository.AuthUserRepository;
import com.truechain.task.admin.service.BsTaskUserService;
import com.truechain.task.admin.service.TaskService;
import com.truechain.task.core.BusinessException;
import com.truechain.task.core.WrapMapper;
import com.truechain.task.core.Wrapper;
import com.truechain.task.model.entity.AuthUser;
import com.truechain.task.model.entity.BsTask;
import com.truechain.task.model.entity.BsTaskDetail;
import com.truechain.task.model.entity.BsTaskUser;

import io.swagger.annotations.ApiOperation;


/**
 * 任务Controller
 */
@RestController
@RequestMapping("/task")
public class TaskController extends BasicController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private TaskService taskService;
    
    @Autowired
    private BsTaskUserService bsTaskUserService;
    

    /**
     * 获取任务数据
     */
    @ApiOperation(value = "任务列表")
    @PostMapping("/getTaskPage")
    public Wrapper getTaskPage(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestBody TaskDTO task) {
        Preconditions.checkArgument(task.getPageIndex() > 0, "分页信息错误");
        Preconditions.checkArgument(task.getPageSize() > 1, "分页信息错误");
        Pageable pageable = new PageRequest(task.getPageIndex() - 1, task.getPageSize());
        Page<BsTask> taskPage = taskService.getTaskPage(task, pageable);
        return WrapMapper.ok(taskPage);
    }

    /**
     * 获取任务详情
     */
    @ApiOperation(value = "任务详情")
    @PostMapping("/getTaskInfo")
    public Wrapper getTaskInfo(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam Long taskId) {
        TaskInfoDTO task = taskService.getTaskInfo(taskId);
        return WrapMapper.ok(task);
    }

    /**
     * 获取报名表信息
     */
    @ApiOperation(value = "报名表信息")
    @PostMapping("/getEntryFormInfo")
    public Wrapper getEntryFormInfo(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam Long taskId) {
        TaskEntryFromDTO taskEntryFromDTO = taskService.getEntryFormInfo(taskId);
        return WrapMapper.ok(taskEntryFromDTO);
    }

    /**
     * 上传图片
     */
    @PostMapping("/uploadTaskIcon")
    public Wrapper uploadTaskIcon(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new BusinessException("请选择文件");
        }
        String fileName = file.getOriginalFilename();
        File uploadFile = new File(AppProperties.TASK_ICON_PATH + UUID.randomUUID().toString().replace("-", "") + fileName);
        try {
            FileUtils.writeByteArrayToFile(uploadFile, file.getBytes());
        } catch (IOException e) {
            throw new BusinessException("文件上传异常");
        }
        Map<String, String> result = new HashMap<>();
        String path = uploadFile.getPath();
        result.put("path", path);
        String showPath = AppProperties.TASK_ICON_URL + "/" + uploadFile.getName();
        result.put("showPath", showPath);
        return WrapMapper.ok(result);
    }

    /**
     * 新增任务
     */
    @ApiOperation(value = "新增任务")
    @PostMapping("/addTask")
    public Wrapper addTask(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestBody TaskInfoDTO taskInfoDTO) {
        logger.info("addTask taskInfoDTO{}", new JSONObject(taskInfoDTO));

        //添加发布人
        String publisherName = "";
        SessionPOJO sessionPoJO = getSessionPoJO();
        if (sessionPoJO != null) {
            AuthUser authUser = authUserRepository.findOne(Long.parseLong(sessionPoJO.getUserId()));
            publisherName = authUser.getRealName();
        }

        Preconditions.checkArgument(null != taskInfoDTO, "数据为空");
        BsTask task = taskInfoDTO.getTask();
        Preconditions.checkArgument(null != task, "任务数据为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(task.getName()), "任务名称不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(task.getLevel()), "任务等级不能为空");
        Preconditions.checkArgument(null != task.getRewardType(), "奖励类型不能为空");
        Preconditions.checkArgument(null != task.getRewardNum(), "奖励不能为空");
        Preconditions.checkArgument(null != taskInfoDTO.getTaskDetailList(), "任务信息不完整");
        int category = taskInfoDTO.getTask().getCategory();
        if(category==0){
            Preconditions.checkArgument(taskInfoDTO.getTaskDetailList().size()==1, "任务类型不正确");
        }
        else{
            Preconditions.checkArgument(taskInfoDTO.getTaskDetailList().size()>1, "任务类型不正确");
	
        }
        Set<BsTaskDetail> taskDetailSet = taskInfoDTO.getTaskDetailList();
        for (BsTaskDetail taskDetail : taskDetailSet) {
            Preconditions.checkArgument(StringUtils.isNotBlank(taskDetail.getStation()), "岗位名称不能为空");
            Preconditions.checkArgument(null != taskDetail.getPeopleNum(), "人数不能为空");
            Preconditions.checkArgument(null != taskDetail.getRewardNum(), "奖励不能为空");
        }

        BsTask bsTask = taskInfoDTO.getTask();
        bsTask.setCreateUser(publisherName);
        String[] startTime = bsTask.getStartDateTime().split("T");
        if (startTime.length > 0) {
            bsTask.setStartDateTime(startTime[0]);
        }
        String[] endTime = bsTask.getEndDateTime().split("T");
        if (endTime.length > 0) {
            bsTask.setEndDateTime(endTime[0]);
        }
        taskService.addTask(taskInfoDTO);
        return WrapMapper.ok();
    }

    /**
     * 更新任务
     */
    @ApiOperation(value = "修改任务")
    @PostMapping("/updateTask")
    public Wrapper updateTask(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestBody TaskInfoDTO taskInfoDTO) {
        logger.info("updateTask taskInfoDTO{}", new JSONObject(taskInfoDTO));
        Preconditions.checkArgument(null != taskInfoDTO, "数据为空");
        BsTask task = taskInfoDTO.getTask();
        Preconditions.checkArgument(null != task, "任务数据为空");
        Preconditions.checkArgument(null != task.getId(), "任务ID不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(task.getName()), "任务名称不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(task.getLevel()), "任务等级不能为空");
        Preconditions.checkArgument(null != task.getRewardType(), "奖励类型不能为空");
        Preconditions.checkArgument(null != task.getRewardNum(), "奖励不能为空");
        Preconditions.checkArgument(null != taskInfoDTO.getTaskDetailList(), "任务信息不完整");
        int category = taskInfoDTO.getTask().getCategory();
        if(category==0){
            Preconditions.checkArgument(taskInfoDTO.getTaskDetailList().size()==1, "任务类型不正确");
        }
        else{
            Preconditions.checkArgument(taskInfoDTO.getTaskDetailList().size()>1, "任务类型不正确");
	
        }
        Set<BsTaskDetail> taskDetailSet = taskInfoDTO.getTaskDetailList();
        for (BsTaskDetail taskDetail : taskDetailSet) {
            Preconditions.checkArgument(StringUtils.isNotBlank(taskDetail.getStation()), "岗位名称不能为空");
            Preconditions.checkArgument(null != taskDetail.getPeopleNum(), "人数不能为空");
            Preconditions.checkArgument(null != taskDetail.getRewardNum(), "奖励不能为空");
        }
        taskService.updateTask(taskInfoDTO);
        return WrapMapper.ok();
    }

    /**
     * 删除任务
     */
    @PostMapping("/deleteTask")
    public Wrapper deleteTask(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam Long taskId) {
        taskService.deleteTask(taskId);
        return WrapMapper.ok();
    }

    /**
     * 启用任务
     */
    @PostMapping("/enableTask")
    public Wrapper enableTask(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam Long taskId) {
        taskService.enableTask(taskId);
        return WrapMapper.ok();
    }

    /**
     * 禁用任务
     */
    @PostMapping("/disableTask")
    public Wrapper disableTask(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam Long taskId) {
        taskService.disableTask(taskId);
        return WrapMapper.ok();
    }

    /**
     * 审核报名表人员
     */
    @PostMapping("/auditEntryFormUser")
    public Wrapper auditEntryFormUser(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam Long taskUserId) {
        AuditEntryFormUserDTO result = taskService.auditEntryFormUser(taskUserId);
        return WrapMapper.ok(result);
    }

    /**
     * 发放奖励
     */
    @PostMapping("/rewardEntryFromUser")
    public Wrapper rewardEntryFromUser(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent,
                                       @RequestParam Long taskUserId, @RequestParam(required = false) Double userReward, @RequestParam(required = false) Double recommendUserReward) {
        BsTaskUser bsTaskUser = taskService.rewardEntryFromUser(taskUserId, userReward, recommendUserReward);
        return WrapMapper.ok(bsTaskUser);
    }
    
    /**
     * 取消报名任务
     */
    @ApiOperation(value = "取消报名任务")
    @PostMapping("/cancelEntryFormUser")
    public Wrapper cancelBsTaskUser(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam Long taskUserId) {
    	bsTaskUserService.cancelBsTaskUser(taskUserId);
        return WrapMapper.ok();
    }
    
    /**
     * 审核报名任务
     */
    @ApiOperation(value = "审核报名任务")
    @PostMapping("/auditBsTaskUser")
    public Wrapper auditBsTaskUser(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestBody AuditBsTaskUserDTO auditBsTaskUserDTO) {
    	bsTaskUserService.auditBsTaskUser(auditBsTaskUserDTO);;
        return WrapMapper.ok();
    }
    
    /**
     * 默认奖励数量
     */
    @GetMapping("/getDefaultReward")
    public Wrapper getDefaultReward(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam Long taskUserId) {
        return WrapMapper.ok(bsTaskUserService.getDefaultReward(taskUserId));
    }
    
    
    
    
}
