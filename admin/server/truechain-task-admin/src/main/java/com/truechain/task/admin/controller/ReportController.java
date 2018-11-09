package com.truechain.task.admin.controller;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.truechain.task.admin.model.dto.RewardViewDTO;
import com.truechain.task.admin.model.dto.TaskDTO;
import com.truechain.task.admin.model.dto.TimeRangeDTO;
import com.truechain.task.admin.model.dto.UserDTO;
import com.truechain.task.admin.model.viewPojo.*;
import com.truechain.task.admin.service.BsRecommendTaskService;
import com.truechain.task.admin.service.BsTaskUserService;
import com.truechain.task.admin.service.TaskService;
import com.truechain.task.admin.service.UserService;
import com.truechain.task.admin.service.impl.BsUserAccountDetailServiceImpl;
import com.truechain.task.admin.util.ExportExcel;
import com.truechain.task.core.WrapMapper;
import com.truechain.task.core.Wrapper;
import com.truechain.task.model.entity.*;
import io.swagger.annotations.ApiOperation;
import joptsimple.internal.Strings;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 报表Controller
 */
@RestController
@RequestMapping(value = "/report")
public class ReportController extends BasicController {

    @Autowired
    private UserService userService;

    @Autowired
    private BsTaskUserService bsTaskUserService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private BsRecommendTaskService bsRecommendTaskService;

    @Autowired
    private BsUserAccountDetailServiceImpl bsUserAccountDetailServiceImpl;



    /**
     * 统计分析的首页数据接口
     * @param token
     * @param agent
     * @return
     */
    @ApiOperation(value = "数据统计")
    @PostMapping("/index")
    public Wrapper index(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent,@RequestBody TimeRangeDTO timeRange ){
        final String startDate = timeRange.getStartDate();
        final String endDate = timeRange.getEndDate();

        ReportIndexPojo reportIndexPojo = new ReportIndexPojo();
        reportIndexPojo.setId(0L);

        reportIndexPojo.setStartDate(startDate);
        reportIndexPojo.setEndDate(endDate);
        //通过审核用户数量
        long userCount = userService.countAuditPass(timeRange.getStartDate(),timeRange.getEndDate());
        reportIndexPojo.setUserCount(userCount);
        //任务总量
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setStartDateTime(timeRange.getStartDate());
        taskDTO.setEndDateTime(timeRange.getEndDate());
        Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
        Page<BsTask> taskPage = taskService.getTaskPage(taskDTO,pageable);
        reportIndexPojo.setTaskCount(taskPage.getTotalElements());
        //完成任务数目

        /*
        taskPage.forEach(bsTask->{
            if(bsTask.getTaskStatus() != null && bsTask.getTaskStatus().intValue() == 1){
                reportIndexPojo.setTaskDoneCount(reportIndexPojo.getTaskDoneCount() + 1);
            }
        });
        */
        taskPage.forEach(bsTask->{
            if(bsTask.getAuditStatus() != null && bsTask.getAuditStatus().intValue() == 1){
                reportIndexPojo.setTaskDoneCount(reportIndexPojo.getTaskDoneCount() + 1);
            }
        });

        //完成任务数目 任务用户列表中的状态为1的数据
        /*long taskDone =;
        TaskDTO taskDTO2 = new TaskDTO();
        taskDTO.setStartDateTime(timeRange.getStartDate());
        taskDTO.setEndDateTime(timeRange.getEndDate());
        Pageable pageable2 = new PageRequest(0, Integer.MAX_VALUE);
        Page<BsTaskUser> taskUserPage2 = bsTaskUserService.getBsTaskUser(taskDTO2, pageable2);
        reportIndexPojo.setTaskCount(taskUserPage2.getTotalElements());
        taskUserPage2.forEach(bsTaskUser->{
            if(bsTaskUser.getTaskStatus() == 1){
                reportIndexPojo.setTaskDoneCount(reportIndexPojo.getTaskDoneCount() + 1);
            }
        });*/

        //进行中任务数 audit_status=0
       // reportIndexPojo.setTaskDoingCount(reportIndexPojo.getTaskCount() - reportIndexPojo.getTaskDoneCount());

        taskPage.forEach(bsTask->{
            if(bsTask.getAuditStatus() != null && bsTask.getAuditStatus().intValue() == 0){
                reportIndexPojo.setTaskDoingCount(reportIndexPojo.getTaskDoingCount() + 1);
            }
        });

        Page<BsUserAccountDetail> bsUserAccountDetails = bsUserAccountDetailServiceImpl.getBsUserAccountDetail(timeRange,pageable);
        bsUserAccountDetails.forEach(bsUserAccountDetail -> {
                    if(bsUserAccountDetail.getRewardType() == 1 && bsUserAccountDetail.getRewardNum() != null){
                        reportIndexPojo.setTrueValue(reportIndexPojo.getTrueValue() + bsUserAccountDetail.getRewardNum().doubleValue());
                    }
                    if(bsUserAccountDetail.getRewardType() == 2 && bsUserAccountDetail.getRewardNum() != null){
                        reportIndexPojo.setTtrValue(reportIndexPojo.getTtrValue() + bsUserAccountDetail.getRewardNum().doubleValue());
                    }
                    if(bsUserAccountDetail.getRewardType() == 3 &&  bsUserAccountDetail.getRewardNum() != null){
                        reportIndexPojo.setRmbValue(reportIndexPojo.getRmbValue() + bsUserAccountDetail.getRewardNum().doubleValue());
                    }
                });

        List<ReportIndexPojo> rewardHistoryPojoList = Lists.newArrayList(reportIndexPojo);
        return WrapMapper.ok(rewardHistoryPojoList);
    }

