package com.charlie.web.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * 如果在类上标注了 @ControllerAdvice注解，则该类是一个全局异常类
 */
@ControllerAdvice
public class MyGlobalException {
    /**
     * 1. 全局异常就是不管哪个Handler抛出异常，都可以捕获
     * 2. 这里处理的全局异常有 数据格式异常和类转换异常
     * 3. 局部异常处理不了的异常，才会来到全局异常进行处理，如果全局异常仍没有匹配异常，就会显示tomcat默认异常
     * 4. 自定义异常也可以交给全局异常进行处理
     */
    @ExceptionHandler({NumberFormatException.class, ClassCastException.class, AgeException.class})
    public String globalException(Exception ex, HttpServletRequest req) {
        System.out.println("全局异常信息：" + ex.getMessage());
        // 将异常信息带到下一个页面
        req.setAttribute("reason", ex.getMessage());
        return "exception_mes";
    }
}
