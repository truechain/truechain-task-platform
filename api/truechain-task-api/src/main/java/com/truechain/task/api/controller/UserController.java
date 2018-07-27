package com.truechain.task.api.controller;

import com.google.common.base.Preconditions;
import com.truechain.task.api.config.AppProperties;
import com.truechain.task.api.model.dto.RecommendTaskDTO;
import com.truechain.task.api.model.dto.SessionPOJO;
import com.truechain.task.api.model.dto.UserInfoDTO;
import com.truechain.task.api.service.UserService;
import com.truechain.task.core.BusinessException;
import com.truechain.task.core.WrapMapper;
import com.truechain.task.core.Wrapper;
import com.truechain.task.model.entity.SysUser;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 用户Controller
 */
@RestController
@RequestMapping(value = "/user")
public class UserController extends BasicController {

    @Autowired
    private UserService userService;

    /**
     * 获取登录用户
     */
    @GetMapping("/getLoginUser")
    public Wrapper getLoginUser() {
        SessionPOJO sessionPOJO = getSessionPoJO();
        Long userId = sessionPOJO.getUserId();
        SysUser user = userService.getByUserId(userId);
        return WrapMapper.ok(user);
    }

    /**
     * 个人详细
     */
    @GetMapping("/getUserInfo")
    public Wrapper getUserInfo(@RequestParam(required = true) Integer rewardType) {
        SessionPOJO sessionPOJO = getSessionPoJO();
        Long userId = sessionPOJO.getUserId();
        UserInfoDTO userInfoDTO = userService.getUserInfo(userId, rewardType);
        return WrapMapper.ok(userInfoDTO);
    }

    /**
     * 获取推荐记录列表
     */
    @GetMapping("/getRecommendUserList")
    public Wrapper getRecommendUserList() {
        SessionPOJO sessionPOJO = getSessionPoJO();
        Long userId = sessionPOJO.getUserId();
        List<RecommendTaskDTO> recommendTaskDTOList = userService.getRecommendUserList(userId);
        return WrapMapper.ok(recommendTaskDTOList);
    }

    /**
     * 更新个人信息
     */
    @PostMapping("/updateUserInfo")
    public Wrapper updateUserInfo(@RequestParam Long userId, @RequestParam String name, @RequestParam String wxNickName, @RequestParam(required = false) String wxNum, @RequestParam(required = false) String openId,
                                  @RequestParam String trueChainAddress, @RequestParam("file") MultipartFile file) {
        Preconditions.checkArgument(!file.isEmpty(), "简历不能为空");
        String fileName = file.getOriginalFilename();
        File uploadFile = new File(AppProperties.UPLOAD_FILE_PATH + fileName);
        try {
            FileUtils.writeByteArrayToFile(uploadFile, file.getBytes());
        } catch (IOException e) {
            throw new BusinessException("文件上传异常");
        }
        SysUser user = new SysUser();
        user.setId(userId);
        user.setPersonName(name);
        user.setWxNickName(wxNickName);
        user.setWxNum(wxNum);
        user.setOpenId(openId);
        user.setTrueChainAddress(trueChainAddress);
        user.setResumeFilePath(uploadFile.getPath());
        userService.updateUser(user);
        return WrapMapper.ok();
    }
}
