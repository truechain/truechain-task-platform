package com.truechain.task.admin.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.truechain.task.admin.model.dto.ManageDTO;
import com.truechain.task.admin.model.viewPojo.ManagePojo;

public interface ManageService {
    Page<ManagePojo> getTaskPage(ManageDTO manageDTO, Pageable pageable);
    
    ManagePojo getManageInfo(Long manageId);
    
    ManagePojo addManage(ManagePojo managePojo);
    
    ManagePojo updateManage(ManagePojo managePojo);
    
    void delete(Long id);
}
