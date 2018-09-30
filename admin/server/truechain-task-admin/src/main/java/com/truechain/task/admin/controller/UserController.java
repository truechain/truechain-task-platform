package com.truechain.task.admin.controller;

import com.google.common.base.Preconditions;
import com.truechain.task.admin.model.dto.UserDTO;
import com.truechain.task.admin.model.dto.UserDetailDTO;
import com.truechain.task.admin.service.UserService;
import com.truechain.task.core.BusinessException;
import com.truechain.task.core.WrapMapper;
import com.truechain.task.core.Wrapper;
import com.truechain.task.model.entity.SysUser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * 用户Controller
 */
@RestController
@RequestMapping("/user")
public class UserController extends BasicController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户记录
     */
    @PostMapping("/getUserPage")
    public Wrapper getUserPage(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestBody UserDTO user) {
        Preconditions.checkArgument(user.getPageIndex() > 0, "分页信息错误");
        Preconditions.checkArgument(user.getPageSize() > 1, "分页信息错误");
        Pageable pageable = new PageRequest(user.getPageIndex() - 1, user.getPageSize());
        Page<SysUser> userPage = userService.getUserPage(user, pageable);
        return WrapMapper.ok(userPage);
    }

    /**
     * 获取用户详情
     */
    @PostMapping("/getUserInfo")
    public Wrapper getUserInfo(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam Long userId) {
        UserDetailDTO userInfo = userService.getUserInfo(userId);
        return WrapMapper.ok(userInfo);
    }

    /**
     * 下载文件
     */
    @GetMapping("/downLoadResume")
    public void downLoadResume(@RequestParam Long userId, final HttpServletResponse response) {
        SysUser user = userService.getUser(userId);
        String resumePath = user.getResumeFilePath();
        if (StringUtils.isBlank(resumePath)) {
            throw new BusinessException("用户未上传简历文件");
        }
        File file = new File(resumePath);
        try {
            byte[] data = FileUtils.readFileToByteArray(file);
            String fileName = resumePath.substring(resumePath.lastIndexOf("/"));
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            response.addHeader("Content-Length", "" + data.length);
            response.setContentType("application/octet-stream;charset=UTF-8");
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            outputStream.write(data);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            logger.error("下载异常", e);
        }
    }

    /**
     * 修改用户
     */
    @PostMapping("/updateUser")
    public Wrapper updateUser(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam long userId, @RequestParam String level) {
        SysUser user = new SysUser();
        user.setId(userId);
        user.setLevel(level);
        userService.updateUser(user);
        return WrapMapper.ok();
    }

    /**
     * 审核用户
     */
    @PostMapping("/auditUser")
    public Wrapper auditUser(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam Long userId, @RequestParam String level, @RequestParam String rewardNum) {
        userService.auditUser(userId, level, rewardNum);
        return WrapMapper.ok();
    }
}
