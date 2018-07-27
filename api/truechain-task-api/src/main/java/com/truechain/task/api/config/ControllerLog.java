package com.truechain.task.api.config;

import com.truechain.task.core.Wrapper;
import com.truechain.task.util.JsonUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerLog {

    private static final Logger logger = LoggerFactory.getLogger(ControllerLog.class);

    @Pointcut("execution(* com.truechain.task.api.controller.*.*(..))")
    public void allController() {
    }

    @Around(value = "allController()")
    public Object controllerReturn(ProceedingJoinPoint point) throws Throwable {
        return around(point);
    }

    /**
     * around
     *
     * @param point
     * @return
     * @throws Throwable
     */
    private Object around(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = point.proceed();
        long endTime = System.currentTimeMillis();
        if (null != result && (result instanceof Wrapper)) {
            ((Wrapper) result).setCosts(endTime - startTime);
            logger.info("----请求结束，controller的返回值： " + JsonUtil.toJsonString(result));
        }
        return result;
    }
}