    /**
     * 获取用户概况数据
     */
    @ApiOperation(value = "统计详情")
    @PostMapping("/getUserProfilePage")
    public Wrapper getUserProfilePage(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestBody UserDTO user) {
        Preconditions.checkArgument(user.getPageIndex() > 0, "分页信息错误");
        Preconditions.checkArgument(user.getPageSize() > 1, "分页信息错误");
        Pageable pageable = new PageRequest(user.getPageIndex() - 1, user.getPageSize());
        Page<SysUser> userPage = userService.getAuditedUserPage(user, pageable);
        Page<UserProfilePagePojo> result = convert(userPage);
        return WrapMapper.ok(result);
    }

    private Page<UserProfilePagePojo> convert(Page<SysUser> userPage) {
    	if(userPage == null){
    		return null;
    	}else if(userPage.getTotalElements() == 0){
    		return userPage.map(source -> {
    			return new UserProfilePagePojo();
    		});
    	}
        Set<Long> userIdSet = Sets.newHashSet();
        userPage.forEach(sysUser -> userIdSet.add(sysUser.getId()));

        //获取用户推荐
        //推荐人
        Map<Long,BsRecommendTask> recommendTasks = bsRecommendTaskService.getMyRecommendUser(userIdSet);
        //用户推荐数
        Map<Long,Integer> recommendCounts = bsRecommendTaskService.getMyRecommendCount(userIdSet);

        Page<UserProfilePagePojo> result = userPage.map(source -> {
            UserProfilePagePojo userProfilePagePojo = new UserProfilePagePojo(source);
            if(recommendTasks.containsKey(userProfilePagePojo.getId())){
                BsRecommendTask bsRecommendTask = recommendTasks.get(userProfilePagePojo.getId());
                userProfilePagePojo.setRecommendPerson(bsRecommendTask.getRecommendUser().getPersonName());
            }
            if(recommendCounts.containsKey(userProfilePagePojo.getId())){
                Integer bsRecommendCount = recommendCounts.get(userProfilePagePojo.getId());
                userProfilePagePojo.setRecommendCount(bsRecommendCount);
            }
            return userProfilePagePojo;
        }
        );
        Map<Long, UserProfilePagePojo> userProfilePagePojoMap = Maps.newHashMap();
        result.forEach(user -> userProfilePagePojoMap.put(user.getId(), user));


        //获取用户任务情况{抢任务数目,完成,进行中}
        List<BsTaskUser> taskUsers = bsTaskUserService.getBsTaskUserByUserIds(userIdSet);
        for (BsTaskUser bsTaskUser : taskUsers) {
            if (userProfilePagePojoMap.containsKey(bsTaskUser.getUser().getId())) {
                UserProfilePagePojo userProfilePagePojo = userProfilePagePojoMap.get(bsTaskUser.getUser().getId());
                userProfilePagePojo.setTaskCount(userProfilePagePojo.getTaskCount() + 1);
                if (bsTaskUser.getTaskStatus() == 1) {              //1-任务已经完成
                    userProfilePagePojo.setTaskDoneCount(userProfilePagePojo.getTaskDoneCount() + 1);
                    //奖励价值
                    List<BsUserAccountDetail> rewardList= bsUserAccountDetailServiceImpl.getBsUserAccountDetail(bsTaskUser.getTaskDetail().getTask().getId(),bsTaskUser.getUser().getId());
                    if(rewardList.size() > 0){
//                        double rewardValue = bsTaskUser.getRewardNum() != null ? NumberUtils.toDouble(bsTaskUser.getRewardNum().toString(), 0) : 0;
//                        final int rewardType = bsTaskUser.getTaskDetail().getTask().getRewardType();
                        BsUserAccountDetail bsUserAccountDetail = rewardList.get(0);
                        //奖励类型(1-true,2-ttr,3-rmp)
                        final int rewardType = bsUserAccountDetail.getRewardType();
                        if (rewardType == 1) {
                            userProfilePagePojo.setTrueValue(userProfilePagePojo.getTrueValue() + bsUserAccountDetail.getRewardNum().doubleValue());
                        }
                        if (rewardType == 2) {
                            userProfilePagePojo.setTtrValue(userProfilePagePojo.getTtrValue() + bsUserAccountDetail.getRewardNum().doubleValue());
                        }
                        if (rewardType == 3) {
                            userProfilePagePojo.setRmbValue(userProfilePagePojo.getRmbValue() + bsUserAccountDetail.getRewardNum().doubleValue());
                        }
                    }
                }
                if (bsTaskUser.getTaskStatus() == 0) {              //0-任务正在进行
                    userProfilePagePojo.setTaskDoingCount(userProfilePagePojo.getTaskDoingCount() + 1);
                }
            }
        }


        return result;
    }

