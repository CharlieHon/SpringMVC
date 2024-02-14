package com.charlie.web.viewresolver;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/goods")
public class GoodsHandler {

    @RequestMapping("/buy")
    public String buy() {
        System.out.println("---------buy()-----------");
        return "myView";   // 自定义视图名
    }

    // 演示直接指定要请求转发的或者是重定向的页面
    @RequestMapping("/order")
    public String order() {
        System.out.println("==========order()=========");
        // 请求转发到 /WEB-INF/pages/my_view.jsp，会被解析到 /springmvc/WEB-INF/pages/my_view.jsp
        //return "forward:/WEB-INF/pages/my_view.jsp";

        /* 直接指定要重定向的页面
        1. 对于重定向来说，不能重定向到 /WEB/-INF/ 目录下
        2. redirect 关键字，表示进行重定向
        3. /login.jsp 会在服务器解析为 /springmvc/login.jsp
         */
        return "redirect:/login.jsp";
    }
}
