<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>vote_ok</title>
</head>
<body>
<h1>获取的数据显示页面</h1>
<hr/>取出 request域的数据-通过前面el表达式获取<br/>
address: ${requestScope.address}<br>
主人名字：${requestScope.master.name}<br/>
主人信息：${requestScope.master.id}<br/>
主人名字：${requestScope.master.pet.name}<br/>

<h1>取出session域的数据</h1>
地址信息=${sessionScope.address}<br/>
主人名字=${sessionScope.master.name}<br/>
</body>
</html>
