package com.truechain.task.admin.service.impl;

import com.google.common.collect.Sets;
import com.truechain.task.admin.repository.BsTaskUserRepository;
import com.truechain.task.admin.service.BsTaskUserService;
import com.truechain.task.model.entity.BsTaskUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class BsTaskUserServiceImpl implements BsTaskUserService {

    @Autowired
    private BsTaskUserRepository bsTaskUserRepository;

    @Override
    public List<BsTaskUser> getBsTaskUserByUserIds(Collection<Long> userIds) {
        return bsTaskUserRepository.findByUserIds(userIds);
    }
}
