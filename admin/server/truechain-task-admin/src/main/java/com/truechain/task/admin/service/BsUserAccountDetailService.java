package com.truechain.task.admin.service;

import com.truechain.task.admin.model.dto.RewardViewDTO;
import com.truechain.task.admin.model.dto.TimeRangeDTO;
import com.truechain.task.admin.model.dto.UserDTO;
import com.truechain.task.model.entity.BsUserAccountDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BsUserAccountDetailService {

    Page<BsUserAccountDetail> getBsUserAccountDetail(RewardViewDTO rewardViewDTO, Pageable pageable);


    Page<BsUserAccountDetail> getRecommendTask(UserDTO userDTO, Pageable pageable);


    Page<BsUserAccountDetail> getBsUserAccountDetail(TimeRangeDTO timeRangeDTO, Pageable pageable);
}
