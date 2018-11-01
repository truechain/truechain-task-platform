package com.truechain.task.admin.controller;

import io.swagger.annotations.ApiOperation;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
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
import com.truechain.task.admin.model.dto.UserDTO;
import com.truechain.task.admin.model.dto.UserDetailDTO;
import com.truechain.task.admin.service.UserService;
import com.truechain.task.core.BusinessException;
import com.truechain.task.core.WrapMapper;
import com.truechain.task.core.Wrapper;
import com.truechain.task.model.entity.SysUser;
import com.truechain.task.model.enums.AuditStatusEnum;
import com.truechain.task.util.ShareCodeUtil;
import com.truechain.task.util.ValidateUtil;

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
    @ApiOperation(value="分页获取用户记录")
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
    @ApiOperation(value="获取用户详情")
    @PostMapping("/getUserInfo")
    public Wrapper getUserInfo(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam Long userId) {
        UserDetailDTO userInfo = userService.getUserInfo(userId);
        return WrapMapper.ok(userInfo);
    }

    /**
     * 下载文件
     */
    @ApiOperation(value="下载简历")
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
     * 创建用户
     */
    @ApiOperation(value = "创建用户")
    @PostMapping("/addUser")
    public Wrapper addUser(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, 
    		@RequestParam String name, @RequestParam String wxNickName, @RequestParam String wxNum, 
    		@RequestParam String mobile,@RequestParam String trueChainAddress, @RequestParam("file") MultipartFile file,
    		@RequestParam String recommendResource, @RequestParam(required = false) String recommendShareCode) {
    	Preconditions.checkArgument(!file.isEmpty(), "简历不能为空");
        String fileName = file.getOriginalFilename();
        Preconditions.checkArgument(fileName.indexOf(".exe") < 0 && fileName.indexOf(".sh") < 0, "上传文件不合法");

        File uploadFile = new File(AppProperties.UPLOAD_FILE_PATH + UUID.randomUUID().toString().replace("-", "") + fileName);
        Preconditions.checkArgument(file.getSize() <= 10 * 1024 * 1024, "文件最大限制为10M");
        try {
            FileUtils.writeByteArrayToFile(uploadFile, file.getBytes());
        } catch (IOException e) {
            throw new BusinessException("文件上传异常");
        }

        SysUser user = new SysUser();
        user.setMobile(mobile);
    	user.setAuditStatus(AuditStatusEnum.UNAUDITED.getCode());
        user.setPersonName(name);
        user.setWxNickName(wxNickName);
        user.setWxNum(wxNum);
        user.setRecommendResource(recommendResource);
        user.setTrueChainAddress(trueChainAddress);
        user.setResumeFilePath(uploadFile.getPath());

        if (StringUtils.isEmpty(recommendShareCode) == false) {
        	String referrerPhone = String.valueOf(ShareCodeUtil.codeToNum(recommendShareCode));
            Preconditions.checkArgument(ValidateUtil.isMobile(referrerPhone), "手机号不合法");
            SysUser referrerUser = userService.getUserByMobile(referrerPhone);
            if (referrerUser == null) {
                throw new BusinessException("没有找到该推荐人");
            }
            user.setRecommendShareCode(recommendShareCode);
            user.setRecommendUserId(referrerUser.getId());
            user.setRecommendUserMobile(referrerPhone);
        }
    	
        userService.addUser(user);
        return WrapMapper.ok();
    }
    
    /**
     * 修改用户
     */
    @ApiOperation(value = "修改用户")
    @PostMapping("/updateUser")
    public Wrapper updateUser(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, 
    		@RequestParam Long id,@RequestParam String name, @RequestParam String wxNickName, @RequestParam String wxNum, 
    		 @RequestParam(value = "file", required = false) MultipartFile file,
    		 @RequestParam(required = false) String recommendResource, @RequestParam(required = false) String recommendShareCode) {
    	SysUser user = new SysUser();    	
    	user.setId(id);
    	if(!file.isEmpty()){
    		String fileName = file.getOriginalFilename();
            Preconditions.checkArgument(fileName.indexOf(".exe") < 0 && fileName.indexOf(".sh") < 0, "上传文件不合法");

            File uploadFile = new File(AppProperties.UPLOAD_FILE_PATH + UUID.randomUUID().toString().replace("-", "") + fileName);
            Preconditions.checkArgument(file.getSize() <= 10 * 1024 * 1024, "文件最大限制为10M");
            try {
                FileUtils.writeByteArrayToFile(uploadFile, file.getBytes());
            } catch (IOException e) {
                throw new BusinessException("文件上传异常");
            }
            user.setResumeFilePath(uploadFile.getPath());
    	}
    	user.setAuditStatus(AuditStatusEnum.UNAUDITED.getCode());
        user.setPersonName(name);
        user.setWxNickName(wxNickName);
        user.setWxNum(wxNum);
        user.setRecommendResource(recommendResource);
        
        if (StringUtils.isEmpty(recommendShareCode) == false) {
        	String referrerPhone = String.valueOf(ShareCodeUtil.codeToNum(recommendShareCode));
            Preconditions.checkArgument(ValidateUtil.isMobile(referrerPhone), "手机号不合法");
            SysUser referrerUser = userService.getUserByMobile(referrerPhone);
            if (referrerUser == null) {
                throw new BusinessException("没有找到该推荐人");
            }
            user.setRecommendShareCode(recommendShareCode);
            user.setRecommendUserId(referrerUser.getId());
            user.setRecommendUserMobile(referrerPhone);
        }
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now=date.format(new Date());
        user.setUpdatetime(now);
        userService.updateUser(user);
    	return WrapMapper.ok();
    }

    /**
     * 修改用户的等级
     */
    @ApiOperation(value = "修改用户的等级")
    @PostMapping("/updateUserLevel")
    public Wrapper updateUserLevel(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam long userId, @RequestParam String level) {
        SysUser user = new SysUser();
        user.setId(userId);
        user.setLevel(level);
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now=date.format(new Date());
        user.setUpdatetime(now);
        userService.updateUserLevel(user);
        return WrapMapper.ok();
    }
    
    /**
     * 将用户拉黑
     */
    @ApiOperation(value="将用户拉黑")
    @PostMapping("/updateUserBlank")
    public Wrapper updateUserBlank(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam long userId, @RequestParam int auditStatus) {
        SysUser user = new SysUser();
        user.setId(userId);
        user.setAuditStatus(auditStatus);
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now=date.format(new Date());
        user.setUpdatetime(now);

        userService.updateUserBlank(user);
        return WrapMapper.ok();
    }   

    /**
     * 审核用户
     */
    @ApiOperation(value="审核用户")
    @PostMapping("/auditUser")
    public Wrapper auditUser(@RequestHeader("Token") String token, @RequestHeader("Agent") String agent, @RequestParam Long userId, 
    		@RequestParam String level, @RequestParam String rewardNum,@RequestParam String recommendResource) {
        userService.auditUser(userId, level, rewardNum,recommendResource);
        return WrapMapper.ok();
    }
}
