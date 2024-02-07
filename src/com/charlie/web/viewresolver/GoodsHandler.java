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
}
