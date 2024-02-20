<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加妖怪</title>
</head>
<body>
<h3>添加妖怪</h3>
<%--这里的表单使用的是SoringMVC的标签来完成的
1. SpringMVC表单标签在显示之前必须在 request中有一个bean，该bean的属性和表单标签的字段要对应
    request域中的key为：form标签的modelAttribute属性值，比如这里的monster
2. SpringMVC的 form:from标签的action属性值中的 / 不代表 WEB应用的根目录
3. 这里使用SpringMVC的标签主要目的是为了方便提示信息回显
--%>
<form:form action="save" method="post" modelAttribute="monster">
    妖怪名字：<form:input path="name"/><form:errors path="name"/> <br/>
    妖怪年龄：<form:input path="age"/><form:errors path="age"/> <br/>
    电子邮件：<form:input path="email"/><form:errors path="email"/> <br/>
    妖怪生日：<form:input path="birthday"/><form:errors path="birthday"/> 要求以"9999-11-11"的形式<br/>
    妖怪薪水：<form:input path="salary"/><form:errors path="salary"/> 要求以"123,890.12"的形式<br/>
    <input type="submit" value="添加妖怪"/>
</form:form>
</body>
</html>
