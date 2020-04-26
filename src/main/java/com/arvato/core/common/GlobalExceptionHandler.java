package com.arvato.core.common;

import javax.servlet.http.HttpServletRequest;

import com.arvato.core.pojo.ResultInfo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 统一异常处理
 * @author DELL
 *
 */

@ControllerAdvice
//@ControllerAdvice定义统一的异常处理类，而不是在每个Controller中逐个定义。@ExceptionHandler用来定义函数针对的异常类型
public class GlobalExceptionHandler{
	
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultInfo defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
    	ResultInfo result = new ResultInfo();
        result.setSuccess(false);
        result.setCode(500);
        result.setMsg(e.getMessage());
        return result;
    }
}
