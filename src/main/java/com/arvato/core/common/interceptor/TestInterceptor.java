package com.arvato.core.common.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 拦截器示例
 * @author DELL
 *
 */
@Component
public class TestInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
		System.out.println("处理拦截之前");
		request.setAttribute("startTime", new Date().getTime());
		System.out.println(((HandlerMethod) o).getBean().getClass().getName());
		System.out.println(((HandlerMethod) o).getMethod().getName());
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
	    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
	    httpServletResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));
	    httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
	    httpServletResponse.setHeader("P3P", "CP=CAO PSA OUR");
	    httpServletResponse.addHeader("Access-Control-Allow-Methods", "POST,GET,TRACE,OPTIONS");
	    httpServletResponse.addHeader("Access-Control-Allow-Headers", "Content-Type,Origin,Accept");
	    httpServletResponse.addHeader("Access-Control-Max-Age", "120");
	    httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
	    httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
	    httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
	    // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
	    if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
	       httpServletResponse.setStatus(HttpStatus.OK.value());
	       return false;
	    }
       return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("开始处理拦截");
        Long start = (Long) request.getAttribute("startTime");
        System.out.println("【拦截器】耗时 " + (new Date().getTime() - start));
        response.sendRedirect(request.getContextPath()+"/admin/login");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
        System.out.println("处理拦截之后");
        Long start = (Long) request.getAttribute("startTime");
        System.out.println("【拦截器】耗时 " + (new Date().getTime() - start));
       // System.out.println("异常信息 " + e.getMessage());
    }
}