package com.truechain.task.admin.service;

import com.truechain.task.admin.model.viewPojo.ResourceTreeVO;
import com.truechain.task.model.entity.AuthResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ResourceService {

    Page<AuthResource> getResourcePageByCriteria(AuthResource resource, Pageable pageable);

    List<AuthResource> getResourceListByCriteria(AuthResource resource);

    List<ResourceTreeVO> getResourceTreeList(AuthResource resource);

    AuthResource getResourceInfoById(Long resourceId);

    void addResource(AuthResource resource);

    void updateResource(AuthResource resource);

    void deleteResource(Long resourceId);
}
