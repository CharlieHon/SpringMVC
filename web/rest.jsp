<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>rest</title>
    <script type="text/javascript" src="script/jquery-3.6.0.min.js"></script>
    <script type="text/javascript">
        $(function () { // 当页面加载完成后，就执行
            // alert("OK!");
            $("#deleteBook").click(function () {
                // alert("点击...");
                // 自定义一个提交行为，把href值，填入到hiddenForn的action
                $("#hiddenForm").attr("action", this.href);
                $(":hidden").val("DELETE");
                $("#hiddenForm").submit();
                return false;   // 点击超链接，但不提交
            })
        })
    </script>
</head>
<body>
<h3>Rest风格的crud操作案例</h3>
<br><hr>

<h3>rest风格的url-查询书籍[get]</h3>
<a href="user/book/111">点击查询书籍</a>
<br><hr>

<h3>rest风格的url-添加书籍[post]</h3>
<form action="user/book" method="post">
    name:<input name="bookName" type="text"><br>
    <input type="submit" value="添加书籍">
</form>
<br><hr>

<h3>rest 风格的url, 删除一本书</h3>
<%--分析
1. 在默认情况下，超链接发出的是GET请求
2. 怎么样将GET请求，转成 springmvc 可以识别的delete，就要考虑 HiddenHttpMethodFilter
    public static final String DEFAULT_METHOD_PARAM = "_method";
    --------------------------------------------------------------
    private static final List<String> ALLOWED_METHODS =
        Collections.unmodifiableList(Arrays.asList(HttpMethod.PUT.name(),
                HttpMethod.DELETE.name(), HttpMethod.PATCH.name()));

    if ("POST".equals(request.getMethod()) && request.getAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE) == null) {
        String paramValue = request.getParameter(this.methodParam);
        if (StringUtils.hasLength(paramValue)) {
            String method = paramValue.toUpperCase(Locale.ENGLISH);
            if (ALLOWED_METHODS.contains(method)) {
                requestToUse = new HttpMethodRequestWrapper(request, method);
            }
        }
    }
3. 从源码中可以看到，HiddenHttpMethodFilter过滤器可以对以POST方式提交的delete/put/patch进行转换，转换成
    springmvc可以识别的 RequestMethod.DELETE / PUT / ...
4. 需要将 get 请求 <a href="user/book/600">删除指定id的书</a> ，以post房四海提交给后端handler，这样过滤器才会生效
5. 可以通过前端 jquery 实现
--%>
<a href="user/book/600" id="deleteBook">删除指定id的书</a>
<form action="" method="post" id="hiddenForm">
    <input type="hidden" name="_method"/>
</form>
<br><hr>

<h3>rest风格的url修改书籍[put]~</h3>
<form action="user/book/123" method="post">
    <input type="hidden" name="_method" value="PUT">
    <input type="submit" value="修改书籍~">
</form>

</body>
</html>
