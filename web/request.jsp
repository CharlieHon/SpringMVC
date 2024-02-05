<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<hr>
<h1>购买商品</h1>
<%--
1. action="user/buy" 对应url：http://localhost:8080/工程路径/user/buy
--%>
<form action="user/buy" method="post">
  购买人：<input type="text" name="username"><br/>
  购买量：<input type="text" name="nums"><br/>
  <input type="submit" value="购买">
</form>

<hr/><h1>演示params的使用</h1>
<a href="user/find?bookId=100">查询书籍</a>

<hr><h1>演示Ant风格的请求资源方式</h1>
<a href="user/message/aa">发送消息1</a><br/>
<a href="user/message/aa/bb/cc">发送消息2</a><br/>

<hr/><h1>占位符的演示</h1>
<a href="user/reg/charlie/12">占位符的演示</a>

<%--练习：编写一个表单, 以Post提交Computer信息, 后端编写ComputerHandler, 可以接收到信息--%>
<hr/><h1>电脑信息</h1><br/>
<form action="computer/message" method="post">
    品牌：<input type="text" name="brand"><br/>
    价格：<input type="text" name="price"><br/>
    数量：<input type="text" name="amount"><br/>
    <button type="submit">提交</button>
</form>
</body>
</html>
