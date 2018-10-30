package com.truechain.task.api.controller;

import com.truechain.task.api.model.dto.ReferrerDTO;
import com.truechain.task.api.model.dto.SessionPOJO;
import com.truechain.task.api.model.dto.TaskDTO;
import com.truechain.task.api.service.TaskService;
import com.truechain.task.api.service.UserService;
import com.truechain.task.api.service.weixin.WeiXinService;
import com.truechain.task.core.WrapMapper;
import com.truechain.task.core.Wrapper;
import com.truechain.task.model.entity.BsTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/unauth")
public class UnAuthController extends BasicController {

	@Autowired
	private UserService userService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private WeiXinService weiXinService;

	/**
	 * 获取任务分页数据
	 */
	@PostMapping("/task/getTaskPage")
	public Wrapper getTaskPage(@RequestParam(required = false) String taskName,
			@RequestParam(required = false) Integer category, @RequestParam(required = false) String level,
			@RequestParam(required = false) Integer reward, @RequestParam int pageIndex, @RequestParam int pageSize) {
		BsTask task = new BsTask();
		task.setName(taskName);
		task.setCategory(category);
		task.setLevel(level);
		task.setRewardType(reward);
		Page<BsTask> taskPage = taskService.getTaskPage(task, pageIndex, pageSize);
		return WrapMapper.ok(taskPage);
	}

	/**
	 * 获取任务详情
	 */
	@PostMapping("/task/getTaskInfo")
	public Wrapper getTaskInfo(@RequestParam Long taskId) {
		SessionPOJO sessionPOJO = getSessionPoJO();
		Long userId = null;
		if (null != sessionPOJO) {
			userId = sessionPOJO.getUserId();
		}
		TaskDTO taskDTO = taskService.getTaskInfo(taskId, userId);
		return WrapMapper.ok(taskDTO);
	}

	/**
	 * 获取微信签名加密
	 */
	@PostMapping(value = "/weixin/getWxSign")
	public Wrapper getSign(String url) {
		Map resultMap = weiXinService.getSign(url);
		if (CollectionUtils.isEmpty(resultMap) || StringUtils.isEmpty(resultMap.get("signature"))) {
			return WrapMapper.error("微信签名失败");
		}
		return WrapMapper.ok(resultMap);
	}

	/**
	 * 获取推荐人
	 */
	@GetMapping("/getReferrer")
	public Wrapper getReferrer(String referralCode) {
		ReferrerDTO dto = userService.getReferrerByCode(referralCode);
		if (null != dto) {
			return WrapMapper.ok(dto);

		}
		return WrapMapper.error("推荐人不存在");
	}

	/**
	 * 获取微信的access_token
	 */
	@GetMapping("/getWxUserInfo")
	public String getWxUserInfo(HttpServletRequest httpServletRequest,String code, String state) {
		try {
			userService.getWxUserInfo(code, Long.valueOf(state));
		} catch (Exception e) {
			return "redirect:"+httpServletRequest.getContextPath()+"/#/invite-reg-success";
		}
		return "redirect:"+httpServletRequest.getContextPath()+"/#/mine";
	}

}
