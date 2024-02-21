<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件上传</title>
</head>
<body>
<h1>文件上传演示</h1>
<form action="fileUpload" method="post" enctype="multipart/form-data">
    文件简介：<input type="text" name="introduce"/><br/>
    选择文件：<input type="file" name="file"/><br/>
    <input type="submit" value="上传文件">
</form>
</body>
</html>
