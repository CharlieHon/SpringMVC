package com.charlie.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 拦截器需要注入到容器中，并且需要在spring配置文件中进行配置
@Component
public class MyInterceptor01 implements HandlerInterceptor {
    /**
     * 1. preHandler在目标方法执行前被执行
     * 2. 如果preHandler()返回false，则不再执行目标方法
     * 3. 该方法可以获取到 request, response, handler
     * 4. 这里根据业务，可以进行拦截，并指定跳转到哪个页面
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("--MyInterceptor01--preHandle()...");
        // 获取到用户提交的关键字
        //String topic = request.getParameter("topic");
        //if ("病毒".equals(topic)) {
        //    request.getRequestDispatcher("/WEB-INF/pages/warning.jsp").forward(request, response);
        //    return false;
        //}
        //System.out.println("得到的topic=" + topic);
        return true;
    }

    /**
     * 1. 在目标方法执行后，执行postHandle()方法
     * 2. 该方法可以获取到目标方法，返回的ModelAndView
     * 3.
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("--MyInterceptor01--postHandle()...");
    }

    /**
     * 1. 该方法afterCompletion()在视图渲染后被执行，这里可以进行资源请理工作
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("--MyInterceptor01--afterCompletion()...");
    }
}
