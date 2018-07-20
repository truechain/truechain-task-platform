package com.truechain.task.admin.security;

import com.truechain.task.admin.config.AppProperties;
import com.truechain.task.admin.model.dto.SessionPOJO;
import com.truechain.task.core.Wrapper;
import com.truechain.task.model.enums.GlobalStatusEnum;
import com.truechain.task.util.JwtUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class AuthInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Autowired
    private SessionPOJOService sessionPOJOService;

    @Autowired
    private PermissionService permissionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }
        String uri = request.getRequestURI();

        logger.info("--AuthInterceptor，请求URI: {}。", uri);

        if (uri.contains("/unauth/") || uri.contains("/actuator")
                || uri.contains("swagger") || uri.contains("webjars") || uri.contains("/v2") || uri.contains(".css")
                || uri.contains(".ttf") || uri.contains(".js") || uri.contains(".png") || uri.contains(".gif")
                || uri.contains(".ico") || uri.contains(".jpg")) {
            return true;
        }

        //认证
        String token = request.getHeader(AppProperties.TOKEN_HEADER);
        String salt = request.getHeader(AppProperties.AGENT_HEADER);
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(salt) || !JwtUtil.verifyToken(token, salt)) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }
        String sessionId = null;
        try {
            sessionId = JwtUtil.getSessionIdByToken(token, salt);
        } catch (Exception e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }


        SessionPOJO sessionPOJO = sessionPOJOService.getBySessionId(sessionId);
        //鉴权
        if (!permissionService.checkPermission(uri, sessionPOJO)) {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            OutputStream stream = response.getOutputStream();
            JSONObject result = new JSONObject(new Wrapper(GlobalStatusEnum.FAIL.getCode(), "无权限"));
            stream.write(result.toString().getBytes(Charset.defaultCharset()));
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
