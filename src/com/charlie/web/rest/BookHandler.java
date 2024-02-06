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
