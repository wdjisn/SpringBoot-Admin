package com.example.spba.config;

import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SaTokenConfigure implements WebMvcConfigurer
{

    /**
     * 注册Sa-Token的拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        // 注册路由拦截器，自定义认证规则
        registry.addInterceptor(new SaRouteInterceptor((req, res, handler) ->
        {
            // 登录认证 -- 拦截所有路由，并排除/login 用于开放登录
            SaRouter.match("/**", "/login", r -> StpUtil.checkLogin());

            // 权限认证：匹配restful风格路由、多个条件一起使用
            SaRouter.match(SaHttpMethod.GET).match("/admins").check(r ->StpUtil.checkPermission("admin:list"));
            SaRouter.match(SaHttpMethod.POST).match("/admin").check(r ->StpUtil.checkPermission("admin:add"));
            SaRouter.match(SaHttpMethod.PUT).match("/admin").check(r ->StpUtil.checkPermission("admin:edit"));
            SaRouter.match(SaHttpMethod.GET).match("/admin/{id}").check(r ->StpUtil.checkPermission("admin:query"));
            SaRouter.match(SaHttpMethod.DELETE).match("/admin/{id}").check(r ->StpUtil.checkPermission("admin:del"));

            SaRouter.match(SaHttpMethod.GET).match("/roles").check(r ->StpUtil.checkPermission("role:list"));
            SaRouter.match(SaHttpMethod.POST).match("/role").check(r ->StpUtil.checkPermission("role:add"));
            SaRouter.match(SaHttpMethod.PUT).match("/role").check(r ->StpUtil.checkPermission("role:edit"));
            SaRouter.match(SaHttpMethod.GET).match("/role/{id}").check(r ->StpUtil.checkPermission("role:query"));
            SaRouter.match(SaHttpMethod.DELETE).match("/role/{id}").check(r ->StpUtil.checkPermission("role:del"));

            SaRouter.match(SaHttpMethod.GET).match("/menus").check(r ->StpUtil.checkPermission("menu:list"));
            SaRouter.match(SaHttpMethod.POST).match("/menu").check(r ->StpUtil.checkPermission("menu:add"));
            SaRouter.match(SaHttpMethod.PUT).match("/menu").check(r ->StpUtil.checkPermission("menu:edit"));
            SaRouter.match(SaHttpMethod.GET).match("/menu/{id}").check(r ->StpUtil.checkPermission("menu:query"));
            SaRouter.match(SaHttpMethod.DELETE).match("/menu/{id}").check(r ->StpUtil.checkPermission("menu:del"));
        })).addPathPatterns("/**");
    }
}
