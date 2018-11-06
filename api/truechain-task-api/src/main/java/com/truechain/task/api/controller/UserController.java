package com.truechain.task.api.controller;

import com.google.common.base.Preconditions;
import com.truechain.task.api.config.AppProperties;
import com.truechain.task.api.model.dto.RecommendTaskDTO;
import com.truechain.task.api.model.dto.SessionPOJO;
import com.truechain.task.api.model.dto.UserInfoDTO;
import com.truechain.task.api.repository.BsRecommendTaskRepository;
import com.truechain.task.api.service.UserService;
import com.truechain.task.core.BusinessException;
import com.truechain.task.core.WrapMapper;
import com.truechain.task.core.Wrapper;
import com.truechain.task.model.entity.SysDeclare;
import com.truechain.task.model.entity.SysUser;
import com.truechain.task.util.ShareCodeUtil;
import com.truechain.task.util.ValidateUtil;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

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
	public Wrapper updateUserInfo(@RequestParam Long userId, @RequestParam String name, @RequestParam String wxNickName,
			@RequestParam(required = false) String wxNum, @RequestParam(required = false) String openId,
			@RequestParam(required = false) String trueChainAddress, @RequestParam(required = false) MultipartFile file,
			@RequestParam(required = false) String referrerCode) {
		//如果上传过简历 则更新资料的时候 不需要再次上传
		SysUser initUser = userService.getByUserId(userId);
				
		String resumeFilePath = "";
		if(null != initUser && null != initUser.getResumeFilePath()){
			resumeFilePath = initUser.getResumeFilePath();
		}else{
			if(null == file || file.isEmpty()){
				return WrapMapper.errorParam("简历不能为空");
			}else{
				String fileName = file.getOriginalFilename();
				Preconditions.checkArgument(fileName.indexOf(".exe") < 0 && fileName.indexOf(".sh") < 0, "上传文件不合法");

				File uploadFile = new File(AppProperties.UPLOAD_FILE_PATH + UUID.randomUUID().toString().replace("-", "") + fileName);
				
				if(file.getSize() > 10 * 1024 * 1024){
					//Preconditions.checkArgument(file.getSize() <= 10 * 1024 * 1024, "文件最大限制为10M");
					return WrapMapper.errorParam("文件最大限制为10M");
				}
				
				try {
					FileUtils.writeByteArrayToFile(uploadFile, file.getBytes());
					resumeFilePath = uploadFile.getPath();
				} catch (IOException e) {
					throw new BusinessException("文件上传异常");
				}
			}
		}

		SysUser user = new SysUser();
		user.setId(userId);
		user.setPersonName(name);
		user.setWxNickName(wxNickName);
		user.setWxNum(wxNum);
		user.setOpenId(openId);
		user.setTrueChainAddress(trueChainAddress);
		user.setResumeFilePath(resumeFilePath);

		if (StringUtils.isEmpty(referrerCode) == false) {
			//根据code反向得到手机号码
			Long phoneNum = ShareCodeUtil.codeToNum(referrerCode);
			//Preconditions.checkArgument(ValidateUtil.isMobile(phoneNum.toString()), "邀请码不存在");
			if(!ValidateUtil.isMobile(phoneNum.toString())){
				return WrapMapper.errorParam("邀请码不存在");
			}
			
			SysUser referrerUser = userService.getUserByMobile(phoneNum.toString());
			if (referrerUser == null) {
				throw new BusinessException("没有找到该推荐人");
			}
			user.setRecommendUserId(referrerUser.getId());
			user.setRecommendUserMobile(referrerUser.getMobile());
			user.setRecommendShareCode(referrerCode);
		}

		userService.updateUser(user);
		return WrapMapper.ok();
	}
	
	/**
	 * 更新个人信息 简版
	 */
	@PostMapping("/updateSimpleUserInfo")
	public Wrapper updateSimpleUserInfo(@RequestParam Long userId, @RequestParam String name, @RequestParam String trueChainAddress){
		SysUser user = new SysUser();
		user.setId(userId);
		user.setPersonName(name);
		user.setTrueChainAddress(trueChainAddress);
		userService.updateSimpleUser(user);
		return WrapMapper.ok();
	}

	/**
	 * 获取推荐码
	 */
	@GetMapping("/getReferralCode")
	public Wrapper getReferralCode() {
		SessionPOJO sessionPOJO = getSessionPoJO();
		Long userId = sessionPOJO.getUserId();
		SysUser user = userService.getByUserId(userId);
		String mobile = user.getMobile();
		return WrapMapper.ok(ShareCodeUtil.numToCode(Long.valueOf(mobile)));
	}
}
