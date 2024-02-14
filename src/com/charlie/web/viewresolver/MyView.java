package com.charlie.web.viewresolver;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/* 自定义视图
1. MyView 继承了 AbstractView，就可以作为一个视图使用
2. Component(value = "myView") 将视图注入到容器中，名字/id是 myView
 */
@Component(value = "myViewx")
public class MyView extends AbstractView {
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 完成视图渲染，并且可以确定要跳转的页面 /WEB-INF/page/my_view.jsp
        System.out.println("进入到自己的视图...");
        /*
        1. 下面就是进行请求转发到 /WEB-INF/pages/my_view.jsp，访问WEB-INF目录下资源需要使用请求转发
        2. /WEB-INF/pages/my_view.jsp 会被springmvc(服务器端)解析成 /springmvc/WEB-INF/pages/my_view.jsp
         */
        request.getRequestDispatcher("/WEB-INF/pages/my_view.jsp").forward(request, response);
    }
}
