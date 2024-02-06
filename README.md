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
            <param-value>classpath:WEB-INF/applicationContext_mvc.xml</param-value>
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

- `@RequestMapping`注解可以指定控制器/处理器的某个方法的请求的url

### 修饰类和方法

- `@RequestMapping`注解可以修饰房啊，也可以修饰类。当同时修饰类和方法时，请求的url就是组合
  `/类请求值/方法请求值`

```java
package com.charlie.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/user")
@Controller
public class UserHandler {

    /*
    1. method=RequestMethod.POST：表示请求buy目标方法必须是 post
    2. RequestMethod 四个常用选项 POST, GET, PUT, DELETE
    3. SpringMVC 控制器默认支持 GET 和 POST 两种方式，即指定method时，GET和POST方法都可以
    4. buy() 方法请求的url：http://localhost:8080/工程路径/user/buy
    5. @PostMapping (value = "/buy") 等价于 把 method=RequestMethod.POST 的 @RequestMapping 方法
     */
    //@RequestMapping(value = "/buy", method = RequestMethod.POST)
    @PostMapping (value = "/buy")
    public String buy() {
        System.out.println("购买商品");
        return "success";
    }
}
```

### 指定请求方式

1. `@RequestMapping`可以指定请求的方式(`post/get/put/delete`)，请求的方式需要和指定的一样，否则报错
2. `SpringMVC`控制器默认支持 `GET` 和 `POST` 两种方式，即当不指定 `method` 时，可以接收 `GET` 和 `POST` 请求
3. ![img_4.png](img_4.png)

### 指定 params 和 headers 支持简单表达式

```
 /*
 1. params = "bookId" 表示请求该目标方法时，必须给一个bookId参数，值没有限定
 2. search(String bookId)：表示请求目标方法时，携带的bookId=100，就会将请求携带的bookId对应的值100
     ，赋值给 String bookId
 3. params = "bookId=100" 表示参数bookId的值必须为100，否则会报错
  */
 @RequestMapping(value = "/find", params = "bookId=100", method = RequestMethod.GET)
 public String search(String bookId) {
     System.out.println("查询数据 bookId=" + bookId);
     return "success";
 }
```

1. `params="bookId"`：表示请求必须包含名为 `bookId` 的请求参数
2. `params!="param1"`：表示请求不能包含名为 `param1` 的请求参数
3. `params = "bookId=100"`：表示请求包含名为 `bookId` 的请求参数，且其值必须为 `100`
4. ![请求参数](img_5.png)

### 支持Ant风格资源地址

1. `?`：匹配文件名的一个字符
2. `*`：匹配文件名中的任意字符
3. `**`：匹配多层路径
4. ![Ant风格的url地址举例](img_6.png)

```
 /*
 1. 需求：可以配置 /user/message/aa, /user/message/aa/bb/cc
 2. @RequestMapping(value = "/message/**") /** 表示可以匹配多层路径
  */
 @RequestMapping(value = "/message/**")
 public String im() {
     System.out.println("发送消息...");
     return "success";
 }
```

### 路径参数

1. `@RequestMapping`可以配合 `@PathVariable` 映射url绑定的占位符
2. 这样就可以**不需要在url地址上带参数名**了，更加简洁明了

```html
<h1>占位符的演示</h1>
<a href="user/reg/charlie/21">占位符演示</a>
```

```java
package com.charlie.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/user")
@Controller
public class UserHandler {
   /*
   1. 要求：希望目标方法获取到 username 和 userId, value="/xx/{username}".@PathVariable("username")
   2. 前端页面：<a href="user/reg/kristina/300">占位符演示</a>
   3. value = "/reg/{username}/{userId}" 表示 charlie->{username} 21->{userId}
   4. @PathVariable("username") 需要与路径变量名 {username} 匹配，传入的参数名 String name 可以自定义
   */
   @RequestMapping(value = "/reg/{username}/{userId}")
   public String register(@PathVariable("username") String name, @PathVariable("userId") String id) {
      System.out.println("接收到参数：username=" + name + "，userId=" + id);
      return "success";
   }
}
```

### 注意事项和使用细节

1. 映射的url，不能重复
   - ![映射的URL不能重复](img_7.png)
2. 各种请求的简写形式，下面的 `value=` 也可以省略
   - ![img_8.png](img_8.png)
3. 当我们确定表单或者超链接会提交某个字段数据比如(email)时，要求提交的参数名和目标方法的参数名保持一致
   - ![img_9.png](img_9.png)

## Postman(接口测试根据)

| ![img_10.png](img_10.png) | ![img_11.png](img_11.png) | ![img_12.png](img_12.png) |
|---------------------------|---------------------------|---------------------------|
| ![img_13.png](img_13.png) | ![img_14.png](img_14.png) | ![img_15.png](img_15.png) |

