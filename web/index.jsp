<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Deliver System Pro</title>
</head>
<body>
<form action="UserLoginServlet" method="post">
    帐号：<input type="text" name="userName"/><br>
    密码：<input type="password" name="userPassword"/><br>
    <input type="submit" value="登录"><br>
</form>
<form action="UserView/register.jsp" method="post">
    <input type="submit" value="注册"/>
</form>
</body>
</html>
