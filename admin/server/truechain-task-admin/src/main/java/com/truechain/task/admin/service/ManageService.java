package com.truechain.task.admin.service;
import com.truechain.task.model.entity.ConfigManage;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface ManageService {
    Page<ConfigManage> getConfigManage( Pageable pageable);
}
