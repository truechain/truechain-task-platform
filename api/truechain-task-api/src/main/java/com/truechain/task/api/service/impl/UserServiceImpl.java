package com.truechain.task.api.service.impl;

import com.google.common.base.Preconditions;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.truechain.task.api.model.dto.AccessTokenDTO;
import com.truechain.task.api.model.dto.RecommendTaskDTO;
import com.truechain.task.api.model.dto.ReferrerDTO;
import com.truechain.task.api.model.dto.UserAccountDTO;
import com.truechain.task.api.model.dto.UserInfoDTO;
import com.truechain.task.api.model.dto.WxUserinfoDTO;
import com.truechain.task.api.repository.BsRecommendTaskRepository;
import com.truechain.task.api.repository.BsUserAccountDetailRepository;
import com.truechain.task.api.repository.BsUserAccountRepository;
import com.truechain.task.api.repository.SysUserRepository;
import com.truechain.task.api.service.UserService;
import com.truechain.task.api.service.weixin.WeiXinService;
import com.truechain.task.core.BusinessException;
import com.truechain.task.core.NullException;
import com.truechain.task.model.entity.*;
import com.truechain.task.model.enums.AuditStatusEnum;
import com.truechain.task.util.ShareCodeUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private SysUserRepository userRepository;

	@Autowired
	private BsUserAccountRepository userAccountRepository;

	@Autowired
	private BsUserAccountDetailRepository userAccountDetailRepository;

	@Autowired
	private BsRecommendTaskRepository recommendTaskRepository;

	@Override
	public SysUser getUserByOpenId(String openId) {
		SysUser sysUser = userRepository.findByOpenId(openId);
		// Preconditions.checkArgument(null != sysUser, "该用户不存在");
		if (sysUser == null) {
			throw new NullException("该用户不存在");
		}
		return sysUser;
	}

	@Override
	public SysUser getUserByMobile(String mobile) {
		SysUser sysUser = userRepository.findByMobile(mobile);
		return sysUser;
	}

	@Override
	public SysUser addUser(SysUser user) {
		long count = userRepository.countByMobile(user.getMobile());
		if (count > 0) {
			throw new BusinessException("手机号已经注册");
		}
		user = userRepository.save(user);
		return user;
	}

	@Override
	public SysUser updateUser(SysUser user) {
		SysUser sysUser = userRepository.findOne(user.getId());
		Preconditions.checkArgument(!sysUser.getMobile().equals(sysUser.getRecommendUserMobile()), "推荐人不能为用户本人手机号");
		if (sysUser == null) {
			throw new NullException("用户不存在");
		}
		sysUser.setPersonName(user.getPersonName());
		sysUser.setWxNickName(user.getWxNickName());
		sysUser.setWxNum(user.getWxNum());
		sysUser.setOpenId(user.getOpenId());
		sysUser.setTrueChainAddress(user.getTrueChainAddress());
		sysUser.setResumeFilePath(user.getResumeFilePath());
		sysUser.setAuditStatus(AuditStatusEnum.UNAUDITED.getCode());
		sysUser.setRecommendUserId(user.getRecommendUserId());
		sysUser.setRecommendUserMobile(user.getRecommendUserMobile());
		sysUser.setRecommendShareCode(user.getRecommendShareCode());
		sysUser = userRepository.save(sysUser);
		BsUserAccount userAccount = new BsUserAccount();
		userAccount.setUser(sysUser);
		userAccount.setGitReward(BigDecimal.ZERO);
		userAccount.setTrueReward(BigDecimal.ZERO);
		userAccount.setTtrReward(BigDecimal.ZERO);
		userAccountRepository.save(userAccount);
		return sysUser;
	}
	
	@Override
	public SysUser updateSimpleUser(SysUser user){
		SysUser sysUser = userRepository.findOne(user.getId());
		Preconditions.checkArgument(!sysUser.getMobile().equals(sysUser.getRecommendUserMobile()), "推荐人不能为用户本人手机号");
		if (sysUser == null) {
			throw new NullException("用户不存在");
		}
		sysUser.setPersonName(user.getPersonName());
		sysUser.setTrueChainAddress(user.getTrueChainAddress());
		sysUser = userRepository.save(sysUser);
		return sysUser;
	}

	@Override
	public UserInfoDTO getUserInfo(long userId, Integer rewardType) {
		UserInfoDTO userInfoDTO = new UserInfoDTO();
		SysUser sysUser = userRepository.findOne(userId);
		// Preconditions.checkArgument(null != sysUser, "该用户不存在");
		if (sysUser == null) {
			throw new NullException("用户不存在");
		}
		userInfoDTO.setUser(sysUser);
//		List<SysUser> recommendUserIds = userRepository.findByRecommendUserId(sysUser.getId());
//		long count = 0;
//		for (int i = 0; i < recommendUserIds.size(); i++) {
//			SysUser user = recommendUserIds.get(i);
//			// 只记录审核通过的玩家
//			if (user.getAuditStatus() == AuditStatusEnum.AUDITED.getCode()) {
//				count++;
//			}
//		}
		long count = userRepository.countByRecommendUserIdAndAuditStatus(sysUser.getId());
		userInfoDTO.setRecommendPeople(count);
		UserAccountDTO userAccountDTO = new UserAccountDTO();
		userAccountDTO.setGitReward("0");
		userAccountDTO.setTrueReward("0");
		userAccountDTO.setTtrReward("0");
		BsUserAccount userAccount = sysUser.getUserAccount();
		if (userAccount != null) {
			userAccountDTO.setGitReward(userAccount.getGitReward().toString());
			userAccountDTO.setTrueReward(userAccount.getTrueReward().toString());
			userAccountDTO.setTtrReward(userAccount.getTtrReward().toString());
			QBsUserAccountDetail accountDetail = QBsUserAccountDetail.bsUserAccountDetail;
			Iterable<BsUserAccountDetail> userAccountDetailIterable = userAccountDetailRepository
					.findAll(accountDetail.userAccount.eq(userAccount).and(accountDetail.rewardType.eq(rewardType)));
			List<BsUserAccountDetail> userAccountDetailList = new ArrayList<>();
			userAccountDetailIterable.forEach(x -> userAccountDetailList.add(x));
			Collections.sort(userAccountDetailList, new Comparator<BsUserAccountDetail>() {
				@Override
				public int compare(BsUserAccountDetail o1, BsUserAccountDetail o2) {
					long id1 = o1.getId();
					long id2 = o2.getId();
					if (id1 == id2) {
						return 0;
					} else if (id1 > id2) {
						return -1;
					} else {
						return 1;
					}
				}
			});
			userAccountDTO.setAccountDetails(userAccountDetailList);
		}
		userInfoDTO.setUserAccount(userAccountDTO);
		return userInfoDTO;
	}

	@Override
	public SysUser getByUserId(Long userId) {
		SysUser sysUser = userRepository.findOne(userId);
		// Preconditions.checkArgument(null != sysUser, "该用户不存在");
		if (sysUser == null) {
			throw new NullException("用户不存在");
		}
		return sysUser;
	}

	@Override
	public List<RecommendTaskDTO> getRecommendUserList(long userId) {
		SysUser user = userRepository.findOne(userId);
		// Preconditions.checkArgument(null != user, "用户不存在");
		if (user == null) {
			throw new NullException("用户不存在");
		}
		List<SysUser> sysUserList = userRepository.findByRecommendUserId(user.getId());
		List<RecommendTaskDTO> recommendTaskDTOList = new ArrayList<>();
		for (int i = 0; i < sysUserList.size(); i++) {
			SysUser sysUser = sysUserList.get(i);
			if (sysUser.getAuditStatus() != AuditStatusEnum.AUDITED.getCode()) {
				continue;
			}
			RecommendTaskDTO recommendTaskDTO = new RecommendTaskDTO();
			recommendTaskDTO.setPersonName(sysUser.getPersonName());
			recommendTaskDTO.setRewardNum(sysUser.getLevel());
			recommendTaskDTO.setCreateTime(sysUser.getUpdateTime());
			recommendTaskDTOList.add(recommendTaskDTO);
		}
		// QBsUserAccountDetail qUserAccountDetail =
		// QBsUserAccountDetail.bsUserAccountDetail;
		// Iterable<BsUserAccountDetail> userAccountDetailIterable =
		// userAccountDetailRepository.findAll(qUserAccountDetail.userAccount.user.eq(user));
		// List<RecommendTaskDTO> recommendTaskDTOList = new ArrayList<>();
		// userAccountDetailIterable.forEach(x -> {
		// RecommendTaskDTO recommendTaskDTO = new RecommendTaskDTO();
		// recommendTaskDTO.setPersonName(x.getUserAccount().getUser().getPersonName());
		// recommendTaskDTO.setRewardNum(x.getRewardNum());
		// recommendTaskDTO.setCreateTime(x.getCreateTime());
		// recommendTaskDTOList.add(recommendTaskDTO);
		// });
		return recommendTaskDTOList;
	}

	@Override
	public ReferrerDTO getReferrerByCode(String referralCode) {
		try {
			String mobile = String.valueOf(ShareCodeUtil.codeToNum(referralCode));
			SysUser sysUser = getUserByMobile(mobile);
			if (null != sysUser) {
				ReferrerDTO referrerDTO = new ReferrerDTO();
				referrerDTO.setReferrerCode(referralCode);
				referrerDTO.setPersonName(sysUser.getPersonName());
				referrerDTO.setLevel(sysUser.getLevel());
				return referrerDTO;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Autowired
	WeiXinService weiXinService;
	
	@Override
	public void getWxUserInfo(String code,Long userId){
		SysUser sysUser= userRepository.getOne(userId);
		Preconditions.checkArgument(sysUser!=null, "用户不存在");
		Preconditions.checkArgument(StringUtils.isEmpty(sysUser.getOpenId()), "用户已经绑定过");
		String url = WeiXinService.oauth2OokenUrl.replace("CODE", code);
		AccessTokenDTO accessTokenDTO = weiXinService.getAccessTokenVo(url);
		Preconditions.checkArgument(StringUtils.isBlank(accessTokenDTO.getErrcode()), "用户绑定异常");
		url = WeiXinService.userinfoUrl.replace("ACCESS_TOKEN", accessTokenDTO.getAccess_token()).replace("OPENID", accessTokenDTO.getOpenid());
		WxUserinfoDTO wxUserinfoDTO = null;
		try {
			wxUserinfoDTO = weiXinService.getUserinfo(url);
		} catch (RestClientException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Preconditions.checkArgument(StringUtils.isBlank(wxUserinfoDTO.getErrcode()), "用户绑定异常");
		Preconditions.checkArgument(userRepository.countByOpenId(wxUserinfoDTO.getOpenid()) == 0 , "微信已被绑定");
		
		sysUser.setOpenId(wxUserinfoDTO.getOpenid());
		sysUser.setWxNickName(wxUserinfoDTO.getNickname());
		sysUser.setWxImageUrl(wxUserinfoDTO.getHeadimgurl());
		userRepository.save(sysUser);
		
	}
}
