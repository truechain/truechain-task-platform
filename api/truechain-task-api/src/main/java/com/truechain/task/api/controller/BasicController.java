package com.truechain.task.api.controller;

import com.truechain.task.api.config.AppProperties;
import com.truechain.task.api.model.dto.SessionPOJO;
import com.truechain.task.api.security.SessionPOJOService;
import com.truechain.task.core.BusinessException;
import com.truechain.task.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BasicController {

    private static final Logger logger = LoggerFactory.getLogger(BasicController.class);

    @Autowired
    protected StringRedisTemplate stringRedisTemplate;

    @Autowired
    protected RedisTemplate redisTemplate;

    @Autowired
    private SessionPOJOService sessionPOJOService;

    protected HttpServletRequest request;

    protected HttpServletResponse response;

    @ModelAttribute
    public void initParam(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("请求URI：{}，请求参数：{}", request.getRequestURI(),
                new JSONObject(request.getParameterMap()));
        this.request = request;
        this.response = response;
    }

    /**
     * 获取登录信息
     *
     * @return
     */
    protected SessionPOJO getSessionPoJO() {
        SessionPOJO sessionPOJO = null;
        String token = request.getHeader(AppProperties.TOKEN_HEADER);
        String salt = request.getHeader(AppProperties.AGENT_HEADER);
        if (StringUtils.isBlank(token) && StringUtils.isBlank(salt)) {
            return sessionPOJO;
        }
        String redisKey = JwtUtil.getRedisKeyByToken(token, salt);
        if (StringUtils.isBlank(redisKey)) {
            throw new BusinessException("用户尚未登录");
        }
        String userId = stringRedisTemplate.opsForValue().get(redisKey);
        sessionPOJO = sessionPOJOService.getByUserId(userId);
        return sessionPOJO;
    }
}
