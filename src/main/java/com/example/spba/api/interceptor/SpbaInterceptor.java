package com.example.spba.api.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import com.example.spba.api.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SpbaInterceptor implements HandlerInterceptor
{

    @Autowired
    private OperateLogService operateLogService;

    /**
     * 拦截请求，访问Controller之前使用的方法
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // System.out.println("拦截请求，访问Controller之前使用的方法");
        return true;
    }

    /**
     * 访问到Controller之后，渲染视图之前
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // System.out.println("访问到Controller之后，渲染视图之前");
        if (!request.getMethod().equals("GET")) {
            operateLogService.save(StpUtil.getLoginIdAsLong(), request.getRequestURI(), request.getMethod(),
                                   JSONUtil.parse(request.getParameterMap()).toBean(String.class),
                                   ServletUtil.getClientIP(request));
        }
    }

    /**
     * 访问到Controller之后，渲染视图之后
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // System.out.println("访问到Controller之后，渲染视图之后");
    }
}