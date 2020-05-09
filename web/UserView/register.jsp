<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户注册</title>
</head>
<body>

<form action="../RegisterServlet" method="post">
    输入帐号：<input type="text" name="account"/><br>
    输入密码：<input type="password" name="password"/><br>
    用户名字：<input type="text" name="userName"/><br>
    宿舍地址：<input type="text" name="location"/><br>
    联系方式：<input type="text" name="contact"/><br>
    选择身份：
    <select name="permissionLevel">
        <option value="1" selected>学生</option>
        <option value="2">老师</option>
        <option value="3">厨师</option>
        <option value="4">副经理</option>
        <option value="5">总经理</option>
    </select><br>
    <input type="submit" value="注册"/><br>
</form>
<a href="../index.jsp">返回登录页面</a>
</body>
</html>
