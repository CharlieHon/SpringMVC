# SpringMVC

## 基本介绍

1. `SpringMVC`是WEB层架构，接管了Web层组件，比如控制器、视图、视图解析，返回给用户数据格式，同时支持MVC的开发模式/架构
2. `SpringMVC`通过注解，让POJO成为控制器，不需要继承类或者实现接口
3. SpringMVC采用低耦合的组件设计方式，具有更好扩展和灵活性
4. 支持 `REST` 格式的URL请求
5. SpringMVC是基于Spring的，也就是SpringMVC是在Spring基础上的。SpringMVC的核心包 `spring-webmvc-xx.jar` 和 `spring-web-xx.jar`
6. ![Spring和SpringMVC和SpringBoot的关系](img.png)

## 快速入门

1. ![SpringMVC登录流程分析](img_1.png)
2. 创建 `springmvc` web工程并配置 tomcat，导入SpringMVC开发需要jar包
   - ![img_2.png](img_2.png)
3. 创建 `src/applicationContext-mvc.xml` 文件，即Spring的容器文件。配置**要扫描的包**，以及**视图解析器**
4. 配置 `WEB-INF/web.xml` ，配置**分发处理器**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">
    <!--Spring配置文件-->

    <!--配置自动扫描的包-->
    <context:component-scan base-package="com.charlie.web"/>

    <!--配置视图解析器，因为容器中只会有一个视图解析器，所以可以不用配置id，根据类型注入-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <!--配置属性suffix和prefix-->
        <property name="prefix" value="/WEB-INF/pages/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
</beans>
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    <!--web配置文件-->

    <!--配置前端控制器/中央控制器/分发控制器
    1. 用户的请求都会经过它的处理
    -->
    <servlet>
        <servlet-name>springDispatcherServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!--配置属性 contextConfigLocation，指定 DispatcherServlet 操作的spring配置文件
        1. 如果没有配置 contextConfigLocation属性
        2. 默认按照这样的位置去定位spring配置文件：WEB-INF/springDispatcherServlet-servlet.xml
        -->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:applicationContext_mvc.xml</param-value>
        </init-param>
        <!--在web项目启动时，就自动加载 DispatcherServlet 对象-->
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>springDispatcherServlet</servlet-name>
        <!--说明
        1) 这里配置的url-pattern是 / ，表示用户的请求都经过 DispatcherServlet
        2) 这样配置也支持 rest 风格的url请求
        -->
        <url-pattern>/</url-pattern>
    </servlet-mapping>

</web-app>
```

```java
package com.charlie.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
1. 当使用了SpringMVC框架，在一个类上标识 @Controller
2. 标识将该类视为一个控制器，注入到容器
3. 比原生servlet开发要简化很多
 */
@Controller
public class UserServlet {

    /* 编写方法，响应用户请求
    1. login() 方法是用于响应用户的登录请i去
    2. @RequestMapping(value = "/login") 类似于以前在原生Servlet中配置的 url-pattern，参数名value可以省略
    3. 即当用户在浏览器输入 http://localhost:8080/web工程路径/login 就能够访问到 login()
    4. return "login_ok"; 表示返回结果给视图解析器(InternalResourceViewResolver)
        ，视图解析器根据配置，来决定跳转到哪个页面
        <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
            <property name="prefix" value="/WEB-INF/pages/"/>
            <property name="suffix" value=".jsp"/>
        </bean>
        根据上面的配置 return "login_ok";  就是转发到 /WEB-INF/pages/login_ok.jsp
     */
    @RequestMapping(value = "/login")
    public String login() {
        System.out.println("login ok...");
        return "login_ok";
    }
}
```

```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>
<h3>登录页面</h3>
<%--
1. javaweb web工程路径，action="login" 表示的url是 http://localhost:8080/springmvc/
2. action="/login" 表示的url是 http://localhost:8080/login
--%>
<form action="login">
    u:<input type="text" name="username"><br/>
    p:<input type="password" name="password"><br/>
    <input type="submit" value="登录">
</form>
</body>
</html>
```

## 执行流程

- ![SpringMVC执行流程](img_3.png)

## @RequestMapping

1. 

