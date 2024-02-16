package com.charlie.controller;

import com.charlie.hspspringmvc.annotation.Controller;
import com.charlie.hspspringmvc.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class OrderController {
    @RequestMapping(value = "/order/list")
    public void listOrder(HttpServletRequest req, HttpServletResponse resp) {
        // 设置编码和返回类型
        resp.setContentType("text/html;charset=utf-8");
        // 获取writer返回信息
        try {
            PrintWriter writer = resp.getWriter();
            writer.write("<h1>订单列表</h1>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/order/add")
    public void addOrder(HttpServletRequest req, HttpServletResponse resp) {
        // 设置编码和返回类型
        resp.setContentType("text/html;charset=utf-8");
        // 获取writer返回信息
        try {
            PrintWriter writer = resp.getWriter();
            writer.write("<h1>添加订单</h1>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
