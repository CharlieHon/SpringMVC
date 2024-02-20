package com.charlie.web.filter;

import javax.servlet.*;
import java.io.IOException;

// 编写过滤器，处理中文乱码
public class MyCharacterFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        // 加入对编码的处理
        servletRequest.setCharacterEncoding("utf-8");
        // 放行请求，这个规则和JavaWeb中过滤器规则一致
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {}
}
