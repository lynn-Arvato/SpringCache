package com.arvato.core.component;

import com.arvato.core.exception.CustomException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({CustomException.class})
    public String handleException(Exception e, HttpServletRequest request){
        Map<String, Object> map = new HashMap<>(16);
        map.put("code", "404");
        map.put("message", e.getMessage());
        request.setAttribute("javax.servlet.error.status_code",404);
        request.setAttribute("extend",map);
        return "forward:/error";//BasicErrorController的接口
    }

}
