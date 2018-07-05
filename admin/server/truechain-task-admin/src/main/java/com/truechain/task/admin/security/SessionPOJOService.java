package com.truechain.task.admin.security;

import com.truechain.task.model.entity.AuthUser;
import com.truechain.task.admin.repository.RedisRepository;
import com.truechain.task.model.dto.SessionPOJO;
import com.truechain.task.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionPOJOService {

    @Autowired
    private RedisRepository<SessionPOJO> sessionPOJORedisRepository;

    /**
     * 初始化登录Session
     *
     * @param user
     * @return
     */
    public SessionPOJO initSession(AuthUser user) {
        SessionPOJO sessionPOJO = new SessionPOJO();
        String sessionId = CommonUtil.getRandomString(6);
        sessionPOJORedisRepository.set(sessionId, sessionPOJO);
        return sessionPOJO;
    }

    /**
     * 获取sessionPoJo
     *
     * @param sessionId
     * @return
     */
    public SessionPOJO getBySessionId(String sessionId) {
        SessionPOJO sessionPOJO = sessionPOJORedisRepository.get(sessionId);
        return sessionPOJO;
    }

    /**
     * 删除SessionPoJo
     *
     * @param sessionId
     */
    public void deleteBySessionId(String sessionId) {
        sessionPOJORedisRepository.delete(sessionId);
    }
}
