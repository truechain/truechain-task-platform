package com.truechain.task.api.security;

import com.truechain.task.api.model.dto.SessionPOJO;
import com.truechain.task.api.repository.RedisRepository;
import com.truechain.task.model.entity.SysUser;
import com.truechain.task.util.CommonUtil;
import com.truechain.task.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class SessionPOJOService {

    private static final Logger logger = LoggerFactory.getLogger(SessionPOJOService.class);

    @Autowired
    private RedisRepository<SessionPOJO> sessionPOJORedisRepository;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 初始化登录Session
     *
     * @param user
     * @return
     */
    public SessionPOJO initSession(SysUser user) {
        String userId = String.valueOf(user.getId());
        SessionPOJO sessionPOJO = new SessionPOJO();
        String sessionId = CommonUtil.getRandomString(6);
        sessionPOJO.setId(sessionId);
        sessionPOJO.setUserId(user.getId());
        String redisKey = CommonUtil.getRandomString(6);
        String salt = CommonUtil.getRandomString(6);
        String token = JwtUtil.createToken(salt, redisKey, 31);
        sessionPOJO.setSalt(salt);
        sessionPOJO.setToken(token);
        SessionPOJO oldSp = sessionPOJORedisRepository.get(userId);
        if (null != oldSp) {
            logger.debug("开始删除");
            try {
                String oldRedisKey = JwtUtil.getRedisKeyByToken(oldSp.getToken(), oldSp.getSalt());
                // 删除旧数据
                stringRedisTemplate.delete(oldRedisKey);
                stringRedisTemplate.delete(String.valueOf(oldSp.getUserId()));
            } catch (Exception e) {
                logger.error("initSessionPojo删除异常", e);
            }
            logger.debug("结束删除");
        }
        stringRedisTemplate.opsForValue().set(redisKey, userId, 30, TimeUnit.DAYS);
        sessionPOJORedisRepository.set(userId, sessionPOJO, 30, TimeUnit.DAYS);
        return sessionPOJO;
    }

    /**
     * 获取sessionPoJo
     *
     * @param userId
     * @return
     */
    public SessionPOJO getByUserId(String userId) {
        SessionPOJO sessionPOJO = sessionPOJORedisRepository.get(userId);
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
