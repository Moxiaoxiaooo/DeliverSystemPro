<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>厨师注册详细页</title>
</head>
<body>

<form action="CookRegisterServlet" method="post">
    上级的id：<input type="text" name="superiorId"/><br>
    负责的窗口编号：<input type="text" name="windowNo"/><br>
    <input type="submit" value="提交"/>
</form>
<br>
<a href="UserView/register.jsp">返回注册页面</a>
</body>
</html>
