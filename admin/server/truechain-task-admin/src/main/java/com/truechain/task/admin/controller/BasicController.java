package com.truechain.task.admin.controller;

import com.truechain.task.admin.config.AppProperties;
import com.truechain.task.admin.model.dto.SessionPOJO;
import com.truechain.task.admin.security.PermissionService;
import com.truechain.task.admin.security.SessionPOJOService;
import com.truechain.task.util.JwtUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BasicController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected StringRedisTemplate stringRedisTemplate;

    @Autowired
    private PermissionService permissionService;

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

        //初始化权限
        permissionService.initPermission();
    }

    /**
     * 获取登录信息
     *
     * @return
     */
    protected SessionPOJO getSessionPoJO(HttpServletRequest request) {
        String token = request.getHeader(AppProperties.TOKEN_HEADER);
        String salt = request.getHeader(AppProperties.AGENT_HEADER);
        String sessionId = JwtUtil.getRedisKeyByToken(token, salt);
        SessionPOJO sessionPOJO = sessionPOJOService.getBySessionId(sessionId);
        return sessionPOJO;
    }
}
