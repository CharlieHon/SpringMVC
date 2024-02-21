package com.charlie.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MyInterceptor02 implements HandlerInterceptor {
    /**
     * 1. pre方法再目标方法执行之前被调用
     * 2. 返回false，则不会再执行目标方法，可以再次响应请求返回页面
     * 3. 不管返回false，还是true，都会执行此拦截器之前的拦截器的afterCompletion方法。
     *      注意：不会执行当前拦截器的afterCompletion方法
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("--MyInterceptor02--preHandler()...");
        return false;
    }

    // 在目标方法被执行之后执行，可以在该方法中访问到目标方法返回的ModelAndView对象
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("--MyInterceptor02--postHandler()...");
    }

    /**
     * 1。若preHandle返回true，则方法在渲染视图之后被执行
     * 2. 若preHandle返回false，则该方法不会被调用
     * 3. 若当前拦截器的下一个拦截器的preHandle方法返回false，则在执行下一个拦截器preHandle方法后马上被执行
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("--MyInterceptor02--afterCompletion()...");
    }
}
