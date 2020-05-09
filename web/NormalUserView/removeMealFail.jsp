<%@ page import="com.LinYuda.www.po.User" %>
<%@ page import="com.LinYuda.www.po.NormalUser" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>删除商品失败</title>
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

删除失败，请确定商品仍然存在<br>
<a href="NormalUserView/normalUserView.jsp">返回菜单页面</a>
</body>
</html>
