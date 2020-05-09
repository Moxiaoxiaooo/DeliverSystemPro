<%@ page import="com.LinYuda.www.po.User" %>
<%@ page import="com.LinYuda.www.po.Cook" %>
<%@ page import="com.LinYuda.www.po.ProductMenu" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改商品</title>
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
你管理的商品有：<br><br>
<%
    ProductMenu[] productMenus = (ProductMenu[]) session.getAttribute("cookMenus");
    for (int i = 0; i < productMenus.length; i++) {
%>
<%=productMenus[i]%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
        href="../ReadyUpdateMenuServlet?selectedMenuId=<%=productMenus[i].getId()%>">修改这个商品</a>
<br>
<%
    }
%><br>

<a href="addMenu.jsp">添加商品</a><br><br><br>

<a href="../LoadOrdersServlet">返回主界面</a>
</body>
</html>
