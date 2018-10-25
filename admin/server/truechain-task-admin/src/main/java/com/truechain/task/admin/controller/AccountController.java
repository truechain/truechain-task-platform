package com.truechain.task.admin.controller;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Preconditions;
import com.truechain.task.admin.service.AccountService;
import com.truechain.task.core.BusinessException;
import com.truechain.task.core.WrapMapper;
import com.truechain.task.core.Wrapper;
import com.truechain.task.model.dto.LoginDTO;
import com.truechain.task.util.RandomValidateCodeUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

/**
 * 账户Controller
 */
@RestController
@RequestMapping("/unauth/account")
public class AccountController extends BasicController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    /**
     * 注册
     *
     * @return
     */
    /*@PostMapping("/register")
    public Wrapper register(@RequestBody AuthUser user) {
        Preconditions.checkArgument(!StringUtils.isEmpty(user.getUsername()), "账户信息缺失");
        Preconditions.checkArgument(!StringUtils.isEmpty(user.getPassword()), "账户信息缺失");
        accountService.register(user);
        return WrapMapper.ok();
    }*/

    /**
     * 登录
     *
     * @return
     */
    @PostMapping("/login")
    public Wrapper login(@RequestParam String userName, @RequestParam String password,
    		@RequestParam String verifyCodeImage,@RequestParam String verifyToken) {
    	Preconditions.checkArgument(StringUtils.isEmpty(verifyCodeImage)==false, "图形验证码不允许为空");
    	ValueOperations<String, String> voperations = stringRedisTemplate.opsForValue();
		String randomString = voperations.get(RandomValidateCodeUtil.RANDOMCODEKEY+"_"+verifyToken);
		if(verifyCodeImage.equalsIgnoreCase(String.valueOf(randomString)) == false){
			throw new BusinessException("图形验证码错误");
		}
        LoginDTO loginDTO = accountService.login(userName, password);
        return WrapMapper.ok(loginDTO);
    }
    
    /**
     * 获取图形验证码 
     */
    @GetMapping("/verifyCodeImage")
    public void verifyCodeImage(@RequestParam String verifyToken){
    	try {
    		Preconditions.checkArgument(StringUtils.isEmpty(verifyToken)==false, "图形验证码不允许为空");
    		String randomString = RandomValidateCodeUtil.getRandcode(response.getOutputStream());
			ValueOperations<String, String> voperations = stringRedisTemplate.opsForValue();
			voperations.set(RandomValidateCodeUtil.RANDOMCODEKEY+"_"+verifyToken, randomString,300,TimeUnit.SECONDS);
    	} catch (IOException e) {			
			e.printStackTrace();
		}
    }


    /**
     * 用户登出
     */
    @GetMapping("/exit")
    public Wrapper accountExit(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent) {

        return WrapMapper.ok();
    }

}
