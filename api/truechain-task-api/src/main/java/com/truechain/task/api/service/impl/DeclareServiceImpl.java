package com.truechain.task.api.service.impl;

import com.truechain.task.api.repository.SysDeclareRepository;
import com.truechain.task.api.service.DeclareService;
import com.truechain.task.model.entity.SysDeclare;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeclareServiceImpl implements DeclareService {

    @Autowired
    private SysDeclareRepository declareRepository;

    @Override
    public SysDeclare getDefaultDeclare() {
        SysDeclare declare = declareRepository.findFirstByOrderByVersionDesc();
        return declare;
    }
}
