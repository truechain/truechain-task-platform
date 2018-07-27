package com.truechain.task.api.security;

import com.truechain.task.api.config.AppProperties;
import com.truechain.task.core.WrapMapper;
import com.truechain.task.core.Wrapper;
import com.truechain.task.util.JwtUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    private StringRedisTemplate stringRedisTemplate;

    public AuthInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

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
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(salt)) {
            forbidden(response);
            return false;
        }
        String redisKey = null;
        try {
            redisKey = JwtUtil.getRedisKeyByToken(token, salt);
        } catch (Exception e) {
            forbidden(response);
            return false;
        }

        String sessionId = stringRedisTemplate.opsForValue().get(redisKey);
        if (null == sessionId) {
            logger.info("--用户验证失败：前端token与后端redis不一致，验证失败URI: {}，验证失败Token: {}，验证失败Salt: {}，--返回登录页面。", uri, token,
                    salt);
            forbidden(response);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    /**
     * forbidden
     *
     * @param response
     */
    private void forbidden(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=utf-8");
        Wrapper wrapper = WrapMapper.wrap(HttpStatus.FORBIDDEN.value(), "拒绝访问");
        PrintWriter out = response.getWriter();
        out.write(new JSONObject(wrapper).toString());
    }
}
