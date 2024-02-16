package com.charlie.controller;

import com.charlie.entity.Monster;
import com.charlie.hspspringmvc.annotation.AutoWired;
import com.charlie.hspspringmvc.annotation.Controller;
import com.charlie.hspspringmvc.annotation.RequestMapping;
import com.charlie.hspspringmvc.annotation.RequestParam;
import com.charlie.service.MonsterService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class MonsterController {

    // @AutoWired表示要完成属性的装配
    @AutoWired
    private MonsterService monsterService;

    // 编写方法，可以列出妖怪列表
    // springmvc支持原生的servlet-api，为了看到底层机制，这里设计两个参数
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
}
