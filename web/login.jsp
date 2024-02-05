<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>
<h3>登录页面</h3>
<%--
1. javaweb web工程路径，action="login" 表示的url是 http://localhost:8080/springmvc/
2. action="/login" 表示的url是 http://localhost:8080/login
--%>
<form action="login">
    u:<input type="text" name="username"><br/>
    p:<input type="password" name="password"><br/>
    <input type="submit" value="登录">
</form>
</body>
</html>
