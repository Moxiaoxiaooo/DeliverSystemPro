<%@ page import="com.LinYuda.www.po.Cook" %>
<%@ page import="com.LinYuda.www.po.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>删除失败</title>
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
商品删除失败
<%
    String errorString = request.getParameter("error");
    int error = Integer.parseInt(errorString);
    if (error == 1) {
%>
删除失败，请确定该商品未被删除
<%
} else if (error == 2) {
%>
删除失败，请确定该商品存在
<%
    }
%>
<a href="updateMenu.jsp">回到修改页面</a>
</body>
</html>
