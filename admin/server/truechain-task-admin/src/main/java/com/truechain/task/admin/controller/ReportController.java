package com.truechain.task.admin.controller;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.truechain.task.admin.service.BsTaskUserService;
import com.truechain.task.admin.service.UserService;
import com.truechain.task.admin.viewPojo.UserProfilePagePojo;
import com.truechain.task.admin.viewPojo.UserRecommendPagePojo;
import com.truechain.task.admin.viewPojo.UserRewardHistoryPojo;
import com.truechain.task.admin.viewPojo.UserTaskStatePojo;
import com.truechain.task.core.WrapMapper;
import com.truechain.task.core.Wrapper;
import com.truechain.task.model.entity.BsTaskUser;
import com.truechain.task.model.entity.SysUser;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 获取用户概况数据
     */
    @PostMapping("/getUserProfilePage")
    public Wrapper getUserProfilePage(@RequestParam int pageIndex, @RequestParam int pageSize) {
        Pageable pageable = new PageRequest(pageIndex - 1, pageSize);
        Page<SysUser> userPage = userService.getUserPage(null, pageable);

        Set<Long> userIdSet = Sets.newHashSet();
        userPage.forEach(sysUser -> userIdSet.add(sysUser.getId()));
        Page<UserProfilePagePojo> result = userPage.map(source -> new UserProfilePagePojo(source));
        Map<Long, UserProfilePagePojo> userProfilePagePojoMap = Maps.newHashMap();
        result.forEach(user -> userProfilePagePojoMap.put(user.getId(), user));
        //获取用户任务情况{抢任务数目,完成,进行中}
        List<BsTaskUser> taskUsers = bsTaskUserService.getBsTaskUserByUserIds(userIdSet);
        for (BsTaskUser bsTaskUser : taskUsers) {
            if (userProfilePagePojoMap.containsKey(bsTaskUser.getId())) {
                UserProfilePagePojo userProfilePagePojo = userProfilePagePojoMap.get(bsTaskUser.getId());
                userProfilePagePojo.setTaskCount(userProfilePagePojo.getTaskCount() + 1);
                if (bsTaskUser.getStatus() == 1) {              //1-任务已经完成
                    userProfilePagePojo.setTaskDoneCount(userProfilePagePojo.getTaskDoneCount() + 1);
                    //奖励价值
                    double rewardValue = bsTaskUser.getRewardNum() != null ? NumberUtils.toDouble(bsTaskUser.getRewardNum().toString(), 0) : 0;
                    //奖励类型(1-true,2-ttr,3-rmp)
                    final int rewardType = bsTaskUser.getTaskDetail().getTask().getRewardType();
                    if(rewardType == 1){
                        userProfilePagePojo.setTrueValue(userProfilePagePojo.getTrueValue() + rewardValue);
                    }
                    if(rewardType == 2){
                        userProfilePagePojo.setTtrValue(userProfilePagePojo.getTtrValue() + rewardValue);
                    }
                    if(rewardType == 3){
                        userProfilePagePojo.setRmbValue(userProfilePagePojo.getRmbValue() + rewardValue);
                    }
                    userProfilePagePojo.setTrueValue(userProfilePagePojo.getTrueValue() + rewardValue);
                }
                if (bsTaskUser.getStatus() == 0) {              //0-任务正在进行
                    userProfilePagePojo.setTaskDoingCount(userProfilePagePojo.getTaskDoingCount() + 1);
                }
            }
        }
        //获取用户推荐

        return WrapMapper.ok(result);
    }

    /**
     * 获取奖励统计数据
     */
    @ApiOperation(value = "获取指定用户的任务状态清单", notes = "返回结构中{taskName:任务名称;taskState:任务状态(0-任务中,1-已经完成)}")
    @PostMapping("/getRewardStats")
    public Wrapper getRewardStats(@RequestParam Long userId) {
        List<UserRewardHistoryPojo> rewardHistoryPojoList = Lists.newArrayList();

        List<BsTaskUser> bsTaskUserList = bsTaskUserService.getBsTaskUserByUserIds(Sets.newHashSet(userId));
        for (BsTaskUser bsTaskUser : bsTaskUserList) {
            UserRewardHistoryPojo rewardHistoryPojo = new UserRewardHistoryPojo();
            rewardHistoryPojo.setId(bsTaskUser.getId());
            if (bsTaskUser.getStatus() == 1) {
                rewardHistoryPojo.setEventName("完成任务");
                rewardHistoryPojo.setGotTime(bsTaskUser.getUpdateTime());
                double rewardValue = bsTaskUser.getRewardNum() != null ? NumberUtils.toDouble(bsTaskUser.getRewardNum().toString(), 0) : 0;
                //奖励类型(1-true,2-ttr,3-rmp)
                final int rewardType = bsTaskUser.getTaskDetail().getTask().getRewardType();
                if(rewardType == 1){
                    rewardHistoryPojo.setRewardType("true");
                }
                if(rewardType == 2){
                    rewardHistoryPojo.setRewardType("ttr");
                }
                if(rewardType == 3){
                    rewardHistoryPojo.setRewardType("rmb");
                }
                rewardHistoryPojo.setRewardNum(rewardValue);

                rewardHistoryPojoList.add(rewardHistoryPojo);
            }
        }
        return WrapMapper.ok(rewardHistoryPojoList);
    }

    /**
     * 获取推荐统计数据
     */
    @ApiOperation(value = "获取指定用户的推荐任务清单", notes = "返回结构")
    @PostMapping("/getRecommendStats")
    public Wrapper getRecommendStats(@RequestParam Long userId) {
        List<UserRecommendPagePojo> rewardHistoryPojoList = Lists.newArrayList();

//        List<BsTaskUser> bsTaskUserList = bsTaskUserService.getBsTaskUserByUserIds(Sets.newHashSet(userId));
        for (long i = 0; i < 8; i++) {
            UserRecommendPagePojo userRecommendPagePojo = new UserRecommendPagePojo();
            userRecommendPagePojo.setId(i);
            userRecommendPagePojo.setName("小"+i);
            userRecommendPagePojo.setWxName("wx_xiao"+i);
            userRecommendPagePojo.setWxNum("wx_num_xiao"+i);
            userRecommendPagePojo.setLevel("A");
            userRecommendPagePojo.setRecommendTime("2018-06-22 20:45:36");

            rewardHistoryPojoList.add(userRecommendPagePojo);
        }
        return WrapMapper.ok(rewardHistoryPojoList);
    }

    /**
     * 获取任务统计数据
     */
    @ApiOperation(value = "获取指定用户的任务状态清单", notes = "返回结构中{taskName:任务名称;taskState:任务状态(0-任务中,1-已经完成);taskCategory:任务类型(0-个人，1-团队)}")
    @PostMapping("/getTaskStats")
    public Wrapper getTaskStats(@RequestParam Long userId) {
        List<BsTaskUser> tasks = bsTaskUserService.getBsTaskUserByUserIds(Sets.newHashSet(userId));
        List<UserTaskStatePojo> result = Lists.newArrayList();
        for (BsTaskUser bsTaskUser : tasks) {
            UserTaskStatePojo userTaskStatePojo = new UserTaskStatePojo();
            userTaskStatePojo.setId(bsTaskUser.getTaskDetail().getId());
            String taskName = Joiner.on("-").join(bsTaskUser.getTaskDetail().getTask().getName(), bsTaskUser.getTaskDetail().getStation());
            userTaskStatePojo.setTaskName(taskName);
            userTaskStatePojo.setTaskLevel(bsTaskUser.getTaskDetail().getTask().getLevel());
            userTaskStatePojo.setTaskState(bsTaskUser.getStatus());
            userTaskStatePojo.setTaskCategory(bsTaskUser.getTaskDetail().getTask().getCategory());
            userTaskStatePojo.setTaskStartTime(bsTaskUser.getCreateTime());

            result.add(userTaskStatePojo);
        }
        return WrapMapper.ok(result);
    }
}
