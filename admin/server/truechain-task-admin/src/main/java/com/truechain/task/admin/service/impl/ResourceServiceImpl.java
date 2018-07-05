package com.truechain.task.admin.service.impl;

import com.truechain.task.model.entity.AuthResource;
import com.truechain.task.admin.repository.AuthResourceRepository;
import com.truechain.task.admin.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * 资源service
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    private static final long serialVersionUID = 1L;

    @Autowired
    private AuthResourceRepository resourceRepository;

    @Override
    public Page<AuthResource> getResourcePageByCriteria(AuthResource resource, Pageable pageable) {
        Page<AuthResource> resourcePage = resourceRepository.findAll(pageable);
        return resourcePage;
    }

    @Override
    public void addResource(AuthResource resource) {
        resourceRepository.save(resource);
    }

    @Override
    public void updateResource(AuthResource resource) {
        resourceRepository.save(resource);
    }

    @Override
    public void deleteResource(Integer resourceId) {
        resourceRepository.delete(resourceId);
    }
}
