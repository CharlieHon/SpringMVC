package com.charlie.web.requestparam;

import com.charlie.web.requestparam.entity.Master;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/vote")
public class VoteHandler {
    /* @RequestParam(value = "name", required = false)
    1. 获取到超链接传递的数据，请求 http://localhost:8080/springmvc/vote/vote01?name=charlie
    2. @RequestParam 表示会接收提交的参数
    3. value 表示提交的参数名是 name
    4. required=false 表示该参数可以没有；默认true，表示必须有这个参数
    5. 当使用了 @RequestParam(value = "name", required = false) 就将参数name指定给传入形参
     */
    @RequestMapping("/vote01")
    public String test01(@RequestParam(value = "name", required = false) String username) {
        System.out.println("得到的username=" + username);
        return "success";
    }

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
