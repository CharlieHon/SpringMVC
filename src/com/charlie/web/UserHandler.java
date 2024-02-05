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

    /*
    1. 需求：可以配置 /user/message/aa, /user/message/aa/bb/cc
    2. @RequestMapping(value = "/message/**") /** 表示可以匹配多层路径
     */
    @RequestMapping(value = "/message/**")
    public String im() {
        System.out.println("发送消息...");
        return "success";
    }

    /*
    1. 要求：希望目标方法获取到 username 和 userId, value="/xx/{username}".@PathVariable("username")
    2. 前端页面：<a href="user/reg/kristina/300">占位符演示</a>
    3. value = "/reg/{username}/{userId}" 表示 kristina->{username} 300->{userId}
    4. @PathVariable("username") 需要与路径变量名 {username} 匹配，传入的参数名 String name 可以自定义
     */
    @RequestMapping(value = "/reg/{username}/{userId}")
    public String register(@PathVariable("username") String name, @PathVariable("userId") String id) {
        System.out.println("接收到参数：username=" + name + "，userId=" + id);
        return "success";
    }

    @RequestMapping(value = "/hi")
    public String hi() {
        System.out.println("hi~");
        return "success";
    }

    // 错误：映射的URL不能重复！虽然语法上不会报错，但是在启动后会报错
    // There is already 'userHandler' bean method
    //@RequestMapping(value = "/hi")
    //public String hi2() {
    //    System.out.println("hi~");
    //    return "success";
    //}

    /*
    1. hello3(String email)：表示如果请求参数有 email=xx，就会将传递的值赋给形参email，
        要求名称保持一致。如果不一致，则接收不到数据，值为null
     */
    @GetMapping(value = "/hello3")
    public String hello3(String email) {
        System.out.println("hello3: email=" + email);
        return "success";
    }
}
