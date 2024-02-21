package com.charlie.web.interceptor;

import com.charlie.web.json.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FurnHandler {
    @RequestMapping(value = "/hi")
    public String hi() {
        System.out.println("--FurnHandler--hi()...");
        return "success";
    }

    @RequestMapping(value = "/hello")
    public String hello(User user) {
        System.out.println("--FurnHandler--hello()...");
        return "success";
    }

    @RequestMapping(value = "/ok")
    public String ok() {
        System.out.println("--FurnHandler--ok()...");
        return "success";
    }
}
