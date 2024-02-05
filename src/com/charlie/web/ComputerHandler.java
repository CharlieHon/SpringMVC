package com.charlie.web;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/computer")
@Service
public class ComputerHandler {

    @PostMapping("/message")
    public String message(String brand, String price, String amount) {
        System.out.println("品牌：" + brand + "\n价格：" + price + "\n数量：" + amount);
        return "success";
    }
}
