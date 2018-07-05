package com.truechain.task.admin.security;

import com.truechain.task.admin.repository.RedisRepository;
import com.truechain.task.model.dto.SessionPOJO;
import com.truechain.task.model.entity.AuthResource;
import com.truechain.task.model.entity.AuthRole;
import com.truechain.task.admin.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
public class PermissionService {

    private static final String PERMISSION_REDIS_KEY = "permission_";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisRepository<AuthRole> roleRedisRepository;

    @Autowired
    private RoleService roleService;

    /**
     * 初始化权限数据
     */
    public void initPermission() {
        if (!stringRedisTemplate.hasKey(PERMISSION_REDIS_KEY)) {
            AuthRole authRole = new AuthRole();
            List<AuthRole> roleList = roleService.getListRoleByCriteria(authRole);
            if(!CollectionUtils.isEmpty(roleList)){
                for (AuthRole role : roleList) {
                    roleRedisRepository.put(PERMISSION_REDIS_KEY, role.getId().toString(), role);
                }
            }
        }
    }


    /**
     * 鉴权
     *
     * @param uri
     * @param sessionPOJO
     * @return
     */
    public boolean checkPermission(String uri, SessionPOJO sessionPOJO) {
        if (CollectionUtils.isEmpty(sessionPOJO.getRoleList())) {
            return false;
        }
        for (AuthRole authRole : sessionPOJO.getRoleList()) {
            AuthRole role = roleRedisRepository.get(PERMISSION_REDIS_KEY, authRole.getId().toString());
            if (!CollectionUtils.isEmpty(role.getResources())) {
                for (AuthResource resource : role.getResources()) {
                    if (resource.getUri().contains(uri)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
