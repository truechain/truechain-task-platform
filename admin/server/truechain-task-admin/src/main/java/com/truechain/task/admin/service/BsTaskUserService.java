package com.truechain.task.admin.service;

import com.truechain.task.admin.model.dto.AuditBsTaskUserDTO;
import com.truechain.task.admin.model.dto.TaskDTO;
import com.truechain.task.model.entity.BsTask;
import com.truechain.task.model.entity.BsTaskUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

public interface BsTaskUserService {

    List<BsTaskUser> getBsTaskUserByUserIds(Collection<Long> userIds);

    Page<BsTaskUser> getBsTaskUser(TaskDTO task, Pageable pageable);

    long getBsTaskUserCount(TaskDTO task);
    
    void cancelBsTaskUser(Long id);
    

	void auditBsTaskUser(AuditBsTaskUserDTO auditBsTaskUserDTO);
	AuditBsTaskUserDTO getDefaultReward(Long id) ;
}
