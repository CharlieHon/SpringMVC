package com.charlie.hspspringmvc.servlet;

import com.charlie.hspspringmvc.annotation.Controller;
import com.charlie.hspspringmvc.annotation.RequestMapping;
import com.charlie.hspspringmvc.annotation.RequestParam;
import com.charlie.hspspringmvc.context.HspWebApplicationContext;
import com.charlie.hspspringmvc.handler.HspHandler;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 1. HspDispatcherServlet充当了原生DispatcherServlet
 * 2. 本质是一个Servlet，所以需要继承HttpServlet
 * 3. 这里需要使用到JavaWeb中学习的servlet
 */
public class HspDispatcherServlet extends HttpServlet {

    // 定义属性,handlerList,保存HspHandler(url和控制器方法的映射关系)
    private List<HspHandler> handlerList = new ArrayList<>();
    // 定义属性,自己的spring容器
    private HspWebApplicationContext hspWebApplicationContext = null;

    // 编写方法,完成url和控制器方法的映射
    private void initHandlerMapping() {
        if (hspWebApplicationContext.ioc.isEmpty()) {
            // 判断当前ioc容器是否为空,
            return;
        }
        // 遍历ioc容器的bean对象,然后进行url映射处理
        for (Map.Entry<String, Object> entry : hspWebApplicationContext.ioc.entrySet()) {
            // 先取出注入的Object的Class对象,然后才能拿到其方法及方法的注解
            Class<?> clazz = entry.getValue().getClass();
            // 如果注入的Bean对象是Controller
            if (clazz.isAnnotationPresent(Controller.class)) {
                // 取出它所有方法
                Method[] declaredMethods = clazz.getDeclaredMethods();
                // 遍历方法
                for (Method declaredMethod : declaredMethods) {
                    // 判断该方法是否有 @RequestMapping
                    if (declaredMethod.isAnnotationPresent(RequestMapping.class)) {
                        // 取出@RequestMapping值,即映射路径url
                        RequestMapping requestMappingAnnotation = declaredMethod.getAnnotation(RequestMapping.class);
                        // 这里可以把工程路径+url => req,getServletContext().getContextPath()
                        String url = requestMappingAnnotation.value();
                        // 创建HspHandler对象,就是一个映射关系
                        HspHandler hspHandler = new HspHandler(url, entry.getValue(), declaredMethod);
                        handlerList.add(hspHandler);
                    }
                }
            }
        }
    }

    // 编写方法，通过request对象，返回HspHandler对象，如果没有则返回null
    private HspHandler getHspHandler(HttpServletRequest req) {
        // 1. 获取用户请求的uri，比如 http://localhost:8080/springmvc/monster/list
        //  => uri: /springmvc/monster/list
        // 2. 这里需要注意，得到的uri和保存的url有一个工程路径的问题
        // 3. 有两个解决方案：1) 将tomcat直接配置 application context => /
        //                  2) 在保存hspHandler的url时，把工程路径拼接上 req.getServletContext().getContextPath()
        String uri = req.getRequestURI();
        for (HspHandler hspHandler : handlerList) {
            if (uri.equals(hspHandler.getUrl())) {  // 说明匹配成功
                return hspHandler;
            }
        }
        // 没有对应请求uri的HspHandler
        return null;
    }

