package com.truechain.task.api.controller;

import com.google.common.base.Preconditions;
import com.truechain.task.api.config.AppProperties;
import com.truechain.task.api.model.dto.LoginDTO;
import com.truechain.task.api.model.dto.SessionPOJO;
import com.truechain.task.api.security.SessionPOJOService;
import com.truechain.task.api.service.DeclareService;
import com.truechain.task.api.service.UserService;
import com.truechain.task.core.BusinessException;
import com.truechain.task.core.WrapMapper;
import com.truechain.task.core.Wrapper;
import com.truechain.task.model.entity.SysDeclare;
import com.truechain.task.model.entity.SysUser;
import com.truechain.task.util.CommonUtil;
import com.truechain.task.util.JwtUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 账户Controller
 */
@RestController
@RequestMapping(value = "/unauth/account")
public class AccountController extends BasicController {

    @Autowired
    private UserService userService;

    @Autowired
    private DeclareService declareService;

    @Autowired
    private SessionPOJOService sessionPOJOService;

    /**
     * 注册
     */
    @PostMapping("/register")
    public Wrapper register(@RequestParam String name, @RequestParam String wxNickName, @RequestParam String wxNum, @RequestParam(required = false) String openId,
                            @RequestParam String trueChainAddress, @RequestParam String mobile, @RequestParam String verifyCode, @RequestParam("file") MultipartFile file) {
        Preconditions.checkArgument(!file.isEmpty(), "简历不能为空");
        String realVerifyCode = stringRedisTemplate.opsForValue().get(mobile);
        if (StringUtils.isBlank(realVerifyCode) || !realVerifyCode.equals(verifyCode)) {
            throw new BusinessException("验证码不正确");
        }
        String fileName = file.getOriginalFilename();
        File uploadFile = new File(AppProperties.UPLOAD_FILE_PATH + fileName);
        try {
            FileUtils.writeByteArrayToFile(uploadFile, file.getBytes());
        } catch (IOException e) {
            throw new BusinessException("文件上传异常");
        }
        SysUser user = new SysUser();
        user.setPersonName(name);
        user.setWxNickName(wxNickName);
        user.setWxNum(wxNum);
        user.setOpenId(openId);
        user.setMobile(mobile);
        user.setResumeFilePath(uploadFile.getPath());
        userService.addUser(user);
        return WrapMapper.ok();
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public Wrapper login(@RequestParam String mobile, @RequestParam String verifyCode) {
        SysUser user = userService.getUserByMobile(mobile);
        String realVerifyCode = stringRedisTemplate.opsForValue().get(mobile);
        if (StringUtils.isBlank(realVerifyCode) || !realVerifyCode.equals(verifyCode)) {
            throw new BusinessException("验证码不正确");
        }
        SessionPOJO sessionPOJO = sessionPOJOService.initSession(user);
        String salt = CommonUtil.getRandomString(6);
        String token = JwtUtil.createToken(salt, sessionPOJO.getId(), 10000L);
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserUid(user.getId());
        loginDTO.setAgent(salt);
        loginDTO.setToken(token);
        return WrapMapper.ok();
    }

    /**
     * 获取验证码
     */
    @GetMapping("/verifyCode/{mobile}")
    public Wrapper getVerifyCode(@PathVariable("mobile") String mobile) {
        String verifyCode = CommonUtil.getRandomString(6);
        stringRedisTemplate.opsForValue().set(mobile, verifyCode);
        return WrapMapper.ok(verifyCode);
    }

    /**
     * 获取使用说明
     */
    @PostMapping("/getDeclare")
    public Wrapper getDeclare() {
        SysDeclare declare = declareService.getDefaultDeclare();
        return WrapMapper.ok(declare);
    }
}
