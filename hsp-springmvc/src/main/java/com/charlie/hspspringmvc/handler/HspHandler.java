package com.charlie.hspspringmvc.handler;

import java.lang.reflect.Method;

// HspHandler对象记录请求的url和控制器方法映射关系
public class HspHandler {
    // 请求的url
    private String url;
    // 调用的处理器
    private Object controller;
    // 调用处理器的方法
    private Method method;

    public HspHandler(String url, Object controller, Method method) {
        this.url = url;
        this.controller = controller;
        this.method = method;
    }

    @Override
    public String toString() {
        return "HspHandler{" +
                "url='" + url + '\'' +
                ", controller=" + controller +
                ", method=" + method +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
