<%@ page import="com.LinYuda.www.po.User" %>
<%@ page import="com.LinYuda.www.po.NormalUser" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改密码</title>
</head>
<body>
<%
    //校验用户是否有登录，如果没有，跳转到登录页面
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("../index.jsp");
    }
    if (user.getId() == -1 || user.getPermissionLevel() == -1) {
        response.sendRedirect("../index.jsp");
    }
    NormalUser normalUser = (NormalUser) session.getAttribute("normalUser");
    if (normalUser == null) {
        response.sendRedirect("../index.jsp");
    }
%>

<form action="../UpdateUserPasswordServlet" method="post">
    请输入新的密码：<input type="password" name="newPassword"/>
    <input type="submit" value="确定"/>
</form>
<a href="normalUserView.jsp">返回主界面</a>

</body>
</html>