## REST-优雅的url请求风格

1. `Representational State Transfer`(REST)，资源表现层状态转化，是目前流行的请求方式。
2. HTTP协议里面，四个表示操作方式的动词：`GET, POST, PUT, DELETE`。分别对应四种基本操作：
   1) GET用来获取资源
   2) POST用来新建资源
   3) PUT用来更新资源
   4) DELETE用来删除资源
3. 传统的url是通过**参数**来说明crud类型，rest是通过get/post/put/delete来说明crud的类型
4. REST的核心过滤器 `HiddenHttpMethodFilter`
   - ![img_16.png](img_16.png)

```xml
<!--web.xml配置-->
<!--配置HiddenHttpMethodFilter过滤器
1. 作用：把以POST方式提交的delete/put请求进行转换
2. 配置 url-pattern 为 /* 表示所有请求都经过 hiddenHttpMethodFilter过滤
-->
<filter>
    <filter-name>hiddenHttpMethodFilter</filter-name>
    <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
</filter>
<filter-mapping>
    <filter-name>hiddenHttpMethodFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>

<!--springDispatcherServlet-servlet.xml-->
<!--加入两个常规配置-->
<!--支持SpringMVC的高级功能，比如JSR303校验，映射动态请求-->
<mvc:annotation-driven/>
<!--将SpringMVC不能处理的请求，交给tomcat处理，比如css,js等-->
<mvc:default-servlet-handler/>
```

```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>rest</title>
    <script type="text/javascript" src="script/jquery-3.6.0.min.js"></script>
    <script type="text/javascript">
        $(function () { // 当页面加载完成后，就执行
            // alert("OK!");
            $("#deleteBook").click(function () {
                // alert("点击...");
                // 自定义一个提交行为，把href值，填入到hiddenForn的action
                $("#hiddenForm").attr("action", this.href);
                $(":hidden").val("DELETE");
                $("#hiddenForm").submit();
                return false;   // 点击超链接，但不提交
            })
        })
    </script>
</head>
<body>
<h3>Rest风格的crud操作案例</h3>
<br><hr>

<h3>rest风格的url-查询书籍[get]</h3>
<a href="user/book/111">点击查询书籍</a>
<br><hr>

<h3>rest风格的url-添加书籍[post]</h3>
<form action="user/book" method="post">
    name:<input name="bookName" type="text"><br>
    <input type="submit" value="添加书籍">
</form>
<br><hr>

<h3>rest 风格的url, 删除一本书</h3>
<%--分析
1. 在默认情况下，超链接发出的是GET请求
2. 怎么样将GET请求，转成 springmvc 可以识别的delete，就要考虑 HiddenHttpMethodFilter
    public static final String DEFAULT_METHOD_PARAM = "_method";
    --------------------------------------------------------------
    private static final List<String> ALLOWED_METHODS =
        Collections.unmodifiableList(Arrays.asList(HttpMethod.PUT.name(),
                HttpMethod.DELETE.name(), HttpMethod.PATCH.name()));

    if ("POST".equals(request.getMethod()) && request.getAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE) == null) {
        String paramValue = request.getParameter(this.methodParam);
        if (StringUtils.hasLength(paramValue)) {
            String method = paramValue.toUpperCase(Locale.ENGLISH);
            if (ALLOWED_METHODS.contains(method)) {
                requestToUse = new HttpMethodRequestWrapper(request, method);
            }
        }
    }
3. 从源码中可以看到，HiddenHttpMethodFilter过滤器可以对以POST方式提交的delete/put/patch进行转换，转换成
    springmvc可以识别的 RequestMethod.DELETE / PUT / ...
4. 需要将 get 请求 <a href="user/book/600">删除指定id的书</a> ，以post房四海提交给后端handler，这样过滤器才会生效
5. 可以通过前端 jquery 实现
--%>
<a href="user/book/600" id="deleteBook">删除指定id的书</a>
<form action="" method="post" id="hiddenForm">
    <input type="hidden" name="_method"/>
</form>
<br><hr>

<h3>rest风格的url修改书籍[put]~</h3>
<form action="user/book/123" method="post">
    <input type="hidden" name="_method" value="PUT">
    <input type="submit" value="修改书籍~">
</form>
</body>
</html>
```

