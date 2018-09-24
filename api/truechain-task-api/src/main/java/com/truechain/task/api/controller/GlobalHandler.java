package com.truechain.task.api.controller;

import com.truechain.task.core.BusinessException;
import com.truechain.task.core.NullException;
import com.truechain.task.core.WrapMapper;
import com.truechain.task.core.Wrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalHandler.class);


    /**
     * 业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler({BusinessException.class, IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Wrapper businessException(Exception e) {
        logger.error("业务异常{}", e.getMessage(), e);
        return WrapMapper.error(e.getMessage());
    }

    /**
     * 全局异常.
     *
     * @param e the e
     * @return the wrapper
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Wrapper exception(Exception e) {
        logger.info("全局异常信息{}", e.getMessage(), e);
        return WrapMapper.error("系统异常");
    }

    /**
     * 空指针异常
     * @param e
     * @return
     */
    @ExceptionHandler(NullException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Wrapper nullException(Exception e) {
        logger.info("全局异常信息{}", e.getMessage(), e);
        return WrapMapper.errorNull(e.getMessage());
    }
}
