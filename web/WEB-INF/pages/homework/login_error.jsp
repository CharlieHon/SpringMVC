<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录失败</title>
</head>
<body>
<h1>登录失败</h1>
<%--
1. <%=request.getContextPath()%>/homework/login.jsp 解析为 /springmvc/homework/login.jsp
2. 浏览器会解析第一个 / 为 http://localhost:8080，在拼接起来即为 http://localhost:8080/springmvc/homework/login.jsp
--%>
<a href="<%=request.getContextPath()%>/homework/login.jsp">返回重新登录</a>
</body>
</html>