```java
package com.charlie.web.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class BookHandler {  // 处理rest风格的请求-增删改查
    // 查询[GET]
    @GetMapping("/book/{id}")
    public String getBook(@PathVariable("id") String id) {
        System.out.println("查询书籍 id=" + id);
        return "success";
    }

    // 添加[PST]
    @PostMapping("/book")
    public String addBook(String bookName) {
        System.out.println("bookName=" + bookName);
        return "success";
    }

    // 删除[DELETE]
    @RequestMapping(value = "/book/{id}", method = RequestMethod.DELETE)
    public String delBook(@PathVariable("id") String id) {
        System.out.println("删除书籍 id=" + id);
        //return "success";   // 如果这样返回会报错：HTTP Status 405 - JSPs only permit GET POST or HEAD
        /* "redirect:/user/success" 重定向
        1. 第一个/在后端执行，会被解析成 /springmvc/user/success
         */
        return "redirect:/user/success";
    }

    // 如果请求是 /user/success，就转发到 success.jsp
    @RequestMapping("/success")
    public String successGeneral() {
        return "success";   // 由该方法转发到 success.jsp 页面
    }

    // 修改[PUT]
    @PutMapping("/book/{id}")
    public String updateBook(@PathVariable("id") String id) {
        System.out.println("修改书籍 id=" + id);
        return "redirect:/user/success";
    }
}
```

### 注意事项和细节说明

1. `HiddenHttpMethodFilter`在将post转成delete/put请求时，是按 `_method` 参数名来读取的
2. ![img_17.png](img_17.png)

## SpringMVC映射请求数据

### 获取参数值

- 开发中，如何获取到 `http://xxx/url?参数名=参数值&参数名=参数值`

```java
package com.charlie.web.requestparam;

@Controller
@RequestMapping("/vote")
public class VoteHandler {
    /* @RequestParam(value = "name", required = false)
    1. 获取到超链接传递的数据，请求 http://localhost:8080/springmvc/vote/vote01?name=charlie
    2. @RequestParam 表示会接收提交的参数
    3. value 表示提交的参数名是 name
    4. required=false 表示该参数可以没有；默认true，表示必须传递该参数
    5. 当使用了 @RequestParam(value = "name", required = false) 就将参数name指定给传入形参
     */
    @RequestMapping("/vote01")
    public String test01(@RequestParam(value = "name", required = false) String username) {
        System.out.println("得到的username=" + username);
        return "success";
    }
}
```

### 获取http请求消息头

```java
@Controller
@RequestMapping("/vote")
public class VoteHandler {
    /*
    需求：获取http请求头消息，获取到 Accept-Encoding 和 Host
    1. 这个涉及到前面讲过的http协议
    2. @RequestHeader("Http请求头字段")
     */
    @RequestMapping("/vote02")
    public String test02(@RequestHeader("Accept-Encoding") String ae, @RequestHeader("Host") String host) {
        System.out.println("Accept-Encoding=" + ae);
        System.out.println("Host=" + host);
        return "success";
    }
}
```

### 获取javabean形式的数据

1. 支持级联数据获取
2. 表单的控件名称name需要和javabean对象字段对应，否则就是null

```html
<%--
1. 这是一个表单，表单的数据对应Master对象
2. 提交的数据参数名和对象的字段名一致即可
--%>
<h2>封装javabean</h2>
<form action="vote/vote03" method="post">
    主人号：<input type="text" name="id"><br/>
    主人名：<input type="text" name="name"><br/>
    宠物号：<input type="text" name="pet.id"><br/>
    宠物名：<input type="text" name="pet.name"><br/>
    <input type="submit" value="添加主人和宠物">
</form>
```

```
 /*
 演示如何获取到提交的数据->封装成java对象
 1. 方法的形参用对应的类型来指定即可，SpringMVC会自动地进行封装
 2. 如果要自动地完成封装，要求提交地数据参数名和对象地字段名保持一致
 3. 如果属性是对象，这里仍然是通过 字段名.字段名 来赋值，比如 Master[pet]，
     提交地数据参数名是 pet.id, pet.name
 4. 如果提交地数据的参数名和对象的字段名不匹配，则对象的属性值就是 null
 5. 底层是 反射+注解
  */
 @RequestMapping("/vote03")
 public String test03(Master master) {
     System.out.println("master=" + master);
     return "success";
 }
```

### 获取servlet api

1. 开发中，可能需要使用到原生的 `servlet api`
2. 需要引入 `tomcat/lib` 下的 `servlet-api.jar`
3. ![img_18.png](img_18.png)

```java
@Controller
@RequestMapping("/vote")
public class VoteHandler {
   // 获取servlet api，来获取提交的数据
   @RequestMapping("/vote04")
   public String test04(HttpServletRequest req, HttpServletResponse resp,
                        HttpSession hs) {

      // 获取到session
      HttpSession session = req.getSession();
      System.out.println("session=" + session);
      // 注意：通过参数传入的hs和req.getSession()得到的session对象是同一个对象
      System.out.println("hs=" + hs);

      String username = req.getParameter("username");
      String password = req.getParameter("password");
      System.out.println("username=" + username + "\npassword=" + password);
      return "success";
   }
}
```

## 模型数据

### 数据放入request域


