<%@ page import="com.LinYuda.www.po.User" %>
<%@ page import="com.LinYuda.www.po.Order" %>
<%@ page import="com.LinYuda.www.po.ProductMenu" %>
<%@ page import="com.LinYuda.www.service.ProductMenuService" %>
<%@ page import="com.LinYuda.www.po.NormalUser" %>
<%@ page import="com.LinYuda.www.entity.FullOrder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>查看历史订单</title>
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

你订过的订单有：<br>
<%
    FullOrder[] orders = (FullOrder[]) session.getAttribute("historyOrders");
    for (int i = 0; i < orders.length; i++) {
        //调用了service
%>
<%=orders[i].getMealName()+ "    订单时间：" + orders[i].getOrderTime() + "    状态：" + (orders[i].getStatus() == 1 ? "已完成" : "未完成")%>
<br>
<%
    }
%>
<br>
<a href="NormalUserView/normalUserView.jsp">返回主界面</a>
</body>
</html>