    /**
     * 获取奖励统计数据
     */
    @ApiOperation(value = "奖励列表", notes = "返回结构中{taskName:任务名称;taskState:任务状态(0-任务中,1-已经完成)}")
    @PostMapping("/getRewardStats")
    public Wrapper getRewardStats(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent,@RequestBody RewardViewDTO rewardViewDTO) {
    	List<UserRewardHistoryPojo> rewardHistoryPojoList = Lists.newArrayList();
    	Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);

    	Page<BsUserAccountDetail> page = bsUserAccountDetailServiceImpl.getBsUserAccountDetail(rewardViewDTO,pageable);
    	page.forEach(bsUserAccountDetail -> {
    		UserRewardHistoryPojo rewardHistoryPojo = new UserRewardHistoryPojo();
    		rewardHistoryPojo.setId(bsUserAccountDetail.getId());

    		Integer rewardResource = bsUserAccountDetail.getRewardResource();
    		if(bsUserAccountDetail.getRewardResource() != null){
    			if(rewardResource.intValue() == 1){
    				rewardHistoryPojo.setEventName("推荐");
    			}
    			if(rewardResource.intValue() == 2){
    				rewardHistoryPojo.setEventName("完成任务");
    			}
    			if(rewardResource.intValue() == 3){
    				rewardHistoryPojo.setEventName("评级");
    			}
    		}
    		rewardHistoryPojo.setGotTime(bsUserAccountDetail.getUpdateTime());
    		//奖励类型(1-true,2-ttr,3-rmp)
    		final int rewardType = bsUserAccountDetail.getRewardType();
    		if (rewardType == 1) {
    			rewardHistoryPojo.setRewardType("true");
    		}
    		if (rewardType == 2) {
    			rewardHistoryPojo.setRewardType("ttr");
    		}
    		if (rewardType == 3) {
    			rewardHistoryPojo.setRewardType("rmb");
    		}
    		if( bsUserAccountDetail.getRewardNum() != null){
    			rewardHistoryPojo.setRewardNum(bsUserAccountDetail.getRewardNum().doubleValue());
    		}

    		rewardHistoryPojoList.add(rewardHistoryPojo);
    	});
    	return WrapMapper.ok(rewardHistoryPojoList);
    }

    /**
     * 获取奖励清单
     */
    @ApiOperation(value = "奖励清单列表", notes = "返回结构中{taskName:任务名称;taskState:任务状态(0-任务中,1-已经完成)}")
    @PostMapping("/getRewardList")
    public Wrapper getRewardList(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent,@RequestBody RewardViewDTO rewardViewDTO){
        List<RewardListPojo> reward=Lists.newArrayList();
        Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
        Page<BsUserAccountDetail> page = bsUserAccountDetailServiceImpl.getBsUserAccountDetail(rewardViewDTO,pageable);
        page.forEach(bsUserAccountDetail ->{
            RewardListPojo rewardListPojo=new RewardListPojo();
            rewardListPojo.setId(bsUserAccountDetail.getId());
            Integer rewardResource = bsUserAccountDetail.getRewardResource();
            if(bsUserAccountDetail.getRewardResource() != null){
                if(rewardResource.intValue()==1){
                    rewardListPojo.setEventName("推荐");
                }
                if(rewardResource.intValue()==2){
                    rewardListPojo.setEventName("完成任务");
                }
                if(rewardResource.intValue()==3) {
                    rewardListPojo.setEventName("评级");
                }
            }
            rewardListPojo.setPersonName(bsUserAccountDetail.getUserAccount().getUser().getPersonName());
            rewardListPojo.setWxName(bsUserAccountDetail.getUserAccount().getUser().getWxNickName());
            rewardListPojo.setTcAddress(bsUserAccountDetail.getUserAccount().getUser().getTrueChainAddress());
            if(bsUserAccountDetail.getTask()!=null)
            {
                rewardListPojo.setTaskName(bsUserAccountDetail.getTask().getName());
                rewardListPojo.setPassTime(bsUserAccountDetail.getTask().getReviewTime());
            }

            final int rewardType = bsUserAccountDetail.getRewardType();
            if(rewardType==1){
                rewardListPojo.setRewardType("true");
            }
            if(rewardType==2){
                rewardListPojo.setRewardType("ttr");
            }
            if(rewardType==3){
                rewardListPojo.setRewardType("rmb");
            }
            if( bsUserAccountDetail.getRewardNum() != null){
                rewardListPojo.setRewardNum(bsUserAccountDetail.getRewardNum().doubleValue());
            }
            final int lstate=bsUserAccountDetail.getLssuingState();
            if(lstate==0){
                rewardListPojo.setLssuingstate("未发放");
            }
            if(lstate==1){
                rewardListPojo.setLssuingstate("已发放");
            }
            reward.add(rewardListPojo);
        });
        return WrapMapper.ok(reward);
    }
    
    
    /**
     * 奖励发放
     */
    @ApiOperation(value = "奖励发放", notes = "奖励清单页面使用")
    @PostMapping("/rewardUserAccountDetail")
    public Wrapper rewardUserAccountDetail(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent,@RequestParam Long UserAccountDetailId){
    	BsUserAccountDetail bsUserAccountDetail = bsUserAccountDetailServiceImpl.rewardUserAccountDetail(UserAccountDetailId);
    	return WrapMapper.ok(bsUserAccountDetail);
    }
    

    /**
     * 获取推荐统计数据
     */
    @ApiOperation(value = "推荐列表", notes = "返回结构")
    @PostMapping("/getRecommendStats")
    public Wrapper getRecommendStats(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestBody UserDTO userDTO) {
        Preconditions.checkArgument(userDTO.getPageIndex() > 0, "分页信息错误");
        Preconditions.checkArgument(userDTO.getPageSize() > 1, "分页信息错误");
        Pageable pageable = new PageRequest(userDTO.getPageIndex() - 1, userDTO.getPageSize());
        List<UserRecommendPagePojo> rewardHistoryPojoList = Lists.newArrayList();

        Page<BsRecommendTask> page = bsRecommendTaskService.getRecommendTask(userDTO,pageable);
//        Page<BsUserAccountDetail> page = bsUserAccountDetailServiceImpl.getRecommendTask(user,pageable);

        page.forEach(item->{
            UserRecommendPagePojo userRecommendPagePojo = new UserRecommendPagePojo();
            userRecommendPagePojo.setId(item.getId());
            userRecommendPagePojo.setName(item.getUser().getPersonName());
            userRecommendPagePojo.setWxName(item.getUser().getWxNickName());
            userRecommendPagePojo.setWxNum(item.getUser().getWxNum());
            userRecommendPagePojo.setLevel(item.getUser().getLevel());
            userRecommendPagePojo.setRecommendTime(item.getUpdateTime());
//            if(!Strings.isNullOrEmpty(user.getLevel()) && userRecommendPagePojo.getLevel().equals(user.getLevel())){
//                rewardHistoryPojoList.add(userRecommendPagePojo);
//            }
//            if(Strings.isNullOrEmpty(user.getLevel())){
//                rewardHistoryPojoList.add(userRecommendPagePojo);
//            }
            rewardHistoryPojoList.add(userRecommendPagePojo);
        });
//
//        page.forEach(item->{
//            UserRecommendPagePojo userRecommendPagePojo = new UserRecommendPagePojo();
//            userRecommendPagePojo.setId(item.getId());
//            userRecommendPagePojo.setName(item.getRecommendTask().getUser().getPersonName());
//            userRecommendPagePojo.setWxName(item.getRecommendTask().getUser().getWxNickName());
//            userRecommendPagePojo.setWxNum(item.getRecommendTask().getUser().getWxNum());
//            userRecommendPagePojo.setLevel(item.getRecommendTask().getUser().getLevel());
//            userRecommendPagePojo.setRecommendTime(item.getUpdateTime());
//            if(!Strings.isNullOrEmpty(user.getLevel()) && userRecommendPagePojo.getLevel().equals(user.getLevel())){
//                rewardHistoryPojoList.add(userRecommendPagePojo);
//            }
//            if(Strings.isNullOrEmpty(user.getLevel())){
//                rewardHistoryPojoList.add(userRecommendPagePojo);
//            }
//        });
        return WrapMapper.ok(rewardHistoryPojoList);
    }

    /**
     * 任务列表
     */
    @ApiOperation(value = "任务列表", notes = "返回结构中{taskName:任务名称;taskState:任务状态(0-任务中,1-已经完成);taskCategory:任务类型(0-个人，1-团队)}")
    @PostMapping("/getTaskStats")
    public Wrapper getTaskStats(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestBody TaskDTO taskDTO) {

        Preconditions.checkArgument(taskDTO.getPageIndex() > 0, "分页信息错误");
        Preconditions.checkArgument(taskDTO.getPageSize() > 1, "分页信息错误");
        Pageable pageable = new PageRequest(taskDTO.getPageIndex() - 1, taskDTO.getPageSize());
        Page<BsTaskUser> tasks = bsTaskUserService.getBsTaskUser(taskDTO, pageable);


        List<UserTaskStatePojo> result = Lists.newArrayList();
        for (BsTaskUser bsTaskUser : tasks) {
            UserTaskStatePojo userTaskStatePojo = new UserTaskStatePojo();
            userTaskStatePojo.setId(bsTaskUser.getTaskDetail().getId());
            String taskName = bsTaskUser.getTaskDetail().getTask().getName();
            userTaskStatePojo.setTaskName(taskName);
            userTaskStatePojo.setTaskLevel(bsTaskUser.getTaskDetail().getTask().getLevel());
            userTaskStatePojo.setTaskState(bsTaskUser.getTaskStatus());
            userTaskStatePojo.setTaskCategory(bsTaskUser.getTaskDetail().getTask().getCategory());
            userTaskStatePojo.setTaskStartTime(bsTaskUser.getCreateTime());

            result.add(userTaskStatePojo);
        }
        return WrapMapper.ok(result);
    }

