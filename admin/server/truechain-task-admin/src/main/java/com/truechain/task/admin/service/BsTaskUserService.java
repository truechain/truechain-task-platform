package com.truechain.task.admin.service;

import com.truechain.task.model.entity.BsTaskUser;

import java.util.Collection;
import java.util.List;

public interface BsTaskUserService {

    List<BsTaskUser> getBsTaskUserByUserIds(Collection<Long> userIds);


}
