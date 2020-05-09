<%@ page import="com.LinYuda.www.po.Cook" %>
<%@ page import="com.LinYuda.www.po.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改密码失败</title>
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
    Cook cook = (Cook) session.getAttribute("cook");
    if (cook == null) {
        response.sendRedirect("../index.jsp");
    }
%>
修改密码失败<br>
请确定已经输入了密码<br>
<a href="../LoadOrdersServlet">返回主界面</a>
</body>
</html>
