package com.truechain.task.admin.service.impl;

import com.google.common.base.Preconditions;
import com.querydsl.core.BooleanBuilder;
import com.truechain.task.admin.repository.SysDeclareRepository;
import com.truechain.task.admin.service.DeclareService;
import com.truechain.task.model.entity.QSysDeclare;
import com.truechain.task.model.entity.SysDeclare;
import org.apache.commons.lang3.StringUtils;
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
        QSysDeclare qSysDeclare = QSysDeclare.sysDeclare;
        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.isNotBlank(declare.getStartDate())) {
            builder.and(qSysDeclare.createTime.gt(declare.getStartDate()));
        }
        if (StringUtils.isNotBlank(declare.getEndDate())) {
            builder.and(qSysDeclare.createTime.lt(declare.getEndDate()));
        }
        if (StringUtils.isNotBlank(declare.getUpdateUser())) {
            builder.and(qSysDeclare.updateUser.like(declare.getUpdateUser() + "%"));
        }
        Page<SysDeclare> declarePage = declareRepository.findAll(builder, pageable);
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
