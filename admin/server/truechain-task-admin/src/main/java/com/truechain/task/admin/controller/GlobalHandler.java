package com.truechain.task.admin.controller;

import com.truechain.task.core.BusinessException;
import com.truechain.task.core.WrapMapper;
import com.truechain.task.core.Wrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
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
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Wrapper businessException(BusinessException e) {
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
        if(e instanceof MissingServletRequestParameterException ){
        	//请求参数异常
        	MissingServletRequestParameterException me =  (MissingServletRequestParameterException)e;
        	String msg = "请求参数:"+me.getParameterName()+"不存在";
        	return WrapMapper.error(msg);
        }
        return WrapMapper.error(e.getMessage());
    }
}
