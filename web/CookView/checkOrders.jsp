<%@ page import="com.LinYuda.www.po.*" %>
<%@ page import="com.LinYuda.www.entity.FullOrder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>查看所有订单</title>
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
<%
    FullOrder[] orders = (FullOrder[]) session.getAttribute("allOrders");
    for (int i = 0; i < orders.length; i++) {
%>
<%=orders[i].getMealName() + "   下单时间：" + orders[i].getOrderTime() + "    地址：" + orders[i].getOrderPersonLocation() + "    点单人：" + orders[i].getOrderPersonName() + "     状态：" + (orders[i].getStatus() == 1 ? "已完成" : "未完成")%>
<br>
<%
    }
%>
<a href="../LoadOrdersServlet">返回主界面</a>
</body>
</html>
