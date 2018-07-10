package com.truechain.task.admin.controller;

import com.google.common.base.Preconditions;
import com.truechain.task.core.WrapMapper;
import com.truechain.task.core.Wrapper;
import com.truechain.task.model.dto.LoginDTO;
import com.truechain.task.model.entity.AuthUser;
import com.truechain.task.admin.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 账户Controller
 */
@RestController
@RequestMapping("/unauth/account")
@Api(value = "/unauth/account", tags = "账户", description = "账户相关操作")
public class AccountController extends BasicController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    /**
     * 注册
     *
     * @return
     */
    @PostMapping("/register")
    @ApiOperation(value = "用户注册")
    @ApiResponses(value = {@ApiResponse(code = 405, message = "Invalid input")})
    public Wrapper register(@RequestBody AuthUser user) {
        Preconditions.checkArgument(!StringUtils.isEmpty(user.getUsername()), "账户信息缺失");
        Preconditions.checkArgument(!StringUtils.isEmpty(user.getPassword()), "账户信息缺失");
        accountService.register(user);
        return WrapMapper.ok();
    }

    /**
     * 登录
     *
     * @return
     */
    @PostMapping("/login")
    public Wrapper login(@RequestParam String userName, @RequestParam String password) {
        LoginDTO loginDTO = accountService.login(userName, password);
        return WrapMapper.ok(loginDTO);
    }


}
