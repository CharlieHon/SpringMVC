<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>测试 request parameter</title>
</head>
<body>
<h2>获取到超链接参数值</h2><hr/>
<a href="vote/vote01?name=charlie">获取超链接的参数</a><br/>

<h2>获取到消息头</h2>
<a href="vote/vote02">获取http消息头信息</a><br/>

<%--
1. 这是一个表单，表单的数据对应Master对象
2. 提交的数据参数名和对象的字段名一致即可
--%>
<h2>封装javabean</h2>
<form action="vote/vote03" method="post">
    主人号：<input type="text" name="id"><br/>
    主人名：<input type="text" name="name"><br/>
    宠物号：<input type="text" name="pet.id"><br/>
    宠物名：<input type="text" name="pet.name"><br/>
    <input type="submit" value="添加主人和宠物">
</form>
<h2>演示servlet-api</h2>
<form action="vote/vote04" method="post">
    用户名：<input type="text" name="username"><br/>
    密　码：<input type="password" name="password"><br/>
    <input type="submit" value="提交">
</form>
</body>
</html>
