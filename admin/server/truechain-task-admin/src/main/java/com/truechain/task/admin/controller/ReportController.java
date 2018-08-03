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
import com.truechain.task.core.WrapMapper;
import com.truechain.task.core.Wrapper;
import com.truechain.task.model.entity.BsRecommendTask;
import com.truechain.task.model.entity.BsTaskUser;
import com.truechain.task.model.entity.SysUser;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
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



    /**
     * 统计分析的首页数据接口
     * @param token
     * @param agent
     * @return
     */
    @PostMapping("/index")
    public Wrapper index(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent,@RequestBody TimeRangeDTO timeRange ){
        //ReportIndexPojo
        ReportIndexPojo reportIndexPojo = new ReportIndexPojo();
        //通过审核用户数量
        long userCount = userService.countAuditPass(timeRange.getStartDate(),timeRange.getEndDate());
        reportIndexPojo.setUserCount(userCount);
        //任务总量
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setStartDateTime(timeRange.getStartDate());
        taskDTO.setEndDateTime(timeRange.getEndDate());
        long taskCount = bsTaskUserService.getBsTaskUserCount(taskDTO);
        reportIndexPojo.setTaskCount(taskCount);

        taskDTO.setTaskStatus(0);       //完成
        Pageable pageable = new PageRequest(1, Integer.MAX_VALUE);
        Page<BsTaskUser> page = bsTaskUserService.getBsTaskUser(taskDTO,pageable);
        //完成任务数
        reportIndexPojo.setTaskDoneCount(page.getTotalElements());
        page.forEach(bsTaskUser->{
            if(bsTaskUser.getTaskDetail().getTask().getRewardType() == 1){
                reportIndexPojo.setTrueValue(reportIndexPojo.getTrueValue() + bsTaskUser.getRewardNum());
            }
            if(bsTaskUser.getTaskDetail().getTask().getRewardType() == 2){
                reportIndexPojo.setTtrValue(reportIndexPojo.getTtrValue() + bsTaskUser.getRewardNum());
            }
            if(bsTaskUser.getTaskDetail().getTask().getRewardType() == 3){
                reportIndexPojo.setRmbValue(reportIndexPojo.getRmbValue() + bsTaskUser.getRewardNum());
            }
        });
        //进行中任务数
        reportIndexPojo.setTaskDoingCount(reportIndexPojo.getTaskCount() - reportIndexPojo.getTaskDoneCount());

        return WrapMapper.ok(reportIndexPojo);
    }

    /**
     * 获取用户概况数据
     */
    @PostMapping("/getUserProfilePage")
    public Wrapper getUserProfilePage(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestBody UserDTO user) {
        Preconditions.checkArgument(user.getPageIndex() > 0, "分页信息错误");
        Preconditions.checkArgument(user.getPageSize() > 1, "分页信息错误");
        Pageable pageable = new PageRequest(user.getPageIndex() - 1, user.getPageSize());
        Page<SysUser> userPage = userService.getUserPage(user, pageable);
        Page<UserProfilePagePojo> result = convert(userPage);
        return WrapMapper.ok(result);
    }

    private Page<UserProfilePagePojo> convert(Page<SysUser> userPage) {
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
                userProfilePagePojo.setRecommendPerson(bsRecommendTask.getUser().getPersonName());
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
            if (userProfilePagePojoMap.containsKey(bsTaskUser.getId())) {
                UserProfilePagePojo userProfilePagePojo = userProfilePagePojoMap.get(bsTaskUser.getId());
                userProfilePagePojo.setTaskCount(userProfilePagePojo.getTaskCount() + 1);
                if (bsTaskUser.getTaskStatus() == 1) {              //1-任务已经完成
                    userProfilePagePojo.setTaskDoneCount(userProfilePagePojo.getTaskDoneCount() + 1);
                    //奖励价值
                    double rewardValue = bsTaskUser.getRewardNum() != null ? NumberUtils.toDouble(bsTaskUser.getRewardNum().toString(), 0) : 0;
                    //奖励类型(1-true,2-ttr,3-rmp)
                    final int rewardType = bsTaskUser.getTaskDetail().getTask().getRewardType();
                    if (rewardType == 1) {
                        userProfilePagePojo.setTrueValue(userProfilePagePojo.getTrueValue() + rewardValue);
                    }
                    if (rewardType == 2) {
                        userProfilePagePojo.setTtrValue(userProfilePagePojo.getTtrValue() + rewardValue);
                    }
                    if (rewardType == 3) {
                        userProfilePagePojo.setRmbValue(userProfilePagePojo.getRmbValue() + rewardValue);
                    }
                    userProfilePagePojo.setTrueValue(userProfilePagePojo.getTrueValue() + rewardValue);
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
    @ApiOperation(value = "获取指定用户的任务状态清单", notes = "返回结构中{taskName:任务名称;taskState:任务状态(0-任务中,1-已经完成)}")
    @PostMapping("/getRewardStats")
    public Wrapper getRewardStats(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent,@RequestBody RewardViewDTO rewardViewDTO) {
        List<UserRewardHistoryPojo> rewardHistoryPojoList = Lists.newArrayList();

        List<BsTaskUser> bsTaskUserList = bsTaskUserService.getBsTaskUserByUserIds(Sets.newHashSet(rewardViewDTO.getUserId()));
        for (BsTaskUser bsTaskUser : bsTaskUserList) {
            UserRewardHistoryPojo rewardHistoryPojo = new UserRewardHistoryPojo();
            rewardHistoryPojo.setId(bsTaskUser.getId());
            if (bsTaskUser.getTaskStatus() == 1) {
                rewardHistoryPojo.setEventName("完成任务");
                rewardHistoryPojo.setGotTime(bsTaskUser.getUpdateTime());
                double rewardValue = bsTaskUser.getRewardNum() != null ? NumberUtils.toDouble(bsTaskUser.getRewardNum().toString(), 0) : 0;
                //奖励类型(1-true,2-ttr,3-rmp)
                final int rewardType = bsTaskUser.getTaskDetail().getTask().getRewardType();
                if (rewardType == 1) {
                    rewardHistoryPojo.setRewardType("true");
                }
                if (rewardType == 2) {
                    rewardHistoryPojo.setRewardType("ttr");
                }
                if (rewardType == 3) {
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
    public Wrapper getRecommendStats(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestBody UserDTO user) {
        Preconditions.checkArgument(user.getPageIndex() > 0, "分页信息错误");
        Preconditions.checkArgument(user.getPageSize() > 1, "分页信息错误");
        Pageable pageable = new PageRequest(user.getPageIndex() - 1, user.getPageSize());
        List<UserRecommendPagePojo> rewardHistoryPojoList = Lists.newArrayList();

        Page<BsRecommendTask> bsRecommendTaskList = taskService.getBsRecommendTaskList(user,pageable);
        List<UserRecommendPagePojo> result = Lists.newArrayList();
        bsRecommendTaskList.forEach(item->{
            UserRecommendPagePojo userRecommendPagePojo = new UserRecommendPagePojo();
            userRecommendPagePojo.setId(item.getId());
            userRecommendPagePojo.setName(item.getUser().getPersonName());
            userRecommendPagePojo.setWxName(item.getUser().getWxNickName());
            userRecommendPagePojo.setWxNum(item.getUser().getWxNum());
            userRecommendPagePojo.setLevel(item.getUser().getLevel());
            userRecommendPagePojo.setRecommendTime(item.getCreateTime());

            rewardHistoryPojoList.add(userRecommendPagePojo);
        });
//        List<BsTaskUser> bsTaskUserList = bsTaskUserService.getBsTaskUserByUserIds(Sets.newHashSet(userId));
//        for (long i = 0; i < 8; i++) {
//            UserRecommendPagePojo userRecommendPagePojo = new UserRecommendPagePojo();
//            userRecommendPagePojo.setId(i);
//            userRecommendPagePojo.setName("小" + i);
//            userRecommendPagePojo.setWxName("wx_xiao" + i);
//            userRecommendPagePojo.setWxNum("wx_num_xiao" + i);
//            userRecommendPagePojo.setLevel("A");
//            userRecommendPagePojo.setRecommendTime("2018-06-22 20:45:36");
//
//            rewardHistoryPojoList.add(userRecommendPagePojo);
//        }
        return WrapMapper.ok(rewardHistoryPojoList);
    }

    /**
     * 获取任务统计数据
     */
    @ApiOperation(value = "获取指定用户的任务状态清单", notes = "返回结构中{taskName:任务名称;taskState:任务状态(0-任务中,1-已经完成);taskCategory:任务类型(0-个人，1-团队)}")
    @PostMapping("/getTaskStats")
    public Wrapper getTaskStats(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestBody TaskDTO taskDTO) {

        Preconditions.checkArgument(taskDTO.getPageIndex() > 0, "分页信息错误");
        Preconditions.checkArgument(taskDTO.getPageSize() > 1, "分页信息错误");
        Pageable pageable = new PageRequest(taskDTO.getPageIndex() - 1, taskDTO.getPageSize());
        Page<BsTaskUser> tasks = bsTaskUserService.getBsTaskUser(taskDTO, pageable);

//        List<BsTaskUser> tasks = bsTaskUserService.getBsTaskUserByUserIds(Sets.newHashSet(userId));
        List<UserTaskStatePojo> result = Lists.newArrayList();
        for (BsTaskUser bsTaskUser : tasks) {
            UserTaskStatePojo userTaskStatePojo = new UserTaskStatePojo();
            userTaskStatePojo.setId(bsTaskUser.getTaskDetail().getId());
            String taskName = Joiner.on("-").join(bsTaskUser.getTaskDetail().getTask().getName(), bsTaskUser.getTaskDetail().getStation());
            userTaskStatePojo.setTaskName(taskName);
            userTaskStatePojo.setTaskLevel(bsTaskUser.getTaskDetail().getTask().getLevel());
            userTaskStatePojo.setTaskState(bsTaskUser.getTaskStatus());
            userTaskStatePojo.setTaskCategory(bsTaskUser.getTaskDetail().getTask().getCategory());
            userTaskStatePojo.setTaskStartTime(bsTaskUser.getCreateTime());

            result.add(userTaskStatePojo);
        }
        return WrapMapper.ok(result);
    }

    @GetMapping("/export")
    public void export(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestBody UserDTO user) {

        Preconditions.checkArgument(user.getPageIndex() > 0, "分页信息错误");
        Preconditions.checkArgument(user.getPageSize() > 1, "分页信息错误");
        Pageable pageable = new PageRequest(user.getPageIndex() - 1, user.getPageSize());
        Page<SysUser> userPage = userService.getUserPage(user, pageable);

        Page<UserProfilePagePojo> userProfilePagePojos = convert(userPage);
        response.setContentType("application/binary;charset=UTF-8");
        try {
            ServletOutputStream out = response.getOutputStream();

            //设置文件头：最后一个参数是设置下载文件名(这里我们叫：张三.pdf)
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("导出" + ".xls", "UTF-8"));

            // 第一步，创建一个workbook，对应一个Excel文件
            HSSFWorkbook workbook = new HSSFWorkbook();

            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
            HSSFSheet hssfSheet = workbook.createSheet("sheet1");

            // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short


            // 第四步，创建单元格，并设置值表头 设置表头居中
            HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
            hssfCellStyle.setAlignment(HorizontalAlignment.CENTER);
            //表头
            HSSFRow headerRow = hssfSheet.createRow(0);
            String[] headers = {"姓名", "微信昵称", "微信号", "抢任务数", "完成任务数", "进行中任务数", "true数量", "ttr数量", "rmb数量", "用户"};
            for (int i = 0; i < headers.length; i++) {
                HSSFCell headCell = headerRow.createCell(i);
                headCell.setCellValue(headers[i]);
                headCell.setCellStyle(hssfCellStyle);
            }

            for (int rowNum = 0;rowNum < userProfilePagePojos.getContent().size();rowNum++){
                UserProfilePagePojo userProfilePagePojo = userProfilePagePojos.getContent().get(rowNum);
                HSSFRow row = hssfSheet.createRow(rowNum + 1);
                //姓名
                HSSFCell[] hssfCellArray = new HSSFCell[10];
                for (int i = 0; i < hssfCellArray.length; i++) {
                    hssfCellArray[i] = row.createCell(i);
                    hssfCellArray[i].setCellStyle(hssfCellStyle);
                }
                hssfCellArray[0].setCellValue(userProfilePagePojo.getSysUser().getUserName());
                hssfCellArray[1].setCellValue(userProfilePagePojo.getSysUser().getWxNickName());
                hssfCellArray[2].setCellValue(userProfilePagePojo.getSysUser().getWxNum());
                hssfCellArray[3].setCellValue(userProfilePagePojo.getTaskCount());
                hssfCellArray[4].setCellValue(userProfilePagePojo.getTaskDoneCount());
                hssfCellArray[5].setCellValue(userProfilePagePojo.getTaskDoingCount());
                hssfCellArray[6].setCellValue(userProfilePagePojo.getTrueValue());
                hssfCellArray[7].setCellValue(userProfilePagePojo.getTtrValue());
                hssfCellArray[8].setCellValue(userProfilePagePojo.getRmbValue());
                hssfCellArray[9].setCellValue(userProfilePagePojo.getRecommendCount());
            }

            // 第七步，将文件输出到客户端浏览器
            try {
                workbook.write(out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
