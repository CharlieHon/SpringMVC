package com.charlie.web.homework;

import com.charlie.web.homework.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
public class LoginHandler {
    // 处理登录
    @RequestMapping("/login")
    public String doLogin(User user) {
        if ("hsp".equals(user.getUsername()) && "123".equals(user.getPassword())) {
            // 验证成功
            return "forward:/WEB-INF/pages/homework/login_ok.jsp";
        }
        return "forward:/WEB-INF/pages/homework/login_error.jsp";
    }
}
