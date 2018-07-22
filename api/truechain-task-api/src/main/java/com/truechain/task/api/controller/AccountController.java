package com.truechain.task.api.controller;

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
import com.truechain.task.util.SMSHttpRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

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

    @Value("${app.sms.smsUserName}")
    private String smsUserName;

    @Value("${app.sms.smsPassword}")
    private String smsPassword;

    /**
     * 注册
     */
    @PostMapping("/register")
    public Wrapper register(@RequestParam String mobile, @RequestParam String verifyCode) {
        String realVerifyCode = stringRedisTemplate.opsForValue().get(mobile);
        if (StringUtils.isBlank(realVerifyCode)) {
            throw new BusinessException("验证码已过期");
        }
        if (!realVerifyCode.equals(verifyCode)) {
            throw new BusinessException("验证码不正确");
        }
        SysUser user = new SysUser();
        user.setMobile(mobile);
        user.setAuditStatus(0);
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
        if (StringUtils.isBlank(realVerifyCode)) {
            throw new BusinessException("验证码已过期");
        }
        if (!realVerifyCode.equals(verifyCode)) {
            throw new BusinessException("验证码不正确");
        }
        SessionPOJO sessionPOJO = sessionPOJOService.initSession(user);
        String salt = CommonUtil.getRandomString(6);
        String token = JwtUtil.createToken(salt, sessionPOJO.getId(), 259200000L);
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserUid(user.getId());
        loginDTO.setAgent(salt);
        loginDTO.setToken(token);
        return WrapMapper.ok(loginDTO);
    }

    /**
     * 获取验证码
     */
    @GetMapping("/verifyCode/{mobile}")
    public Wrapper getVerifyCode(@PathVariable("mobile") String mobile) {
        String verifyCode = CommonUtil.getRandomString(6);
        SMSHttpRequest.sendVerifyCodeSMS(smsUserName, smsPassword, mobile, verifyCode);                                //调用SMSAPI发送验证码短信
        stringRedisTemplate.opsForValue().set(mobile, verifyCode, 3, TimeUnit.MINUTES);
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
