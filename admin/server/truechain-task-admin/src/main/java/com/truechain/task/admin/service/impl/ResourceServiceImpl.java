package com.truechain.task.admin.service.impl;

import com.google.common.base.Preconditions;
import com.querydsl.core.BooleanBuilder;
import com.truechain.task.admin.model.viewPojo.ResourceTreeVO;
import com.truechain.task.admin.repository.AuthResourceRepository;
import com.truechain.task.admin.service.ResourceService;
import com.truechain.task.model.entity.AuthResource;
import com.truechain.task.model.entity.QAuthResource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
        QAuthResource qAuthResource = QAuthResource.authResource;
        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.isNotEmpty(resource.getName())) {
            builder.and(qAuthResource.name.like(resource.getName() + "%"));
        }
        Page<AuthResource> resourcePage = resourceRepository.findAll(builder, pageable);
        for (AuthResource authResource : resourcePage.getContent()) {
            if (authResource.getParentId() != null && authResource.getParentId() != 0) {
                AuthResource parentResource = resourceRepository.findOne(authResource.getParentId());
                if (parentResource != null) {
                    authResource.setParentName(parentResource.getName());
                }
            }
        }
        return resourcePage;
    }

    @Override
    public List<AuthResource> getResourceListByCriteria(AuthResource resource) {
        QAuthResource qAuthResource = QAuthResource.authResource;
        BooleanBuilder builder = new BooleanBuilder();
        if (resource.getStatus() != null) {
            builder.and(qAuthResource.status.eq(resource.getStatus()));
        }
        Iterable<AuthResource> resourceIterable = resourceRepository.findAll(builder);
        List<AuthResource> resourceList = new ArrayList<>();
        if (resourceIterable != null) {
            resourceIterable.forEach(x -> resourceList.add(x));
        }
        return resourceList;
    }

    @Override
    public List<ResourceTreeVO> getResourceTreeList(AuthResource resource) {
        AuthResource authResource = null;
        Long parentId = null;
        if (resource.getId() != null) {
            authResource = resourceRepository.findOne(resource.getId());
            Preconditions.checkArgument(authResource != null, "权限不存在");
            parentId = authResource.getParentId();
        }
        QAuthResource qAuthResource = QAuthResource.authResource;
        BooleanBuilder builder = new BooleanBuilder();
        if (resource.getStatus() != null) {
            builder.and(qAuthResource.status.eq(resource.getStatus()));
        }
        Iterable<AuthResource> resourceIterable = resourceRepository.findAll(builder);
        List<ResourceTreeVO> resourceTreeVOList = new ArrayList<>();
        if (resourceIterable != null) {
            Iterator<AuthResource> iterator = resourceIterable.iterator();
            while (iterator.hasNext()) {
                authResource = iterator.next();
                ResourceTreeVO resourceTreeVO = new ResourceTreeVO();
                resourceTreeVO.setId(authResource.getId());
                resourceTreeVO.setCode(authResource.getCode());
                resourceTreeVO.setName(authResource.getName());
                resourceTreeVO.setParentId(authResource.getParentId());
                resourceTreeVO.setUri(authResource.getUri());
                if (parentId != null && authResource.getId().equals(parentId)) {
                    resourceTreeVO.setChecked(true);
                }
                resourceTreeVOList.add(resourceTreeVO);
            }
        }
        return resourceTreeVOList;
    }

    @Override
    public AuthResource getResourceInfoById(Long resourceId) {
        AuthResource authResource = resourceRepository.findOne(resourceId);
        Preconditions.checkArgument(authResource != null, "资源不存在");
        if (authResource.getParentId() != 0) {
            AuthResource parentResource = resourceRepository.findOne(authResource.getParentId());
            if (parentResource != null) {
                authResource.setParentName(parentResource.getName());
            }
        }
        return authResource;
    }

    @Override
    public void addResource(AuthResource resource) {
        resource.setStatus((short) 1);
        resource.setType((short) 1);
        if (resource.getParentId() == null) {
            resource.setParentId(0L);
        }
        resourceRepository.save(resource);
    }

    @Override
    public void updateResource(AuthResource resource) {
        AuthResource authResource = resourceRepository.findOne(resource.getId());
        authResource.setName(resource.getName());
        authResource.setCode(resource.getCode());
        authResource.setParentId(resource.getParentId());
        authResource.setRemark(resource.getRemark());
        resourceRepository.save(authResource);
    }

    @Override
    public void deleteResource(Long resourceId) {
        AuthResource authResource = resourceRepository.findOne(resourceId);
        Preconditions.checkArgument(authResource != null, "资源不存在");
        Preconditions.checkArgument(CollectionUtils.isEmpty(authResource.getRoles()), "已有角色绑定此资源");
        resourceRepository.delete(authResource);
    }
}