    // 编写方法，完成分发请求任务
    private void executeDispatch(HttpServletRequest req, HttpServletResponse resp) {
        HspHandler hspHandler = getHspHandler(req);
        try {
            if (hspHandler == null) {   // 说明用户请求的资源/路径不存在
                resp.getWriter().print("<h1>404 NOT FOUND</h1>");
            } else {    // 匹配成功，反射调用控制器的房方法
                // 目标：将 HttpServletRequest 和 HttpServletResponse 封装到参数里面
                // 1. 得到目标方法的参数信息对应的数组
                Class<?>[] parameterTypes = hspHandler.getMethod().getParameterTypes();
                // 2. 创建一个参数数组，对应实参数组，在后面反射调用目标方法时会使用到
                Object[] params = new Object[parameterTypes.length];
                // 3. 遍历parameterTypes(形参数组)，根据形参数组信息，将实参填充到实参数组(params)中，必须要按顺序添
                for (int i = 0; i < params.length; ++i) {
                    // 取出每一个形参类型
                    Class<?> parameterType = parameterTypes[i];
                    // 如果形参是HttpServletRequest，将req填充到实参数组
                    if ("HttpServletRequest".equals(parameterType.getSimpleName())) {
                        params[i] = req;
                    } else if ("HttpServletResponse".equals(parameterType.getSimpleName())) {
                        params[i] = resp;
                    }
                }

                // 将http请求参数封装到params数组中，注意填充实参的时候的顺序问题
                // 1. 获取http请求的参数集合
                // 2. 返回的Map<String, String[]> ：String表示http请求的参数名；
                //  String[]表示http请求的参数值，因为同一个参数可以有多个值：http://localhost:8080/monster/find?name=charlie&hobby=book&hobby=swimming
                Map<String, String[]> parameterMap = req.getParameterMap();
                // 2. 遍历parameterMap将请求参数，按照顺序填充到实参数组
                for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                    // 这个name就是对应的参数名
                    String name = entry.getKey();
                    // 这里做了简化，只考虑提交的参数是单值的情况，即不考虑累计checkbox提交的数据
                    String value = entry.getValue()[0]; // 对应的实参值

                    // 得到请求的参数对应目标方法的第几个形参，然后将其填充进去 => 通过 getIndexRequestParameterIndex 方法实现返回对应索引位置
                    int indexRequestParameterIndex = getIndexRequestParameterIndex(hspHandler.getMethod(), name);
                    if (-1 != indexRequestParameterIndex) { // 找到了对应的位置
                        params[indexRequestParameterIndex] = value;
                    } else { // 说明没有找到 @RequestParameter 注解，就会使用默认的机制进行配置
                        // 1. 得到目标方法的所有形参的名称-专门编写一个方法，获取形参名称
                        // 2. 对得到的目标方法的所有形参名进行遍历，如果匹配就把当前的请求的参数值，填充到params

                    }
                }

                /* 目标方法，如：public void listMonsters(HttpServletRequest req, HttpServletResponse resp)
                1. 下面这样这些，是针对目标方法是 m(HttpServletRequest req, HttpServletResponse resp);
                2. 这里将需要传递给目标方法的实参 => 封装到参数数组 => 然后以反射调用的方式传递给目标方法
                3. public Object invoke(Object obj, Object... args)
                 */
                //hspHandler.getMethod().invoke(hspHandler.getController(), req, resp);

                // 传入实参数组
                hspHandler.getMethod().invoke(hspHandler.getController(), params);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 编写方法，返回请求参数是目标方法的第几个形参
     * @param method：目标方法
     * @param name：请求的参数名
     * @return 目标方法的第几个形参
     */
    public int getIndexRequestParameterIndex(Method method, String name) {
        // 1. 得到method的所有形参
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; ++i) {
            // 取出当前的形参参数
            Parameter parameter = parameters[i];
            if (parameter.isAnnotationPresent(RequestParam.class)) {    // 该参数有 @RequestParameter 注解
                // 取出当前这个参数的 @RequestParameter(value = "xx") 的value值
                String value = parameter.getAnnotation(RequestParam.class).value();
                if (value.equals(name)) {
                    // 找到请求的参数，对应的目标方法的形参位置
                    return i;
                }
            }
        }
        // 如果没有匹配成功，就返回-1
        return -1;
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

        /* 获取web.xml中的配置信息
            <!--给HspDispatcherServlet配置参数，指定要操作的spring容器文件-->
            <init-param>
              <param-name>contextConfigLocation</param-name>
              <param-value>classpath:hspspringmvc.xml</param-value>
            </init-param>
         */
        // classpath:hspspringmvc.xml
        String configLocation = servletConfig.getInitParameter("contextConfigLocation");
        // 创建自己的spring容器
        hspWebApplicationContext = new HspWebApplicationContext(configLocation);
        hspWebApplicationContext.init();
        // 调用 initHandlerMapping,完成url和控制器方法的映射
        initHandlerMapping();
        // 输出handlerList
        System.out.println("handlerList初始化结果=" + handlerList);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // System.out.println("--HspDispatcherServlet--doGet()..");
        // 调用方法，完成请求转发
        executeDispatch(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //System.out.println("--HspDispatcherServlet--doPost()..");
        doGet(req, resp);
    }
}
