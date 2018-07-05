package com.truechain.task.admin.service.impl;

import com.google.common.base.Preconditions;
import com.truechain.task.model.entity.SysDeclare;
import com.truechain.task.admin.repository.SysDeclareRepository;
import com.truechain.task.admin.service.DeclareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DeclareServiceImpl implements DeclareService {

    @Autowired
    private SysDeclareRepository declareRepository;

    @Override
    public Page<SysDeclare> getDeclarePage(SysDeclare declare, Pageable pageable) {
        Page<SysDeclare> declarePage = declareRepository.findAll(pageable);
        return declarePage;
    }

    @Override
    public SysDeclare getDeclareInfo(Long declareId) {
        SysDeclare declare = declareRepository.findOne(declareId);
        return declare;
    }

    @Override
    public SysDeclare addDeclare(SysDeclare declare) {
        declare = declareRepository.save(declare);
        return declare;
    }

    @Override
    public SysDeclare updateDeclare(SysDeclare declare) {
        SysDeclare sysDeclare = declareRepository.findOne(declare.getId());
        Preconditions.checkArgument(null != sysDeclare, "该条说明不存在");
        sysDeclare.setContent(declare.getContent());
        sysDeclare = declareRepository.save(sysDeclare);
        return sysDeclare;
    }

    @Override
    public void deleteDeclare(Long declareId) {
        SysDeclare sysDeclare = declareRepository.findOne(declareId);
        Preconditions.checkArgument(null != sysDeclare, "该条说明不存在");
        declareRepository.delete(sysDeclare);
    }
}
