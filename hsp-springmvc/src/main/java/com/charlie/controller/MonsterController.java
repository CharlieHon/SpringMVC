package com.charlie.controller;

import com.charlie.entity.Monster;
import com.charlie.hspspringmvc.annotation.*;
import com.charlie.service.MonsterService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MonsterController {

    // @AutoWired表示要完成属性的装配
    @AutoWired
    private MonsterService monsterService;

    // 编写方法，可以列出妖怪列表
    // springmvc支持原生的servlet-api，为了看到底层机制，这里设计两个参数
    // 目标方法的实参，在springmvc底层通过封装号的参数数组传入...
    @RequestMapping(value = "/monster/list")
    public void listMonsters(HttpServletRequest req, HttpServletResponse resp) {
        // 这样些能运行，把参数传递过来，是因为在分发器中使用反射传参了：hspHandler.getMethod().invoke(hspHandler.getController(), req, resp);
        // 设置编码和返回类型
        resp.setContentType("text/html;charset=utf-8");

        StringBuilder content = new StringBuilder("<h1>妖怪列表信息</h1>");
        content.append("<table border='1px width='500px' style='border-collapse:collapse'>");
        // 调用monsterService方法
        List<Monster> monsters = monsterService.listMonster();
        for (Monster monster : monsters) {
            content.append("<tr><td>" + monster.getId() + "</td><td>" + monster.getName() + "</td><td>" +
                    monster.getSkill() + "</td><td>" + monster.getAge() + "</td></tr>");
        }
        content.append("</table>");
        // 获取writer返回信息
        try {
            PrintWriter writer = resp.getWriter();
            writer.write(content.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/monster/find")
    public void findMonstersByName(HttpServletRequest req, HttpServletResponse resp,
                                   @RequestParam(value = "name") String monsterName, String hobby) {
        System.out.println("-----------接收到的monsterName=" + monsterName);
        System.out.println("-----------接收到的hobby=" + hobby);
        resp.setContentType("text/html;charset=utf-8");
        StringBuilder content = new StringBuilder("<h1>查询的妖怪信息</h1>");
        content.append("<table border='1px width='500px' style='border-collapse:collapse'>");
        List<Monster> monsters = monsterService.findMonsterByName(monsterName);
        for (Monster monster : monsters) {
            content.append("<tr><td>" + monster.getId() + "</td><td>" + monster.getName() + "</td><td>" +
                    monster.getSkill() + "</td><td>" + monster.getAge() + "</td></tr>");
        }
        content.append("</table>");
        try {
            PrintWriter writer = resp.getWriter();
            writer.write(content.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 处理妖怪登录的方法，返回要请求转发/重定向的字符串
    @RequestMapping(value = "/monster/login")
    public String loginByName(HttpServletRequest req, HttpServletResponse resp, @RequestParam(value = "mName") String name) {
        // 注意：因为所有请求都是通过 HspDispatcherServlet 转发的，所以解决中文乱码问题可以在分发器里一次性解决
        //req.setCharacterEncoding("utf-8");
        System.out.println("接收到的name=" + name);
        // 将name设置到request域中
        req.setAttribute("name", name);
        boolean flag = monsterService.login(name);
        if (flag) { // 登录成功
            //return "forward:/WEB-INF/login_ok.jsp";
            // 测试重定向
            //return "redirect:/login_ok.jsp";
            // 测试默认方式
            return "/login_ok.jsp";
        } else {    // 登录失败
            //return "forward:/WEB-INF/login_error.jsp";
            //return "redirect:/login_error.jsp";
            return "/login_error.jsp";
        }
    }

    /**
     * 编写方法，返回json格式的数据
     * 1. 目标方法返回的结果是给SpringMVC底层通过反射调用的位置
     * 2. 在springmvc底层反射调用的位置，接收到结果并解析即可
     * 3. 方法上标注了 @ResponseBody 表示希望以json格式返回数据
     */
    @ResponseBody
    @RequestMapping(value = "/monster/list/json")
    public List<Monster> listMonsterByJson(HttpServletRequest req, HttpServletResponse resp) {
        return monsterService.listMonster();
    }
}
