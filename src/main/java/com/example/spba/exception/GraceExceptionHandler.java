package com.example.spba.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.hutool.json.JSONUtil;
import com.example.spba.service.ErrorLogService;
import com.example.spba.utils.R;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

/**
 * RestControllerAdvice = ControllerAdvice + ResponseBody
 * 是controller的增强器,可以全局捕获spring mvc抛的异常
 */
@RestControllerAdvice
/**
 * 优雅异常处理程序
 * 统一拦截处理
 * 在该类中，可以定义多个方法，不同的方法处理不同的异常
 */
public class GraceExceptionHandler
{

    @Autowired
    private ErrorLogService errorLogService;


    /*************************SpringBoot常见异常*************************/


    /**
     * 处理Validated验证异常
     * @param e
     * @return
     */
    @ExceptionHandler({BindException.class})
    public R bindExceptionHandler(BindException e) {
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        return R.error(objectError.getDefaultMessage());
    }

    /**
     * 处理Validated验证异常
     * @param e
     * @return
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public R constraintViolationExceptionHandler(ConstraintViolationException e) {
        return R.error(e.getMessage().split(",")[0].split(":")[1]);
    }

    /**
     * 处理单个文件超大异常
     * @ExceptionHandler 注解用来指明异常的处理类型
     * @param e
     * @return
     */
    @ExceptionHandler(FileSizeLimitExceededException.class)
    public R fileSizeLimitExceededExceptionHandler(FileSizeLimitExceededException e) {
        return R.error("单个文件大小不允许超过2MB");
    }

    /**
     * 处理请求数据超大异常
     * @ExceptionHandler
     * @param e
     * @return
     */
    @ExceptionHandler(SizeLimitExceededException.class)
    public R sizeLimitExceededExceptionHandler(SizeLimitExceededException e) {
        return R.error("请求数据大小不允许超过10MB");
    }

    /**
     * 处理其它异常：统一捕获，写入异常日志
     * 例1：HttpRequestMethodNotSupportedException 路由请求方式异常
     * 例2：NullPointerException 空指针异常
     * 例3：BadSqlGrammarException sql异常
     * ......
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler({Exception.class})
    public R exceptionHandler(Exception e, HttpServletRequest request)
    {
        errorLogService.save(request.getRequestURI(), request.getMethod(),
                             JSONUtil.parse(request.getParameterMap()).toBean(String.class),
                             e.getMessage(), e.getClass().toString());
        return R.error();
    }



    /*************************Sa-Token常见异常*************************/



    /**
     * 处理sa-token未登录异常
     * @param e
     * @return
     */
    @ExceptionHandler({NotLoginException.class})
    public R notLoginExceptionHandler(NotLoginException e) {
        System.out.println(e.toString());
        System.out.println(e.getType());
        // -3 Token已过期：55c23394-d97d-4157-9db2-a6410633b05e
        // -2 Token无效：4eaec3ca-db5d-4d94-b911-42afd247db57
        return R.error(501, "请登录");
    }

    /**
     * 处理sa-token checkPermission异常
     * @param e
     * @return
     */
    @ExceptionHandler({NotPermissionException.class})
    public R notPermissionExceptionHandler(NotPermissionException e) {
        return R.error("暂无权限");
    }

    /**
     * 处理sa-token checkRole异常
     * @param e
     * @return
     */
    @ExceptionHandler({NotRoleException.class})
    public R notRoleExceptionHandler(NotRoleException e) {
        return R.error("暂无权限");
    }
}
