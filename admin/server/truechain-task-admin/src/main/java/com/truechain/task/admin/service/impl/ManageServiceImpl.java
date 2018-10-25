package com.truechain.task.admin.service.impl;
import com.querydsl.core.BooleanBuilder;
import com.truechain.task.admin.model.dto.ManageDTO;
import com.truechain.task.admin.repository.ConfigManageRepository;
import com.truechain.task.admin.service.ManageService;
import com.truechain.task.model.entity.ConfigManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ManageServiceImpl implements ManageService {

    @Autowired
    private ConfigManageRepository configManageRepository;
    @Override
    public Page<ConfigManage> getConfigManage( Pageable pageable) {
        Page<ConfigManage> managePage=configManageRepository.findAll(pageable);
        return managePage;
    }
}
