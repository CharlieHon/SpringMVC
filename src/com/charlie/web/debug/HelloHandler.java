package com.charlie.web.debug;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HelloHandler {
    // 编写方法，响应请求，返回ModelAndView
    @RequestMapping("/debug/springmvc")
    public ModelAndView hello(HttpServletRequest req, HttpServletResponse resp) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ok"); // 对应到 /WEB-INF/pages/ok.jsp
        modelAndView.addObject("name", "韩顺平");  // 在model中放入数据[k-v]
        return modelAndView;
    }
}