//    public  void export(HttpServletRequest request){
////        UserDTO user=new UserDTO();
////        if(request.getParameter("name")!=null) user.setName(request.getParameter("name"));
////        if(request.getParameter("startDate")!=null) user.setStartDate(request.getParameter("startDate"));
////    }

    /**
     * Created by NancySmall
     * on 2018/10/20
     * @param startDate
     * @param endDate
     * @param name
     * @param wxNickName
     * @param auditStatus
     * @param level
     * @param pageSize
     * @param pageIndex
     */
    @ApiOperation(value = "统计详情-导出")
    @GetMapping("/export")
    public void export(@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate,@RequestParam("name") String name,@RequestParam("wxNickName") String wxNickName,@RequestParam("auditStatus") Integer auditStatus,@RequestParam("level") String level,@RequestParam("pageSize") int pageSize,@RequestParam("pageIndex") int pageIndex) {
        UserDTO user= ExportExcel.getUserDTO(startDate,endDate,name,wxNickName,auditStatus,level,pageSize,pageIndex);
        Preconditions.checkArgument(user.getPageIndex()> 0, "分页信息错误");
        Preconditions.checkArgument(user.getPageSize()> 1, "分页信息错误");
        Pageable pageable = new PageRequest(user.getPageIndex() - 1, user.getPageSize());
        Page<SysUser> userPage = userService.getAuditedUserPage(user, pageable);
        Page<UserProfilePagePojo> userProfilePagePojos = convert(userPage);
        response.setContentType("application/vnd.ms-excel");
        ExportExcel.exportData(userProfilePagePojos,response);
    }



}
